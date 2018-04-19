package pl.makzyt.exam.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "PRODUCT_TYPE")
class ProductType {
    @Id
    @GeneratedValue
    var id: Long = -1
    var name: String = ""
}