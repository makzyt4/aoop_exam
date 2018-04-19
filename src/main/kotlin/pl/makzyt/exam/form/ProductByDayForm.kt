package pl.makzyt.exam.form

import java.util.*
import javax.validation.constraints.Min

class ProductByDayForm {
    @Min(value = 0, message = "{validation.invalidProductType}")
    var typeId: Long = -1
    var deliveryDate: Date = Date()
}