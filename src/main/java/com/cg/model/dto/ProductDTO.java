package com.cg.model.dto;


import com.cg.model.Category;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ProductDTO implements Validator {

    private Long id;


    private String title;

    @Valid
    private CategoryDTO category;

    private String quantity;

    private String price;



    private String image;

    @Column(columnDefinition = " boolean default false")
    private boolean deleted;

    public ProductDTO(Long id, String title, Category category, int quantity, BigDecimal price, String image) {
        this.id = id;
        this.title = title;
        this.category = category.toCategoryDTO();
        this.quantity = String.valueOf(quantity);
        this.price = price.toString();
        this.image = image;
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setCategory(category.toCategory())
                .setQuantity(Integer.parseInt(quantity))
                .setPrice(new BigDecimal(price))
                .setImage(image)
                .setDeleted(deleted);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;

        String TitleCheck = productDTO.getTitle();
        String PriceCheck = productDTO.getPrice();
        String QuantityCheck = productDTO.getQuantity();
        String Category = String.valueOf(productDTO.getCategory());

        if ((TitleCheck.trim().isEmpty())){
            errors.rejectValue("title", "title.isEmpty", "T??n s???n ph???m kh??ng ???????c ????? tr???ng");
            return;
        }

        if ((PriceCheck.trim()).isEmpty()) {
            errors.rejectValue("price", "price.isEmpty", "Vui l??ng nh???p gi?? s???n ph???m");
            return;
        }

        if ((Long.parseLong(PriceCheck) < 0)) {
            errors.rejectValue("price", "price.isEmpty", "Gi?? s???n ph???m ph???i l???n h??n 0");
            return;
        }

        if ((Long.parseLong(PriceCheck) > 999999999)) {
            errors.rejectValue("price", "price.isEmpty", "Gi?? s???n ph???m kh??ng ???????c l???n h??n 999999999 vn??");
            return;
        }

        if ((QuantityCheck.trim()).isEmpty()) {
            errors.rejectValue("quantity", "quantity.isEmpty", "B???n ph???i nh???p s??? l?????ng s???n ph???m, kh??ng th?? l???y g?? b??n");
            return;
        }

        if ((Long.parseLong(QuantityCheck) < 0)) {
            errors.rejectValue("quantity", "quantity.isEmpty", "S??? l?????ng s???n ph???m ph???i l???n h??n 0");
            return;
        }

        if ((Category.trim()).isEmpty()) {
            errors.rejectValue("category", "category.isEmpty", "Tr?????ng n??y kh??ng t???n t???i");
            return;
        }

        if ((TitleCheck.length() < 3 || TitleCheck.length() > 255)) {
            errors.rejectValue("title", "title.length", "T??n T??? 3 ?????n 255 K?? T???");
            return;
        }

    }
}
