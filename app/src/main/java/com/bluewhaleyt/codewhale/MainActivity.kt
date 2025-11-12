package com.bluewhaleyt.codewhale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhale.common.theme.Theme
import com.bluewhaleyt.codewhale.common.theme.ThemeDefinition

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeFile = if (isSystemInDarkTheme()) "dark.json" else "light.json"
            val definition = ThemeDefinition.Factory.createFromInputStream(assets.open(themeFile))

            Theme(definition) {
                Surface(Modifier.fillMaxSize()) {
                    Column(Modifier.safeDrawingPadding()) {
                        Text("Colors")
                        Text(Theme.colors.toString())
                    }
                }
            }
        }
    }
}
