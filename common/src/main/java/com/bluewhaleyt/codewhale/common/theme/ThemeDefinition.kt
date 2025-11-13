package com.bluewhaleyt.codewhale.common.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.bluewhaleyt.codewhale.common.extension.json5
import com.bluewhaleyt.codewhale.common.extension.staticCompositionLocal
import com.bluewhaleyt.codewhale.common.theme.colors.RootThemeColors
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.InputStream

@Serializable
class ThemeDefinition internal constructor(

    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: ThemeType = ThemeType.LIGHT,

    @SerialName("colors")
    val colors: RootThemeColors
) {

    object Factory {

        fun createFromInputStream(inputStream: InputStream): ThemeDefinition {
            return createFromJson(inputStream.bufferedReader().use { it.readText() })
        }

        fun createFromJson(jsonString: String): ThemeDefinition {
            return json5.decodeFromString<ThemeDefinition>(jsonString)
        }
    }
}

internal fun ThemeDefinition.createMaterialColorScheme(): ColorScheme {
    val isDark = type == ThemeType.DARK
    val baseScheme = if (isDark) darkColorScheme() else lightColorScheme()

    return baseScheme.copy(
//        primary =  ,
//        onPrimary =  ,
//        primaryContainer =  ,
//        onPrimaryContainer =  ,
//        inversePrimary =  ,
//        secondary =  ,
//        onSecondary =  ,
//        secondaryContainer =  ,
//        onSecondaryContainer =  ,
//        tertiary =  ,
//        onTertiary =  ,
//        tertiaryContainer =  ,
//        onTertiaryContainer =  ,
        background = colors.background.color,
        onBackground = colors.foreground.color,
        surface = colors.background.color,
        onSurface = colors.background.color,
//        surfaceVariant =  ,
//        onSurfaceVariant =  ,
//        surfaceTint =  ,
//        inverseSurface =  ,
//        inverseOnSurface =  ,
//        error =  ,
//        onError =  ,
//        errorContainer =  ,
//        onErrorContainer =  ,
//        outline =  ,
//        outlineVariant =  ,
//        scrim =  ,
//        surfaceBright =  ,
//        surfaceDim =  ,
//        surfaceContainer =  ,
//        surfaceContainerHigh =  ,
//        surfaceContainerHighest =  ,
//        surfaceContainerLow =  ,
//        surfaceContainerLowest =  ,
//        primaryFixed =  ,
//        primaryFixedDim =  ,
//        onPrimaryFixed =  ,
//        onPrimaryFixedVariant =  ,
//        secondaryFixed =  ,
//        secondaryFixedDim =  ,
//        onSecondaryFixed =  ,
//        onSecondaryFixedVariant =  ,
//        tertiaryFixed =  ,
//        tertiaryFixedDim =  ,
//        onTertiaryFixed =  ,
//        onTertiaryFixedVariant =  ,
    )
}

val LocalThemeDefinition = staticCompositionLocal<ThemeDefinition>()