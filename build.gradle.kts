plugins {
	java
	id("org.springframework.boot") version "2.7.10"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id ("java")
	id("com.avast.gradle.docker-compose") version "0.14.3"
	id("jacoco")
	id("org.sonarqube") version "3.3"
}

group = "com.hm"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

sonarqube {
	properties {
		property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.exclusions", "src/main/java/com/hm/alianza/entity/*,src/main/java/com/hm/alianza/dto/*,src/main/java/com/hm/alianza/config/*,src/main/java/com/hm/alianza/exceptions/*,src/main/java/com/hm/alianza/common/*,src/main/java/com/hm/alianza/AlianzaApplication.java")
		property("sonar.coverage.exclusions", "src/main/java/com/hm/alianza/entity/*,src/main/java/com/hm/alianza/dto/*,src/main/java/com/hm/alianza/config/*,src/main/java/com/hm/alianza/exceptions/*,src/main/java/com/hm/alianza/common/*,src/main/java/com/hm/alianza/AlianzaApplication.java")
	}
}