package com.bluewhaleyt.codewhale.editor.features.inlayhint

import android.graphics.Canvas
import io.github.rosemoe.sora.graphics.InlayHintRenderParams
import io.github.rosemoe.sora.graphics.Paint
import io.github.rosemoe.sora.graphics.inlayHint.InlayHintRenderer
import io.github.rosemoe.sora.lang.styling.inlayHint.InlayHint
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

abstract class BaseEditorInlayHintRenderer<T : InlayHint> internal constructor(
    override val typeName: String
) : InlayHintRenderer() {

    private val localPaint = Paint().apply { isAntiAlias = true }

    private var spaceWidth = 0f
    private var lineHeight = 0f
    private var baselineOffset = 0f

    abstract fun setText(inlayHint: T): String

    open fun setBackgroundPaint(paint: Paint, scheme: EditorColorScheme) {
        paint.color = scheme.getColor(EditorColorScheme.TEXT_INLAY_HINT_BACKGROUND)
    }

    open fun setForegroundPaint(paint: Paint, scheme: EditorColorScheme) {
        paint.color = scheme.getColor(EditorColorScheme.TEXT_INLAY_HINT_FOREGROUND)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onMeasure(
        inlayHint: InlayHint,
        paint: Paint,
        params: InlayHintRenderParams
    ): Float {
        localPaint.typeface = paint.typeface
        localPaint.textSize = paint.textSize

        spaceWidth = localPaint.measureText("")
        lineHeight = localPaint.descent() - localPaint.ascent()
        baselineOffset = lineHeight / 2f - localPaint.descent()

        val text = setText(inlayHint as T)
        val textWidth = localPaint.measureText(text)

        return textWidth + spaceWidth * 2f
    }

    @Suppress("UNCHECKED_CAST")
    override fun onRender(
        inlayHint: InlayHint,
        canvas: Canvas,
        paint: Paint,
        params: InlayHintRenderParams,
        colorScheme: EditorColorScheme,
        measuredWidth: Float
    ) {
        localPaint.typeface = paint.typeface
        localPaint.textSize = paint.textSize

        val centerY = (params.textTop + params.textBottom) / 2f

        setBackgroundPaint(localPaint, colorScheme)
        canvas.drawRoundRect(
            spaceWidth * 0.5f,
            centerY - lineHeight / 2f,
            measuredWidth - spaceWidth * 0.5f,
            centerY + lineHeight / 2f,
            params.textHeight * 0.15f,
            params.textHeight * 0.15f,
            localPaint
        )

        setForegroundPaint(localPaint, colorScheme)
        val text = setText(inlayHint as T)
        val baseline = centerY + baselineOffset
        canvas.drawText(text, spaceWidth, baseline, localPaint)
    }
}