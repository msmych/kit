package uk.matvey.kit.net

import java.net.URI

object NetKit {

    fun URI.queryParams(name: String) = query
        ?.split('&')
        ?.map { it.split('=') }
        ?.filter { it[0] == name }
        ?.map { it[1] }
        ?: listOf()

    fun URI.queryParamOrNull(name: String) = queryParams(name).firstOrNull()

    fun URI.queryParam(name: String) = requireNotNull(queryParamOrNull(name)) {
        "Query parameter '$name' is missing in $this"
    }
}