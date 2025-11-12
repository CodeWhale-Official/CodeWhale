package com.bluewhaleyt.codewhale.common.extension

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder

val json = json()
val json5 = json5()

fun json5(builder: JsonBuilder.() -> Unit = {}) =
    json {
        isLenient = true
        allowComments = true
        allowTrailingComma = true
        builder()
    }

fun json(builder: JsonBuilder.() -> Unit = {}) =
    Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        prettyPrintIndent = indent(2)
        builder()
    }

@PublishedApi
internal fun indent(size: Int) = " ".repeat(size)