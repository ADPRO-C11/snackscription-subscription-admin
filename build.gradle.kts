plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.sonarqube") version "4.4.1.3373"
}

group = "snackscription"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.9.1"
val springDotenvVersion = "4.0.0"
val springBootVersion = "3.2.5"
val jjwtVersion = "0.12.5"
val prometheusVersion = "1.12.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus:$prometheusVersion")
	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
	testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
	testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
	implementation("me.paulschwarz:spring-dotenv:$springDotenvVersion")
}

sonar {
	properties {
		property("sonar.projectKey", "ADPRO-C11_snackscription-subscription-admin")
		property("sonar.organization", "adpro-c11")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"

	filter {
		excludeTestsMatching("*FunctionalTest")
	}
}

tasks.register<Test>("functionalTest") {
	description = "Runs functional tests."
	group = "verification"

	filter {
		includeTestsMatching("*FunctionalTest")
	}
}

tasks.withType<Test>().configureEach() {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	classDirectories.setFrom(files(classDirectories.files.map {
		fileTree(it) { exclude("**/*Application**") }
	}))
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required.set(false)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}