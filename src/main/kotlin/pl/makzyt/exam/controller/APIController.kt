package pl.makzyt.exam.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.makzyt.exam.model.Product
import pl.makzyt.exam.repository.ProductRepository
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class APIController {
    @Autowired
    lateinit var productRepository: ProductRepository

    @RequestMapping("product/{id}")
    fun productsById(@PathVariable("id") id: Long): Product {
        return productRepository.findById(id).get()
    }

    @RequestMapping("product/type/{typeId}")
    fun productsByDay(@PathVariable("typeId") typeId: Long): MutableList<Product>? {
        val products = productRepository.findAllByOrderByDeliveryDateAsc()
                .stream()
                .filter({
                    p: Product ->
                    p.type!!.id == typeId
                }).collect(Collectors.toList())

        return products
    }
}