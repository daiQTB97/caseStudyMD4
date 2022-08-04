package com.cg.model;

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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String urlImage;

    @Column(nullable = false)

    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    private String created_at;

    private String updated_at;

    private int stopSelling;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
