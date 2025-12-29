import com.google.protobuf.gradle.id

plugins {
  id("java")
  id("maven-publish")
  id("io.freefair.lombok") version "9.1.0"
  id("com.google.protobuf") version "0.9.5"
}

group = "io.poddeck"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_25
java.targetCompatibility = JavaVersion.VERSION_25

publishing {
  publications {
    register<MavenPublication>("gpr") {
      from(components["java"])
    }
  }
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/poddeck/poddeck-common")
      credentials {
        username = System.getenv("GITHUB_USERNAME") ?: findProperty("github.username") as String?
        password = System.getenv("GITHUB_TOKEN") ?: findProperty("github.token") as String?
      }
    }
  }
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:6.0.1"))
  testImplementation("org.junit.jupiter:junit-jupiter:6.0.1")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.1")

  implementation("com.google.guava:guava:33.5.0-jre")

  implementation("org.projectlombok:lombok:1.18.42")
  annotationProcessor("org.projectlombok:lombok:1.18.42")
  testImplementation("org.projectlombok:lombok:1.18.42")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.42")

  implementation("org.apache.commons:commons-configuration2:2.13.0")
  implementation("commons-beanutils:commons-beanutils:1.11.0")

  implementation("io.grpc:grpc-stub:1.78.0")
  implementation("io.grpc:grpc-protobuf:1.78.0")
  implementation("io.grpc:grpc-netty:1.78.0")
  implementation("com.google.protobuf:protobuf-java:4.33.2")
}

tasks.test {
  useJUnitPlatform()
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:4.33.2"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:1.78.0"
    }
  }
  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc")
      }
    }
  }
}

sourceSets {
  main {
    java.srcDir("build/generated/source/proto/main/java")
    java.srcDir("build/generated/source/proto/main/grpc")
  }
}