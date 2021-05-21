package br.com.dblogic.cartolareports.controller

import br.com.dblogic.cartolareports.service.GitService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.nio.file.Path
import java.nio.file.Paths

@Controller
@RequestMapping("")
class ReportsController {

    private val logger = LoggerFactory.getLogger(ReportsController::class.java)

    @Value("\${github.cartola}")
    lateinit var caRtola: String

    @Autowired
    lateinit var gitService: GitService

    @GetMapping("/hello")
    fun helloworld() : String {
        logger.info("opa")
        return "helloworld"
    }

    @GetMapping("/pwd")
    fun pwd(): String {
        val currentWorkingDir: Path = Paths.get("").toAbsolutePath()
        logger.info("currentWorkingDir: $currentWorkingDir")
        return "helloworld"
    }

    @GetMapping("/pull")
    fun gitPull(): String {
        logger.info("PULL!")
        val currentWorkingDir: Path = Paths.get("").toAbsolutePath()
        val resources = Paths.get("$currentWorkingDir/src/main/resources").toAbsolutePath()
        val caRtola = Paths.get("$resources/$caRtola").toAbsolutePath()

        logger.info("fazendo o pull no diretório $caRtola")
        gitService.gitPull(caRtola)

        return "helloworld"
    }

}