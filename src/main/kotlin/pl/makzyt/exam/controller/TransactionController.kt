package pl.makzyt.exam.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import pl.makzyt.exam.form.ProductForm
import pl.makzyt.exam.model.info.ProductInfo
import pl.makzyt.exam.repository.ProductRepository
import pl.makzyt.exam.repository.ProductTypeRepository
import pl.makzyt.exam.repository.TransactionRepository
import pl.makzyt.exam.service.ProductService
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/transaction")
class TransactionController {
    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @GetMapping("/showall")
    fun showAllProducts(model: Model): String {
        model.addAttribute("allTransactions", transactionRepository.findAll())

        return "transaction_showall"
    }
}