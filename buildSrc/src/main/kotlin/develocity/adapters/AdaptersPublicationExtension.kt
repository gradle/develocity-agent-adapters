package develocity.adapters

import org.gradle.api.provider.Property

interface AdaptersPublicationExtension {
    val name: Property<String>
    val description: Property<String>
}