package pl.makzyt.exam.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import pl.makzyt.exam.form.ProductForm
import pl.makzyt.exam.repository.ProductTypeRepository
import pl.makzyt.exam.service.ProductService
import javax.validation.Valid

@Controller
@RequestMapping("/product")
class ProductController {
    @Autowired
    lateinit var productTypeRepository: ProductTypeRepository

    @Autowired
    lateinit var productService: ProductService

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