package com.cg.model;

import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name ="images")
    private String image;

    @Column(nullable = false)

    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = " boolean default false")
    private boolean deleted;

    public ProductDTO toProductDTO() {
        return new ProductDTO().setId(id)
                .setTitle(title)
                .setQuantity(String.valueOf(quantity))
                .setPrice(price.toString())
                .setImage(image).setCategory(category.toCategoryDTO())
                .setDeleted(deleted);
    }
}
