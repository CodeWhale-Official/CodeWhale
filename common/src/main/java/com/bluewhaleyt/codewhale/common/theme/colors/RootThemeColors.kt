package com.bluewhaleyt.codewhale.common.theme.colors

import com.bluewhaleyt.codewhale.common.theme.HexColorString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RootThemeColors internal constructor(
    override val background: HexColorString,
    override val foreground: HexColorString,
    override val editor: EditorThemeColors = EditorThemeColors()
) : RootThemeColorsProvider

interface RootThemeColorsProvider : ThemeColorsProvider {

    @SerialName("editor")
    val editor: EditorThemeColors
}