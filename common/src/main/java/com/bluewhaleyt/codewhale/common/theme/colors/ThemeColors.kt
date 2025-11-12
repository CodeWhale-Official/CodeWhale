package com.bluewhaleyt.codewhale.common.theme.colors

import com.bluewhaleyt.codewhale.common.theme.HexColorString
import kotlinx.serialization.SerialName

interface ThemeColorsProvider {

    @SerialName("background")
    val background: HexColorString

    @SerialName("foreground")
    val foreground: HexColorString
}