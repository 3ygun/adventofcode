import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// This stuff was mainly based off the generated project from: https://start.ktor.io
// Commands:
//   gradle compileKotlin
//   gradle clean test
//   gradle run --args='-year YEAR -all'

// These are set in gradle.properties
val kotlin_version: String by project // Need to update in file and in the `plugins` section
val kotlinCoroutinesVersion: String by project
val kotlintestVersion: String by project

plugins {
	application
	kotlin("jvm") version "1.3.61"
}

group = "adventofcode"
version = "0.1.1"
description = "Advent of Code Challenges"

application {
	mainClassName = "adventofcode.MainKt"
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")

	testImplementation(kotlin("test"))
	testImplementation(kotlin("test-junit"))
	testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlintestVersion")
}

// Source Sets
kotlin.sourceSets["main"].kotlin.srcDirs("main")
kotlin.sourceSets["test"].kotlin.srcDirs("test")
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("resources")

// CompileOptions
// https://kotlinlang.org/docs/reference/using-gradle.html#compiler-options
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

// Setup testing
tasks {
	test {
		testLogging.showExceptions = true
		useJUnitPlatform()

		reports {
			junitXml.isEnabled = false
			html.isEnabled = true
		}

		// On "gradle clean test" prints the test name that hits the following events
		// Otherwise you need to look at some file?
		testLogging {
			// events.add(TestLogEvent.PASSED)
			events.add(TestLogEvent.SKIPPED)
			events.add(TestLogEvent.FAILED)
		}
	}
}
