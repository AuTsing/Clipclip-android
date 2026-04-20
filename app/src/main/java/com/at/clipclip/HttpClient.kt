package com.at.clipclip

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

fun newHttpClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) { json() }
}

sealed interface RequestMessage {

    sealed interface Upload {

        @Serializable
        data class Text(
            val ty: String = "Text",
            val content: String,
        ) : Upload
    }

    @Serializable
    data object Download : RequestMessage
}

fun String.toUploadMessage(): RequestMessage.Upload = RequestMessage.Upload.Text(
    content = this,
)

sealed interface ResponseMessage {

    @Serializable
    data class UploadResult(
        val success: Boolean,
        val message: String,
    ) : ResponseMessage

    @Serializable
    data class DownloadResult(
        val success: Boolean,
        val message: String,
        val clip: String,
    ) : ResponseMessage

    @Serializable
    data class Unknown(
        val success: Boolean,
        val message: String,
    ) : ResponseMessage
}

suspend fun HttpClient.upload(
    addr: String,
    message: RequestMessage.Upload,
): Result<Unit> = runCatching {
    val resp = post(addr) {
        url { appendPathSegments("upload") }
        contentType(ContentType.Application.Json)
        setBody(message)
    }
    val uploadResult = resp.body<ResponseMessage.UploadResult>()
    if (!uploadResult.success) {
        throw Exception(uploadResult.message)
    }
}
