plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
}

// CocoaPods requires the podspec to have a version.
version = "1.0"

kotlin {
    targets {
        val sdkName: String? = System.getenv("SDK_NAME")

        val isiOSDevice = sdkName.orEmpty().startsWith("iphoneos")
        if (isiOSDevice) {
            iosArm64("iOS")
        } else {
            iosX64("iOS")
        }

        android()
    }

    ios()

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
    }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
                implementation("io.ktor:ktor-client-core:1.3.2")
                implementation("io.ktor:ktor-client-json:1.3.2")
                implementation("io.ktor:ktor-client-logging:1.3.2")
                implementation("io.ktor:ktor-client-serialization:1.3.2")
                implementation("com.squareup.sqldelight:runtime:1.4.2")
            }
        }
        val androidMain by getting{
            dependencies{
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
                implementation("io.ktor:ktor-client-android:1.3.2")
                implementation("io.ktor:ktor-client-core-jvm:1.3.2")
                implementation("io.ktor:ktor-client-json-jvm:1.3.2")
                implementation("io.ktor:ktor-client-logging-jvm:1.3.2")
                implementation("org.slf4j:slf4j-simple:1.7.30")
                implementation("io.ktor:ktor-client-mock-jvm:1.3.2")
                implementation("io.ktor:ktor-client-serialization-jvm:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
                implementation("com.squareup.sqldelight:android-driver:1.4.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.5")
                implementation("io.ktor:ktor-client-ios:1.3.2")
                implementation("io.ktor:ktor-client-core-native:1.3.2")
                implementation("io.ktor:ktor-client-json-native:1.3.2")
                implementation("io.ktor:ktor-client-logging-native:1.3.2")
                implementation("io.ktor:ktor-client-serialization-native:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.20.0")
                implementation("com.squareup.sqldelight:native-driver:1.4.2")
                implementation("io.ktor:ktor-client-mock-native:1.3.2")
            }
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.brunonavarro.shared"
        sourceFolders = listOf("sqldelight")
    }
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}