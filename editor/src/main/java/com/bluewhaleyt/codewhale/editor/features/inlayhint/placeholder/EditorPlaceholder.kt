package com.bluewhaleyt.codewhale.editor.features.inlayhint.placeholder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bluewhaleyt.codewhale.editor.features.inlayhint.BaseEditorInlayHintRenderer
import io.github.rosemoe.sora.graphics.Paint
import io.github.rosemoe.sora.lang.styling.inlayHint.InlayHint
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

@PublishedApi
internal class EditorPlaceholder(val text: String) : InlayHint(0, 0, TypeName)

@PublishedApi
internal class EditorPlaceholderRenderer private constructor() : BaseEditorInlayHintRenderer<EditorPlaceholder>(TypeName) {

    override fun setText(inlayHint: EditorPlaceholder): String {
        return inlayHint.text
    }

    override fun setBackgroundPaint(paint: Paint, scheme: EditorColorScheme) {
        paint.color = Color.Transparent.toArgb()
    }

    override fun setForegroundPaint(paint: Paint, scheme: EditorColorScheme) {
        paint.color = scheme.getColor(EditorColorScheme.LINE_NUMBER_CURRENT)
    }

    companion object {
        val instance: EditorPlaceholderRenderer by lazy { EditorPlaceholderRenderer() }
    }
}

private const val TypeName = "placeholder"