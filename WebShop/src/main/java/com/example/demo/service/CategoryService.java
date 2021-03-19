package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category create(String name, String description);



    void delete(String name);

   


    List<Category> findAll();
    void deleteById(Long id);
    Optional<Category> findById(Long id);
    Optional<Category> edit(Long id, String name, String adress);
    Optional<Category> save(String name, String address);
    List<Category> findCategoryByNameLike(String name);
}
