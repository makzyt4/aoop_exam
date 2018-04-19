package pl.makzyt.exam.controller

import org.springframework.stereotype.Controller

@Controller
class IndexController {
    fun index(): String {
        return "index"
    }
}