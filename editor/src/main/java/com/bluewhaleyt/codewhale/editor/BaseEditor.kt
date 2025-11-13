package com.bluewhaleyt.codewhale.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.runtime.getValue
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
import com.bluewhaleyt.codewhale.editor.theme.EditorTheme
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.lang.styling.inlayHint.InlayHintsContainer
import io.github.rosemoe.sora.widget.subscribeAlways
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("ViewConstructor")
abstract class BaseEditor internal constructor(
    context: Context,
    themeDefinition: ThemeDefinition
) : SoraEditor(context, null, 0, 0) {

    private val scope = CoroutineScope(Dispatchers.Main)

    var fontFamily: FontFamily = FontFamily.Default
        set(value) {
            field = value
            val resolver = createFontFamilyResolver(context)
            val typeface by resolver.resolveAsTypeface(value)
            super.setTypefaceText(typeface)
            super.setTypefaceLineNumber(typeface)
        }

    var placeholder: String? = null

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        fontFamily = FontFamily.Default
        colorScheme = EditorTheme(themeDefinition)

        props.apply {
            drawSideBlockLine = false
            singleDirectionFling = false
        }

        this.post {
            requestFocus()
            registerInlayHintRenderer(EditorPlaceholderRenderer.instance)

            showPlaceholder(text.isEmpty())
            subscribeAlways<ContentChangeEvent> { event ->
                post {
                    showPlaceholder(event.editor.text.isEmpty())
                }
            }
        }
    }

    private fun showPlaceholder(textEmpty: Boolean) {
        scope.launch {
            inlayHints = if (textEmpty && inlayHints == null) {
                placeholder?.let {
                    with(Dispatchers.Main) {
                        delay(1.milliseconds)
                        InlayHintsContainer().apply {
                            add(EditorPlaceholder(it))
                        }
                    }
                }
            } else null
        }
    }

    @SoraEditorApiOverride
    @Deprecated("Use setFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun setTypefaceText(typefaceText: Typeface?) {
        super.setTypefaceText(typefaceText)
    }

    @SoraEditorApiOverride
    @Deprecated("Use setFontFamily() instead", level = DeprecationLevel.HIDDEN)
    override fun setTypefaceLineNumber(typefaceLineNumber: Typeface?) {
        super.setTypefaceLineNumber(typefaceLineNumber)
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
}

private typealias SoraEditor = io.github.rosemoe.sora.widget.CodeEditor

@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(message = "The API is overridden and inaccessible.")
private annotation class SoraEditorApiOverride