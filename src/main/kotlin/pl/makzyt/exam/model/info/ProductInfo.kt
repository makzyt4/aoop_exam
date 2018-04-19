package pl.makzyt.exam.model.info

import java.util.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

class ProductInfo {
    var type: String = ""
    var amountLeft: Float = 0F
    var wholePrice: Float = 0F
    var deliveryDate: Date = Date()
}