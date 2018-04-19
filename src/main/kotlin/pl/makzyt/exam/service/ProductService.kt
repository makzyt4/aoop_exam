package pl.makzyt.exam.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.makzyt.exam.model.Product
import pl.makzyt.exam.model.ProductType
import pl.makzyt.exam.model.Transaction
import pl.makzyt.exam.repository.ProductRepository
import pl.makzyt.exam.repository.TransactionRepository

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

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
        val products = productRepository.findAll()
        var price = 0F
        var amountTaken = 0F
        var availableAmount = 0F

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

            if (amount >= currentAmount) {
                product.amountTaken = product.amount
            } else {
                product.amountTaken += amount
            }

            amountTaken = currentAmount
            price += amountTaken * product.price

            productRepository.save(product)
        }

        if (amountTaken < amount) {
            price = 0F
        }

        val transaction = Transaction()
        transaction.amount = -amount
        transaction.price = price
        transaction.type = type

        transactionRepository.save(transaction)

        return price
    }
}