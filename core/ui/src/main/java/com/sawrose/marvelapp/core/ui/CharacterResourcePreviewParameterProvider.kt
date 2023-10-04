package com.sawrose.marvelapp.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sawrose.marvelapp.core.model.Character
import com.sawrose.marvelapp.core.ui.PreviewParametersData.characterResources

/**
 * This [PreviewParameterProvider](https://developer.android.com/reference/kotlin/androidx/compose/ui/tooling/preview/PreviewParameterProvider)
 * provides list of [Character] for Composable previews.
 */
class CharacterResourcePreviewParameterProvider : PreviewParameterProvider<List<Character>>{
    override val values: Sequence<List<Character>>
        get() = sequenceOf(characterResources)
}

private const val IMAGE_URL_FORMAT = "%s.%s"

object PreviewParametersData{
    val thumbnails = listOf<String>(
        "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
        "jpg"
    )
    val characterResources = listOf(
        Character(
            id = "1",
            name = "Superman",
            description = "Superman is a fictional superhero.",
            imageUrl = IMAGE_URL_FORMAT.format(
                thumbnails[0].replace("http", "https"),
                thumbnails[1]
            )
        ),
        Character(
            id = "2",
            name = "Batman",
            description = "Batman is a fictional superhero.",
            imageUrl = IMAGE_URL_FORMAT.format(
                thumbnails[0].replace("http", "https"),
                thumbnails[1]
            )
        ),
        Character(
            id = "3",
            name = "Spiderman",
            description = "Spiderman is a fictional superhero.",
            imageUrl = IMAGE_URL_FORMAT.format(
                thumbnails[0].replace("http", "https"),
                thumbnails[1]
            )
        )

    )
}