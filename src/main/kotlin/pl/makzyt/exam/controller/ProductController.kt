package pl.makzyt.exam.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import pl.makzyt.exam.form.ProductForm
import pl.makzyt.exam.form.ProductTakeForm
import pl.makzyt.exam.model.info.ProductInfo
import pl.makzyt.exam.repository.ProductRepository
import pl.makzyt.exam.repository.ProductTypeRepository
import pl.makzyt.exam.service.ProductService
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/product")
class ProductController {
    @Autowired
    lateinit var productRepository: ProductRepository

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

    @GetMapping("/showall")
    fun showAllProducts(model: Model): String {
        val products = productRepository.findAllByOrderByDeliveryDateAsc()
        val tableRows = arrayListOf<ProductInfo>()

        for (product in products) {
            val info = ProductInfo()
            info.amountLeft = product.amount - product.amountTaken
            info.deliveryDate = product.deliveryDate
            info.type = product.type!!.name
            info.wholePrice = info.amountLeft * product.price

            tableRows.add(info)
        }

        model.addAttribute("infos", tableRows)

        return "product_showall"
    }

    @GetMapping("/take")
    fun takeProducts(model: Model): String {
        model.addAttribute("allTypes", productTypeRepository.findAll())
        model.addAttribute("form", ProductTakeForm())

        return "product_take"
    }

    @PostMapping("/take")
    fun takeProducts(@Valid @ModelAttribute("form") form: ProductTakeForm,
                     result: BindingResult, model: Model): String {
        model.addAttribute("allTypes", productTypeRepository.findAll())

        if (!result.hasErrors()) {
            val type = productTypeRepository.findById(form.typeId).get()
            val price = productService.takeProduct(type, form.amount)

            model.addAttribute("failure", price <= 0)

            if (price > 0) {
                model.addAttribute("wholePrice", price)
                model.addAttribute("type", type)
                return "product_summary"
            }
        }

        return "product_take"
    }

}