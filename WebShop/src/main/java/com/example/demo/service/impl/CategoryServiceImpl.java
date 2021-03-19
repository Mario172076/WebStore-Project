package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.exceptions.ProductNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category create(String name, String description) {
       if(name==null || name.isEmpty()){
           throw new IllegalArgumentException();
       }
       Category c = new Category(name, description);
       categoryRepository.save(c);
       return c;
    }

    @Override
    public void delete(String name) {
        if(name==null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        categoryRepository.deleteByName(name);

    }




    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> save(String name, String descritpion) {
        return Optional.of(this.categoryRepository.save(new Category(name, descritpion)));
    }

    @Override
    public List<Category> findCategoryByNameLike(String name) {
        String nameLike = name != null ? "%" + name + "%" : null;
        if(nameLike!=null) {
            return this.categoryRepository.findCategoryByNameLike(nameLike);
        }
        else{
            return this.categoryRepository.findAll();
        }
    }

    @Override
    public Optional<Category> edit(Long id, String name, String description) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        category.setName(name);
        category.setDescription(description);

        return Optional.of(this.categoryRepository.save(category));

    }
}
