package com.sawrose.marvelapp.core.data.repository

import android.util.Log
import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.data.changeListSync
import com.sawrose.marvelapp.core.data.model.asEntity
import com.sawrose.marvelapp.core.database.dao.CharactersDao
import com.sawrose.marvelapp.core.database.model.asExternalModel
import com.sawrose.marvelapp.core.datastore.ChangeListVersion
import com.sawrose.marvelapp.core.datastore.MarvelPreferencesDataSource
import com.sawrose.marvelapp.core.model.Character
import com.sawrose.marvelapp.core.network.MarvelNetworkDataSource
import com.sawrose.marvelapp.core.network.model.CharacterDto
import com.sawrose.marvelapp.core.notification.Notifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Disk storage backed implementation of the [MarvelRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstCharactersRepository @Inject constructor(
    private val dataStore: MarvelPreferencesDataSource,
    private val characterDao: CharactersDao,
    private val network: MarvelNetworkDataSource,
    private val notifier: Notifier,
) : MarvelRepository {
    override fun getCharacters(): Flow<List<Character>> =
        characterDao.getCharactersEntities()
            .map {
                it.map { character ->
                    Log.i("Character List: \n", character.asExternalModel().imageUrl)
                    character.asExternalModel()
                }
            }

    override fun getCharacter(id: String): Flow<Character> =
        characterDao.getCharacterEntity(id)
            .map { it.asExternalModel() }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        var isFirstSync = false
        return synchronizer.changeListSync(
            versionReader = ChangeListVersion::characterVersion,
            changeListFetcher = { currentVersion ->
                isFirstSync = currentVersion <= 0
                network.getCharacterChangeList(after = currentVersion)
            },
            versionUpdater = { newVersion ->
                copy(characterVersion = newVersion)
            },
            modelDeleter = characterDao::deleteCharacters,
            modelUpdater = { changeIds ->
                val networkCharacter = network.getCharacters(ids = changeIds).data.results
                characterDao.upsertCharacters(
                    entities = networkCharacter.map(CharacterDto::asEntity),
                )
                if (isFirstSync) {
                    dataStore.setCharacterResourcesViewed(changeIds, true)
                }

                val exisitingCharacterIds = characterDao.getCharactersEntities(
                    ids = changeIds.toSet(),
                )
                    .first()
                    .map { it.id }
                    .toSet()

                // Obtain the news resources which have changed from the network and upsert them locally
                changeIds.chunked(SYNC_BATCH_SIZE).forEach { chunkedIds ->
                    val networkCharacters = network.getCharacters(ids = chunkedIds).data.results
                    characterDao.upsertCharacters(
                            entities = networkCharacters.map(
                                CharacterDto::asEntity
                            ),
                    )
                }

                val addedNewResources = characterDao.getCharactersEntities(
                    ids = changeIds.toSet(),
                )
                    .first()
                    .filter { !exisitingCharacterIds.contains(it.id) }
                    .map { it.asExternalModel() }

                if(addedNewResources.isNotEmpty()) notifier.postCharacterNotification(addedNewResources)
            },

        )
    }

}