package xyz.stellar.felicity

import com.kotlindiscord.kord.extensions.ExtensibleBot

import dev.kord.common.entity.PresenceStatus
import dev.kord.core.Kord
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.*
import xyz.stellar.felicity.util.ExtensionScanner

const val packagePrefix = "xyz.stellar.felicity.command.impl"
var scannedExtensions = ExtensionScanner.getExtensions(packagePrefix)

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val bot = ExtensibleBot(TOKEN) {
        extensions {
            scannedExtensions.forEach {
                add { it }
                println("Loaded extension: ${it.name}")
            }
        }
    }

    GlobalScope.launch {
        delay(5 * 1000L) // 5 seconds
        while (isActive) {
            updateStatus(bot.kordRef)
            delay(5 * 60 * 1000) // 5 minutes
        }
    }

    bot.start()
}

suspend fun updateStatus(kord: Kord) {
    val guilds = kord.guilds.toList()
    val serverCount = guilds.size
    val memberCount = guilds.sumOf { it.memberCount ?: 0 }

    kord.editPresence {
        status = PresenceStatus.Online
        state = "$serverCount servers | $memberCount members"
    }

    println("Updated status to: $serverCount servers | $memberCount members")
}