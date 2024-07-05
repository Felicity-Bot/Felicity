package xyz.stellar.felicity.db.model

import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

data class ServerSettings(
    @BsonId val id: String,
    val serverID: String,
    val logChannel: String
) {
    constructor(
        serverID: String,
        logChannel: String
    ): this(UUID.randomUUID().toString(), serverID, logChannel)
}