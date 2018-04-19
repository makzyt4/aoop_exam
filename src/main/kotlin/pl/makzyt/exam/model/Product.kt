package pl.makzyt.exam.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "PRODUCT")
class Product {
    @Id
    @GeneratedValue
    var id: Long = -1
    @ManyToOne
    var type: ProductType? = null
    var deliveryDate: Date = Date()
    var amount: Float = 0F
    var amountTaken: Float = 0F
    var price: Float = 0F
}