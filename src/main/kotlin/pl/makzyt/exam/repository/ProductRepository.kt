package pl.makzyt.exam.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.makzyt.exam.model.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>