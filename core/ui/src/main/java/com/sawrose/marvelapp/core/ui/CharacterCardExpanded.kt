package com.sawrose.marvelapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sawrose.marvelapp.core.designsystem.theme.MarvelTheme
import com.sawrose.marvelapp.core.model.Character
import com.sawrose.marvelapp.core.designsystem.R as DesignSystemR

private const val IMAGE_URL_FORMAT = "%s.%s"


@Composable
fun CharacterCardExpanded(
    character: Character,
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                CharacterImage(
                    characterImage = character.imageUrl,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CharacterName(character.name)
                    CharacterDescription(character.description)
                }

            }
        }
    }
}

@Composable
fun CharacterImage(
    characterImage: String?,
) {
    AsyncImage(
        placeholder = if (LocalInspectionMode.current) {
            painterResource(DesignSystemR.drawable.ic_placeholder_default)
        } else {
            null
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentScale = ContentScale.Crop,
        model = characterImage,
        contentDescription = null,
    )
}

@Composable
fun CharacterName(
    characterName: String
) {
    Text(
        text = characterName,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CharacterDescription(
    characterDescription: String
) {
    Text(
        text = characterDescription,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview("CharacterResourcePreview")
@Composable
private fun ExpandedNewsResourcePreview(
    @PreviewParameter(CharacterResourcePreviewParameterProvider::class)
    userNewsResources: List<Character>,
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        MarvelTheme {
            Surface {
                CharacterCardExpanded(
                    character = userNewsResources[0],
                    onCharacterClick = {},
                )
            }
        }
    }
}