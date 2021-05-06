package br.com.dblogic.cartolareports.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class ReportsController {

    private val logger = LoggerFactory.getLogger(ReportsController::class.java)

    @GetMapping("/hello")
    fun helloworld() : String {
        logger.info("opa")
        return "helloworld"
    }

}