package pl.makzyt.exam.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.makzyt.exam.form.ProductForm
import pl.makzyt.exam.model.Product
import pl.makzyt.exam.model.ProductType
import pl.makzyt.exam.model.Transaction
import pl.makzyt.exam.repository.ProductRepository
import pl.makzyt.exam.repository.ProductTypeRepository
import pl.makzyt.exam.repository.TransactionRepository

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productTypeRepository: ProductTypeRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    fun addProduct(product: Product) {
        productRepository.save(product)

        val transaction = Transaction()
        transaction.amount = product.amount
        transaction.price = product.price
        transaction.type = product.type

        transactionRepository.save(transaction)
    }

    fun takeProduct(type: ProductType, amount: Float): Float {
        val products = productRepository.findAllByOrderByDeliveryDateAsc()
        var price = 0F
        var availableAmount = 0F
        var amountLeft = amount

        // Check if there are enough products in the storage
        for (product in products) {
            if (product.type != type) {
                continue
            }

            availableAmount += product.amount - product.amountTaken
        }

        // Return 0 if there are not enough products in the storage
        if (availableAmount < amount) {
            return 0F
        }

        // Take products from the storage
        for (product in products) {
            if (product.type != type) {
                continue
            }

            val currentAmount = product.amount - product.amountTaken
            val amountTaken = if (amount > currentAmount) currentAmount else amountLeft

            price += amountTaken * product.price
            amountLeft -= amountTaken

            product.amountTaken += amountTaken

            productRepository.save(product)

            if (amountLeft <= 0) {
                break
            }
        }

        val transaction = Transaction()
        transaction.amount = -amount
        transaction.price = price
        transaction.type = type

        transactionRepository.save(transaction)

        return price
    }

    fun formToProduct(form: ProductForm): Product {
        val productType = productTypeRepository.findById(form.typeId).get()

        val product = Product()
        product.amount = form.amount
        product.price = form.price
        product.type = productType
        product.deliveryDate = form.deliveryDate

        return product
    }
}