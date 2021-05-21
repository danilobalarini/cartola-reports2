package br.com.dblogic.cartolareports.configuration

import br.com.dblogic.cartolareports.service.GitService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors




@Configuration
class InitialConfiguration {

    private val logger = LoggerFactory.getLogger(InitialConfiguration::class.java)

    @Value("\${game.year}")
    lateinit var year: String

    @Value("\${github.cartola}")
    lateinit var caRtola: String

    @Value("\${github.url}")
    lateinit var caRtolaURL: String

    @Autowired
    lateinit var gitService: GitService

    @Bean
    fun initDataDirectory() = CommandLineRunner {

        logger.info("initDataDirectory()")

        val currentWorkingDir: Path = Paths.get("").toAbsolutePath()
        val resources = Paths.get("$currentWorkingDir/src/main/resources").toAbsolutePath()
        val caRtola = Paths.get("$resources/$caRtola").toAbsolutePath()

        logger.info("currentWorkingDir: $currentWorkingDir")
        logger.info("resources: $resources")

        val isDirectory = Files.isDirectory(caRtola)

        if(!isDirectory) {
            logger.info("git clone no $caRtola")
            //File("$caRtola").mkdirs()
            gitService.gitClone(caRtola, caRtolaURL)
        } else {
            logger.info("já tem um diretório chamado caRtola")
        }

    }



}