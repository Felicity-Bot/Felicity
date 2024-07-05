package xyz.stellar.felicity.command.impl.utility

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import dev.kord.core.behavior.interaction.respondEphemeral
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension

class ReminderExtension : CustomExtension(CommandCategory.UTILITY) {
    override val name: String = "reminder"

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun setup() {
        publicSlashCommand(::ReminderArgs) {
            name = "reminder"
            description = "Sets a reminder"

            action {
                val timeString = event.interaction.command.strings["time"]
                val message = event.interaction.command.strings["message"]

                val delayMillis = convertTimeStringToMillis(timeString!!)
                if (delayMillis <= System.currentTimeMillis()) {
                    event.interaction.respondEphemeral {
                        content = "Invalid time format. Example: 1h10m5s"
                    }
                    return@action
                }

                GlobalScope.launch {
                    delay(delayMillis - System.currentTimeMillis())

                    user.getDmChannelOrNull()?.createMessage("Reminder: $message")
                }

                respond {
                    content = "Reminder set! I will remind you in $timeString. Make sure you have DMs enabled!"
                }
            }
        }
    }

    private fun convertTimeStringToMillis(time: String): Long {
        val regex = Regex("(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?")
        val matchResult = regex.matchEntire(time)

        if (matchResult != null) {
            val (days, hours, minutes, seconds) = matchResult.destructured
            val daysInMillis = days.removeSuffix("d").toLongOrNull()?.times(86400000) ?: 0
            val hoursInMillis = hours.removeSuffix("h").toLongOrNull()?.times(3600000) ?: 0
            val minutesInMillis = minutes.removeSuffix("m").toLongOrNull()?.times(60000) ?: 0
            val secondsInMillis = seconds.removeSuffix("s").toLongOrNull()?.times(1000) ?: 0

            return System.currentTimeMillis() + daysInMillis + hoursInMillis + minutesInMillis + secondsInMillis
        }

        return 0
    }

    inner class ReminderArgs : Arguments() {
        val message by string {
            name = "message"
            description = "The message to send"
        }

        val time by string {
            name = "time"
            description = "The time to send the message. Format: XdYhZmWs"
        }
    }
}