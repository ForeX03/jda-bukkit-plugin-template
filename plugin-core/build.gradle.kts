import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {

    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    // -- dream-platform --
    implementation("cc.dreamcode.platform:bukkit:1.13.4")
    implementation("cc.dreamcode.platform:bukkit-config:1.13.4")
    implementation("cc.dreamcode.platform:dream-command:1.13.4")
    implementation("cc.dreamcode.platform:persistence:1.13.4")
    implementation("cc.dreamcode.platform:bukkit-hook:1.13.4")

    // -- dream-utilties --
    implementation("cc.dreamcode:utilities-adventure:1.5.8")

    // -- dream-notice --
    implementation("cc.dreamcode.notice:bukkit:1.7.4")
    implementation("cc.dreamcode.notice:bukkit-serializer:1.7.4")

    // -- dream-command --
    implementation("cc.dreamcode.command:bukkit:2.2.3")

    // -- dream-menu --
    implementation("cc.dreamcode.menu:bukkit:1.4.5")
    implementation("cc.dreamcode.menu:bukkit-serializer:1.4.5")

    // -- tasker (easy sync/async scheduler) --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")

    // -- JDA --
    implementation("net.dv8tion:JDA:5.3.2") {
        exclude(module="opus-java") //voice stuff
    }
}

tasks.withType<ShadowJar> {

    archiveFileName.set("Dream-JDATemplate-${project.version}.jar")

    relocate("com.cryptomorin", "cc.dreamcode.jdatemplate.libs.com.cryptomorin")
    relocate("eu.okaeri", "cc.dreamcode.jdatemplate.libs.eu.okaeri")
    relocate("net.kyori", "cc.dreamcode.jdatemplate.libs.net.kyori")

    relocate("cc.dreamcode.platform", "cc.dreamcode.jdatemplate.libs.cc.dreamcode.platform")
    relocate("cc.dreamcode.utilities", "cc.dreamcode.jdatemplate.libs.cc.dreamcode.utilities")
    relocate("cc.dreamcode.menu", "cc.dreamcode.jdatemplate.libs.cc.dreamcode.menu")
    relocate("cc.dreamcode.command", "cc.dreamcode.jdatemplate.libs.cc.dreamcode.command")
    relocate("cc.dreamcode.notice", "cc.dreamcode.jdatemplate.libs.cc.dreamcode.notice")

    relocate("org.bson", "cc.dreamcode.jdatemplate.libs.org.bson")
    relocate("com.mongodb", "cc.dreamcode.jdatemplate.libs.com.mongodb")
    relocate("com.zaxxer", "cc.dreamcode.jdatemplate.libs.com.zaxxer")
    relocate("org.slf4j", "cc.dreamcode.jdatemplate.libs.org.slf4j")
    relocate("org.json", "cc.dreamcode.jdatemplate.libs.org.json")
    relocate("com.google.gson", "cc.dreamcode.jdatemplate.libs.com.google.gson")

}