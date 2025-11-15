package com.bluewhaleyt.codewhale.common.theme.colors

import com.bluewhaleyt.codewhale.common.theme.HexColorString
import kotlinx.serialization.Serializable

@Serializable
data class EditorThemeColors internal constructor(
    override val background: HexColorString = HexColorString.Unspecified,
    override val foreground: HexColorString = HexColorString.Unspecified
) : EditorThemeColorsProvider

@PublishedApi
internal interface EditorThemeColorsProvider : ThemeColorsProvider