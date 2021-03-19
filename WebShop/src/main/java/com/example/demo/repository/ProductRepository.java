package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameLike(String name);

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByManufacturer(Manufacturer manufacturer);


    List<Product> findAllByNameLikeAndCategory(String name, Category category);

    List<Product> findAllByNameLikeAndManufacturer(String name, Manufacturer manufacturer);

    List<Product> findAllByCategoryAndManufacturer(Category category, Manufacturer manufacturer);

    List<Product> findAllByNameLikeAndCategoryAndManufacturer(String name, Category category, Manufacturer manufacturer);
}
