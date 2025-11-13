package com.bluewhaleyt.codewhale.editor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import com.bluewhaleyt.codewhale.common.theme.LocalThemeDefinition
import com.bluewhaleyt.codewhale.editor.Editor
import com.bluewhaleyt.codewhale.editor.EditorSaver
import com.bluewhaleyt.codewhale.editor.EditorState
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.widget.subscribeEvent

@Composable
fun Editor(
    state: EditorState,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    update: (Editor) -> Unit = NoOpUpdate
) {
    val context = LocalContext.current
    val themeDefinition = LocalThemeDefinition.current

    val editor by rememberSaveable(stateSaver = EditorSaver(context, themeDefinition)) {
        mutableStateOf(Editor(context, themeDefinition))
    }

    AndroidView(
        modifier = modifier,
        factory = { editor },
        update = { currentEditor ->
            currentEditor.apply {
                if (text.toString() != state.text) {
                    setText(state.text)
                }
            }.also(update)
        }
    )

    DisposableEffect(Unit) {
        val contentChangeEvent = editor.subscribeEvent<ContentChangeEvent> { event, _ ->
            onTextChange(event.editor.text.toString())
        }

        onDispose {
            contentChangeEvent.unsubscribe()
        }
    }
}