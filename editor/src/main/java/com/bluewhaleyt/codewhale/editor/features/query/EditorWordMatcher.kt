package com.bluewhaleyt.codewhale.editor.features.query

import com.bluewhaleyt.codewhale.editor.BaseEditor
import io.github.rosemoe.sora.text.CharPosition

@PublishedApi
internal class EditorWordMatcher(private val editor: BaseEditor) {

    private val text
        get() = editor.text
    private val indexer
        get() = editor.text.indexer

    internal fun getWordRanges(line: Int, column: Int): List<Pair<CharPosition, CharPosition>> {
        val word = getWord(line, column) ?: return emptyList()

        val pattern = "\\b${Regex.escape(word)}\\b".toRegex()
        val result = mutableListOf<Pair<CharPosition, CharPosition>>()

        pattern.findAll(text).forEach { matchResult ->
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last + 1

            val startPos = indexer.getCharPosition(startIndex)
            val endPos = indexer.getCharPosition(endIndex)

            result += startPos to endPos
        }

        return result
    }

    private fun getWord(line: Int, column: Int): String? {
        val cursorIndex = try {
            indexer.getCharIndex(line, column)
        } catch (e: Exception) {
            return null
        }
        if (cursorIndex !in text.indices) return null

        val start = text.findWordStartIndex(cursorIndex)
        val end = text.findWordEndIndex(cursorIndex)
        return if (start < end) text.substring(start, end) else null
    }

    private fun CharSequence.findWordStartIndex(pos: Int): Int {
        var i = pos.coerceAtMost(lastIndex)
        while (i > 0 && this[i - 1].isWordChar()) i--
        return i
    }

    private fun CharSequence.findWordEndIndex(pos: Int): Int {
        var i = pos.coerceAtMost(lastIndex)
        while (i < length && this[i].isWordChar()) i++
        return i
    }

    private fun Char.isWordChar(): Boolean =
        this.isLetterOrDigit() || this == '_' || this in '\u200C'..'\u200D'
}