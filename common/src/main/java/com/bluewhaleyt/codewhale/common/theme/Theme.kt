package com.bluewhaleyt.codewhale.common.theme

import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.bluewhaleyt.codewhale.common.theme.colors.RootThemeColors

@Composable
fun Theme(
    definition: ThemeDefinition,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalThemeDefinition provides definition) {
        MaterialExpressiveTheme(colorScheme = definition.createMaterialColorScheme()) {
            content()
        }
    }
}

object Theme {

    val colors: RootThemeColors
        @Composable get() = LocalThemeDefinition.current.colors
}