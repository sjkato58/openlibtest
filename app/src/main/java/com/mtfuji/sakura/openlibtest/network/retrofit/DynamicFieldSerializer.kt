package com.mtfuji.sakura.openlibtest.network.retrofit

import com.mtfuji.sakura.openlibtest.data.models.Description
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveKind.*
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object DynamicFieldSerializer: KSerializer<Any?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DynamicField", STRING)

    override fun serialize(encoder: Encoder, value: Any?) {
        when (value) {
            is String -> encoder.encodeString(value)
            is Description -> encoder.encodeSerializableValue(Description.serializer(), value)
            else -> encoder.encodeNull()
        }
    }

    override fun deserialize(decoder: Decoder): Any? {
        val jsonInput = decoder as? JsonDecoder ?: throw SerializationException("Expected Json input")
        val element = jsonInput.decodeJsonElement()

        return when {
            element is JsonObject -> Json.decodeFromJsonElement(Description.serializer(), element)
            element is JsonPrimitive && element.isString -> element.content
            else -> null
        }
    }
}