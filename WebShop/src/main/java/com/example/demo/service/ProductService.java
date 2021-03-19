package com.example.demo.service;


import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product, MultipartFile image) throws IOException;

    Optional<Product> edit(Long id, Product product) throws IOException;

    void deleteById(Long id);

    List<Product> listProductsByNameLikeAndCategoryAndManufacturer(String name, Category category, Manufacturer manufacturer);

    Page<Product> findPaginated(int pageNo, int pageSize);

}
