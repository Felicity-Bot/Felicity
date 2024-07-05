package xyz.stellar.felicity.util

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ConfigurationBuilder
import xyz.stellar.felicity.command.CustomExtension

object ExtensionScanner {
    fun getExtensions(packagePrefix: String): List<CustomExtension> {
        val reflections = Reflections(
            ConfigurationBuilder()
                .forPackages(packagePrefix)
                .addScanners(SubTypesScanner(false)) // Do not exclude Object class
        )

        // Find all classes that implement SlashCommand interface
        val extensionClasses = reflections.getSubTypesOf(CustomExtension::class.java)

        // Instantiate each class and return the list of instances
        return extensionClasses.mapNotNull { extensionClass ->
            try {
                extensionClass.getDeclaredConstructor().newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}