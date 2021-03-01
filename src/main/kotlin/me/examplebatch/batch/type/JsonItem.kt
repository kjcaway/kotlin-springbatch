package me.examplebatch.batch.type

import java.io.Serializable

data class JsonItem(
    var number: Int? = null,
    var data: String? = null
): Serializable
