package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.model.exceptions.CategoryNotFoundException;
import com.example.demo.model.exceptions.ManufacturerNotFoundException;
import com.example.demo.model.exceptions.ProductNotFoundException;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public Optional<Manufacturer> findById(Long id) {
        return this.manufacturerRepository.findById(id);
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> save(String name, String address) {
        return Optional.of(this.manufacturerRepository.save(new Manufacturer(name, address)));
    }

    @Override
    public List<Manufacturer> findManufacturerByNameLike(String name) {
        String nameLike = name != null ? "%" + name + "%" : null;
        if(nameLike!=null) {
            return this.manufacturerRepository.findManufacturerByNameLike(nameLike);
        }
        else{
            return this.manufacturerRepository.findAll();
        }
    }

    @Override
    public void deleteById(Long id) {
        this.manufacturerRepository.deleteById(id);
    }

    @Override
    public Optional<Manufacturer> edit(Long id, String name, String adress) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        manufacturer.setName(name);
        manufacturer.setAdress(adress);

        return Optional.of(this.manufacturerRepository.save(manufacturer));

    }
}
