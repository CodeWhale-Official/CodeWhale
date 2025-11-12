package com.bluewhaleyt.codewhale.common.extension

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

inline fun <reified T> compositionLocal(
    crossinline defaultFactory: () -> T = { noLocalProvidedFor<T>() }
) = compositionLocalOf { defaultFactory() }

inline fun <reified T> staticCompositionLocal(
    crossinline defaultFactory: () -> T = { noLocalProvidedFor<T>() }
) = staticCompositionLocalOf { defaultFactory() }

@PublishedApi
internal inline fun <reified T> noLocalProvidedFor(): Nothing =
    noLocalProvidedFor(T::class.java.simpleName)

@PublishedApi
internal fun noLocalProvidedFor(name: String): Nothing =
    error("CompositionLocal $name not present")