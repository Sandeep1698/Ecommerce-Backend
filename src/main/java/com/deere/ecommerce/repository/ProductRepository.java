package com.deere.ecommerce.repository;

import com.deere.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p "+
    "WHERE (p.category.name = :category OR :category = '') "+
    "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "+
    "AND(:minDiscount IS NULL OR p.discountPercent >= :minDiscount) "+
    "ORDER BY "+
    "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, "+
    "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
    public List<Product> filterProducts(@Param("category")String category,
                                        @Param("minPrice")Integer minPrice,
                                        @Param("maxPrice")Integer maxPrice,
                                        @Param("minDiscount")Integer minDiscount,
                                        @Param("sort")String sort);
}
