package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.repository.CategoryRepository;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.delete(getById(id));
    }

    @Override
    public List<ProductDTO> findAllProductDTO() {
        return productRepository.findAllProductDTO();
    }

    @Override
    public List<ProductDTO> searchAllByWord(String word) {
        return productRepository.search(word);
    }

    @Override
    public Optional<ProductDTO> findProductDTOById(Long id) {
        return productRepository.findProductDTOById(id);
    }

    @Override
    public Boolean exitsByIdProduct(Long id) {
        return productRepository.existsById(id);
    }


    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
    }

    @Override
    public void deleteProductSoft(Product product) {
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Boolean existsByTitleAndIdIsNot(String title, Long id) {
        return existsByTitleAndIdIsNot(title, id);
    }
}
