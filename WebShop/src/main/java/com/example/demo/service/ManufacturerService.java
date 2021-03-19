package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    Optional<Manufacturer> findById(Long id);
    List<Manufacturer> findAll();

    void deleteById(Long id);

    Optional<Manufacturer> edit(Long id, String name, String adress);
    Optional<Manufacturer> save(String name, String address);
    List<Manufacturer> findManufacturerByNameLike(String name);
}
