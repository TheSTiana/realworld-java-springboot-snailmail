dependencies {
    implementation(project(":core"))

    runtimeOnly("org.postgresql:postgresql:42.5.4")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
}
