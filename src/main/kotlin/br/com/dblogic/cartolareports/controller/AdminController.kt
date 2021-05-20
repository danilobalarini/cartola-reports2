package br.com.dblogic.cartolareports.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController {

    private val logger = LoggerFactory.getLogger(AdminController::class.java)

    @GetMapping("")
    fun admindex() : String {
        logger.info("entramos no index do admin!")
        return "adm_index"
    }


}