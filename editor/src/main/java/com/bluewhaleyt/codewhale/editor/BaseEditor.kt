package com.bluewhaleyt.codewhale.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.font.resolveAsTypeface
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.bluewhaleyt.codewhale.common.extension.isImeVisible
import com.bluewhaleyt.codewhale.common.extension.window
import com.bluewhaleyt.codewhale.common.theme.ThemeDefinition
import com.bluewhaleyt.codewhale.editor.features.inlayhint.placeholder.EditorPlaceholder
import com.bluewhaleyt.codewhale.editor.features.inlayhint.placeholder.EditorPlaceholderRenderer
import com.bluewhaleyt.codewhale.editor.features.query.EditorWordMatcher
import com.bluewhaleyt.codewhale.editor.theme.EditorTheme
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.SelectionChangeEvent
import io.github.rosemoe.sora.lang.styling.HighlightTextContainer
import io.github.rosemoe.sora.lang.styling.color.ConstColor
import io.github.rosemoe.sora.lang.styling.inlayHint.InlayHintsContainer
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import io.github.rosemoe.sora.widget.subscribeAlways
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("ViewConstructor")
abstract class BaseEditor internal constructor(
    context: Context,
    themeDefinition: ThemeDefinition
) : SoraEditor(context, null, 0, 0) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val searcher by lazy { EditorWordMatcher(this) }

    var fontFamily: FontFamily = FontFamily.Default
        set(value) {
            field = value
            val resolver = createFontFamilyResolver(context)
            val typeface by resolver.resolveAsTypeface(value)
            super.setTypefaceText(typeface)
            super.setTypefaceLineNumber(typeface)
        }

    var theme: EditorTheme = EditorTheme(themeDefinition)
        private set(value) {
            field = value
            super.setColorScheme(value)
        }

    var placeholder: String? = null

    var isWordHighlightEnabled: Boolean = true

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        fontFamily = FontFamily.Default
        theme = EditorTheme(themeDefinition)

        props.apply {
            drawSideBlockLine = false
            singleDirectionFling = false
        }

        this.post {
            requestFocus()
            registerInlayHintRenderer(EditorPlaceholderRenderer.instance)

            updatePlaceholder(text.isEmpty())
            updateWordHighlight(isWordHighlightEnabled, cursor.leftLine, cursor.leftColumn)

            subscribeAlways<ContentChangeEvent> { event ->
                post {
                    updatePlaceholder(event.editor.text.isEmpty())
                }
            }

            subscribeAlways<SelectionChangeEvent> { event ->
                post {
                    updateWordHighlight(isWordHighlightEnabled && !event.isSelected,
                        event.left.line, event.left.column)
                }
            }
        }
    }

    private fun updatePlaceholder(enabled: Boolean) {
        scope.launch {
            inlayHints = if (enabled && inlayHints == null) {
                placeholder?.let {
                    withContext(Dispatchers.Main) {
                        delay(1.milliseconds)
                        InlayHintsContainer().apply {
                            add(EditorPlaceholder(it))
                        }
                    }
                }
            } else null
        }
    }

    private fun updateWordHighlight(enabled: Boolean, line: Int, column: Int) {
        scope.launch {
            val allRanges = withContext(Dispatchers.IO) {
                searcher.getWordRanges(line, column)
            }
            withContext(Dispatchers.Main) {
                highlightTexts = if (enabled && allRanges.isNotEmpty() && allRanges.size > 1) {
                    delay(1.milliseconds)
                    HighlightTextContainer().apply {
                        addAll(
                            allRanges.map { (start, end) ->
                                val current = (start.line == line && end.line == line) &&
                                        (column >= start.column && column <= end.column)
                                HighlightTextContainer.HighlightText(
                                    startLine = start.line,
                                    startColumn = start.column,
                                    endLine = end.line,
                                    endColumn = end.column,
                                    color = if (current) {
                                        ConstColor(theme.colors!!.foreground.color.copy(0.25f).toArgb())
                                    } else {
                                        ConstColor(theme.colors!!.foreground.color.copy(0.15f).toArgb())
                                    }
                                )
                            }
                        )
                    }
                } else null
            }
        }
    }

    override fun isSoftKeyboardEnabled(): Boolean {
        return isImeVisible
    }

    override fun showSoftInput() {
        window?.let {
            WindowCompat.getInsetsController(it, this).show(WindowInsetsCompat.Type.ime())
        }
    }

    override fun hideSoftInput() {
        window?.let {
            WindowCompat.getInsetsController(it, this).hide(WindowInsetsCompat.Type.ime())
        }
    }

    @SoraEditorApiOverride
    @Deprecated("Use getFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun getTypefaceText(): Typeface {
        return super.getTypefaceText()
    }

    @SoraEditorApiOverride
    @Deprecated("Use setFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun setTypefaceText(typefaceText: Typeface?) {
        super.setTypefaceText(typefaceText)
    }

    @SoraEditorApiOverride
    @Deprecated("Use getFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun getTypefaceLineNumber(): Typeface {
        return super.getTypefaceLineNumber()
    }

    @SoraEditorApiOverride
    @Deprecated("Use setFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun setTypefaceLineNumber(typefaceLineNumber: Typeface?) {
        super.setTypefaceLineNumber(typefaceLineNumber)
    }

    @SoraEditorApiOverride
    @Deprecated("Use getTheme() instead", level = DeprecationLevel.HIDDEN)
    override fun getColorScheme(): EditorColorScheme {
        return super.getColorScheme()
    }

    @SoraEditorApiOverride
    @Deprecated("Use setTheme() instead", level = DeprecationLevel.HIDDEN)
    override fun setColorScheme(colors: EditorColorScheme) {
        super.setColorScheme(colors)
    }
}

private typealias SoraEditor = io.github.rosemoe.sora.widget.CodeEditor

@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(message = "The API is overridden and inaccessible.")
private annotation class SoraEditorApiOverride