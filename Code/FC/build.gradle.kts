// https://proandroiddev.com/migrate-from-groovy-to-kotlin-dsl-951266f3c072

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
