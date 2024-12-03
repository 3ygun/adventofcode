import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.*

// This stuff was mainly based off the generated project from: https://start.ktor.io
// Commands:
//   gradle compileKotlin
//   gradle clean test
//   gradle run --args='-year YEAR -all'
//   gradle build --continuous

// These are set in gradle.properties
val kotlin_version: String by project // Need to update in file and in the `plugins` section
val kotlinCoroutinesVersion: String by project
val kotestVersion: String by project

plugins {
	id("application")
	kotlin("jvm").version("2.1.0")
}

group = "adventofcode"
version = "0.2.0"
description = "Advent of Code Challenges"

application {
	mainClass = "adventofcode.MainKt"
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")

	testImplementation(kotlin("test"))
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

kotlin {
	jvmToolchain(21)
}

// CompileOptions
// https://kotlinlang.org/docs/reference/using-gradle.html#compiler-options
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
}

// Setup testing
tasks.withType<Test>().configureEach {
	testLogging {
		showExceptions = true
		showCauses = true

		// This line adds the "expected X but was Y" messages
		exceptionFormat = TestExceptionFormat.FULL

		events.add(TestLogEvent.FAILED)
		events.add(TestLogEvent.SKIPPED)
	}
	useJUnitPlatform()
}
