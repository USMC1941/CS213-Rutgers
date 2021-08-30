plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "com.app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("com.app.Calculator")
}
