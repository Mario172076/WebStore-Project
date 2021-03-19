package com.example.demo.service.impl;


import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;

import com.example.demo.model.exceptions.CategoryNotFoundException;
import com.example.demo.model.exceptions.InvalidArgumentsException;
import com.example.demo.model.exceptions.ManufacturerNotFoundException;
import com.example.demo.model.exceptions.ProductNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }



    @Override
    public Product save(Product product, MultipartFile image) throws IOException {
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId()).orElseThrow(() -> new InvalidArgumentsException());
        Category category = this.categoryService.findById(product.getCategory().getId()).
                orElseThrow(() -> new InvalidArgumentsException());
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//            product.setImage(image);
            product.setImageBase64(base64Image);
        }
        return this.productRepository.save(product);
    }






    @Override
    public Optional<Product> edit(Long id, Product product) throws IOException {
        Product p = this.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId()).orElseThrow(() -> new ManufacturerNotFoundException(id));
        Category category = this.categoryService.findById(product.getCategory().getId()).orElseThrow(() -> new CategoryNotFoundException(id));
        p.setManufacturer(manufacturer);
        p.setCategory(category);
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());
        if (p.getImageBase64() != null) {
            p.setImageBase64(p.getImageBase64());
        }
        return Optional.of(this.productRepository.save(p));
    }


    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> listProductsByNameLikeAndCategoryAndManufacturer(String name, Category category, Manufacturer manufacturer) {


        String nameLike = name != null ? "%" + name + "%" : null;
        if(nameLike!=null && category!=null && manufacturer!=null) {
            return this.productRepository.findAllByNameLikeAndCategoryAndManufacturer(nameLike, category, manufacturer);
        }
        else if(nameLike!=null && category!=null && manufacturer==null) {
            return this.productRepository.findAllByNameLikeAndCategory(nameLike, category);
        }
        else if(nameLike!=null && manufacturer!=null && category==null){
            return this.productRepository.findAllByNameLikeAndManufacturer(nameLike, manufacturer);

        }
        else if(category!=null && manufacturer!=null && nameLike==null){
            return this.productRepository.findAllByCategoryAndManufacturer(category, manufacturer);
        }

        else if(nameLike!=null && manufacturer==null && category==null){
            return this.productRepository.findAllByNameLike(nameLike);
        }
        else if (category!=null && nameLike==null && manufacturer==null){
            return this.productRepository.findAllByCategory(category);
        }
        else if(manufacturer!=null && category==null && nameLike==null){
            return this.productRepository.findAllByManufacturer(manufacturer);
        }
        else {
            return this.productRepository.findAll();
        }
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.productRepository.findAll(pageable);
    }


}
