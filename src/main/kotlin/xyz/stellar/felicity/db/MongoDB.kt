package xyz.stellar.felicity.db

import com.mongodb.kotlin.client.coroutine.MongoClient
import xyz.stellar.felicity.db.model.ServerSettings

object MongoDB {
    val client = MongoClient.create("mongodb://localhost:27017")
    val database = client.getDatabase("felicity")

    // collections
    val serverSettings = database.getCollection<ServerSettings>("server_settings")
}