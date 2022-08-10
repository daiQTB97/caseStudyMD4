package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {
    List<ProductDTO> findAllProductDTO();

    List<ProductDTO> searchAllByWord(String word);

    Optional<ProductDTO> findProductDTOById(Long id);

    Boolean exitsByIdProduct(Long id);

    void deleteProductById(Long id);

    void deleteProductSoft(Product product);
    Boolean existsByTitleAndIdIsNot(String title, Long id);


}
