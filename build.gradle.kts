import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	id("org.openapi.generator") version "5.3.0"
}

group = "com.initializer"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("io.javalin:javalin:4.5.0")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	implementation("org.aspectj:aspectjrt:1.9.7")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("io.github.microutils:kotlin-logging:2.0.11")
	implementation("javax.validation:validation-api:2.0.1.Final")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

openApiGenerate {
	globalProperties.set(mapOf(
		"apis" to "",
		"models" to ""
	))
	generatorName.set("kotlin-spring")
	inputSpec.set("$projectDir/api/openapi.yaml")
	outputDir.set("$projectDir/src/main/resources/generated")

	configOptions.set(
		mapOf(
			"interfaceOnly" to "true",
		)
	)
}

sourceSets {
	main {
		kotlin {
			srcDir("src/main/kotlin")
		}
	}
}