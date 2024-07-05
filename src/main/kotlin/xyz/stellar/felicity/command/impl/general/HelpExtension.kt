package xyz.stellar.felicity.command.impl.general

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.converters.impl.stringChoice
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import dev.kord.rest.builder.message.embed
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension
import xyz.stellar.felicity.scannedExtensions

class HelpExtension : CustomExtension(CommandCategory.GENERAL) {
    override val name: String = "help"

    override suspend fun setup() {
        publicSlashCommand(::HelpArgs) {
            name = "help"
            description = "Shows commands by their category"

            action {
                val categoryOption = event.interaction.command.options["category"]?.value as String
                val category = CommandCategory.valueOf(categoryOption.uppercase())

                respond {
                    embed {
                        title = "${category.categoryName} Commands"
                        description = scannedExtensions.filter { it.category == category }.joinToString("\n") { extension ->
                            extension.slashCommands.joinToString("\n") { command ->
                                val subCommands = command.subCommands.joinToString("\n") { subCommand ->
//                                    "> `${subCommand.name}` - ${subCommand.description}"
                                    "> `/${command.name} ${subCommand.name}` - ${subCommand.description}"
                                }

                                buildString {
                                    append("`/${command.name}` - ${command.description}")
                                    if (subCommands.isNotEmpty()) {
                                        append("\n$subCommands")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    inner class HelpArgs : Arguments() {
        val category = stringChoice {
            name = "category"
            description = "The category of commands"

            val map = mutableMapOf<String, String>()
            CommandCategory.entries.forEach {
                map[it.categoryName] = it.name.lowercase()
            }

            choices = map
        }
    }
}
