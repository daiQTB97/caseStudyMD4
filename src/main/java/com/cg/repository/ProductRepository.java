package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT NEW com.cg.model.dto.ProductDTO(" +
            "p.id, " +
            "p.title, " +
            "p.category, " +
            "p.quantity, " +
            "p.price, " +
            "p.image )" +
            "FROM Product p  WHERE p.deleted = false ")
    List<ProductDTO> findAllProductDTO();


    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.category, " +
            "p.quantity, " +
            "p.price, " +
            "p.image)" +
            "FROM Product AS p " +
            "WHERE p.id= ?1 ")

    Optional<ProductDTO> findProductDTOById(@Param("id")Long id);

    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.category, " +
            "p.quantity, " +
            "p.price, " +
            "p.image" +
            ") " +
            "FROM Product AS p " +
            "JOIN Category AS ca " +
            "ON p.category.id = ca.id " +
            "WHERE p.deleted = false " +
            "AND CONCAT(" +
            "p. id, " +
            "p.title, " +
            "p.image, " +
            "p.quantity, " +
            "p.price, " +
            "ca.title" +
            ") " +
            "LIKE %?1%"
    )
    List<ProductDTO> search(String keyword);

    @Modifying
    @Query("UPDATE Product AS p set p.deleted = true WHERE p.id= :id")
    void deleteProductById(@Param("id") Long id);
}