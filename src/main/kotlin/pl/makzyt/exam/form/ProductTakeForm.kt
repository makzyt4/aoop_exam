package pl.makzyt.exam.form

import java.util.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

class ProductTakeForm {
    @Min(value = 0, message = "{validation.invalidProductType}")
    var typeId: Long = -1
    @DecimalMin(value = "0.1", message = "{validation.minAmount}")
    var amount: Float = 0F
    var takingDate: Date = Date()
}