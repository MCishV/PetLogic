plugins {
    id("java")
}

group = "dev.mcishv.PetLogic"
version = "1.0-DEV BUILD-12"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
    maven {
        name = "minecraft-repo"
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    implementation("com.google.guava:guava:32.0.1-android")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("com.mojang:authlib:1.5.21")
}
