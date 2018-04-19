package pl.makzyt.exam.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.makzyt.exam.model.ProductType

@Repository
interface ProductTypeRepository : JpaRepository<ProductType, Long>