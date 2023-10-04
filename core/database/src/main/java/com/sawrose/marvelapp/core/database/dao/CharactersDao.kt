package com.sawrose.marvelapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sawrose.marvelapp.core.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao{
    @Query(
        value = """
        SELECT * FROM characters
        WHERE id = :characterId
        ORDER BY name ASC
        """
    )
    fun getCharacterEntity(characterId: String): Flow<CharacterEntity>

    @Query(
        value = """
        SELECT * FROM characters
        """
    )
    fun getCharactersEntities(): Flow<List<CharacterEntity>>

    @Query(
        value = """
        SELECT * FROM characters
        WHERE id IN (:ids)
        """
    )
    fun getCharactersEntities(ids: Set<String>): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrIgnoreCharacters(characters: List<CharacterEntity>)

    @Update
    suspend fun updateCharacters(entities: List<CharacterEntity>)

    @Upsert
    suspend fun upsertCharacters(entities: List<CharacterEntity>)

    @Query(
        value = """
        DELETE FROM characters
        WHERE id IN (:ids)
        """
    )
    suspend fun deleteCharacters(ids: List<String>)
}