package pl.makzyt.exam.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import pl.makzyt.exam.form.ProductForm
import pl.makzyt.exam.repository.ProductTypeRepository
import pl.makzyt.exam.service.ProductService
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/product")
class ProductController {
    @Autowired
    lateinit var productTypeRepository: ProductTypeRepository

    @Autowired
    lateinit var productService: ProductService

    @InitBinder
    fun datebindingPreparation(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val orderDateEditor: CustomDateEditor = CustomDateEditor(dateFormat, true)
        binder.registerCustomEditor(Date::class.java, orderDateEditor)
    }

    @GetMapping("/add")
    fun addProduct(model: Model): String {
        model.addAttribute("form", ProductForm())
        model.addAttribute("allTypes", productTypeRepository.findAll())

        return "product_add"
    }

    @PostMapping("/add")
    fun addProduct(@Valid @ModelAttribute("form") form: ProductForm,
                   result: BindingResult, model: Model): String {
        if (!result.hasErrors()) {
            model.addAttribute("success", true)

            val product = productService.formToProduct(form)
            productService.addProduct(product)
        }

        model.addAttribute("allTypes", productTypeRepository.findAll())

        return "product_add"
    }
}