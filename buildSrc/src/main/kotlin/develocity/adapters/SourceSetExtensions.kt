package develocity.adapters

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.kotlin.dsl.the

fun Project.sourceSetOutput(name: String): SourceSetOutput = the<SourceSetContainer>().getByName(name).output
