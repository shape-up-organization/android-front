package com.shapeup.api.services.users

import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter

@Serializable(with = DateSerializer::class)
data class UserFieldPayload(
    val email: String,
    val name: String,
    val lastName: String,
    val cellPhone: String,
    val birth: LocalDate,
    val biography: String,
    val username: String,
    val password: String,
)

@Serializable
data class SearchByUsernamePayload(
    val username: String
)

@Serializable
data class SearchByFullNamePayload(
    val fullName: String
)

@Serializer(forClass = LocalDate::class)
object DateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}