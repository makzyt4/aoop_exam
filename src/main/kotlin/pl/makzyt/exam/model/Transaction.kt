package pl.makzyt.exam.model

import javax.persistence.*

@Entity
@Table(name = "TRANSACTION")
class Transaction {
    @Id
    @GeneratedValue
    var id: Long = -1
    @ManyToOne
    var type: ProductType? = null
    var amount: Float = 0F
    var price: Float = 0F
}