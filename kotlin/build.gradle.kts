import org.gradle.kotlin.dsl.*

// This stuff was mainly based off the generated project from: https://start.ktor.io
// Commands:
//   gradle compileKotlin
//   gradle clean test
//   gradle run --args='-year YEAR -all'

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
	useJUnitPlatform()
}
//	test {
//		testLogging.showExceptions = true
//		useJUnitPlatform()
//
//		reports {
//			junitXml.isEnabled = false
//			html.isEnabled = true
//		}
//
//		// On "gradle clean test" prints the test name that hits the following events
//		// Otherwise you need to look at some file?
//		testLogging {
//			// events.add(TestLogEvent.PASSED)
//			events.add(TestLogEvent.SKIPPED)
//			events.add(TestLogEvent.FAILED)
//		}
//	}
//}
