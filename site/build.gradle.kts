import com.varabyte.kobweb.gradle.application.tasks.KobwebExportTask
import com.varabyte.kobweb.gradle.application.tasks.KobwebStartTask
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    // alias(libs.plugins.kobwebx.markdown)
}

group = "org.example.kobwebtailwind"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("kobwebtailwind")
    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            // implementation(libs.kobwebx.markdown)

            implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.256-kotlin-1.5.31")
            implementation(npm("tailwindcss", "4.1.8"))
            implementation(npm("postcss", "8.5.4"))
            implementation(npm("@tailwindcss/postcss", "4.1.8"))
            implementation(devNpm("postcss-loader", "8.1.1"))
        }
    }
}

val jsWorkspace = "${rootProject.buildDir}/js"
val jsProjectDir = "${jsWorkspace}/packages/${rootProject.name}"


val configurePostCss by tasks.registering(Copy::class) {
    val kotlinNodeJsSetup by rootProject.tasks.getting(NodeJsSetupTask::class)
    val kotlinNpmInstall by rootProject.tasks.getting(KotlinNpmInstallTask::class)

    from("./postcss.config.mjs")
    into(jsProjectDir)
    dependsOn(
        kotlinNodeJsSetup,
        kotlinNpmInstall
    )
}
tasks.named("jsBrowserDevelopmentWebpack") { dependsOn(configurePostCss) }
tasks.named("jsBrowserProductionWebpack") { dependsOn(configurePostCss) }

tasks.named<KobwebStartTask>("kobwebStart") { dependsOn(configurePostCss) }
tasks.named<KobwebExportTask>("kobwebExport") { dependsOn(configurePostCss) }