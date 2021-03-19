package com.example.demo.web;


import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.model.exceptions.ProductIsAlreadyInShoppingCartException;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;


    public ManufacturerController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;

    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getManufacturerPage(@RequestParam(required = false) String error,
                                      @RequestParam(required = false) String manName,
                                      Model model) {
        List<Manufacturer> manufacturers;
        if (manName == null) {
            manufacturers = manufacturerService.findAll();
        } else {
            manufacturers= this.manufacturerService.findManufacturerByNameLike(manName);
        }
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("manufacturers", manufacturers);
        return "manufacturers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
            this.manufacturerService.deleteById(id);
        return "redirect:/manufacturers";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editManufacturerPage(@PathVariable Long id, Model model) {
        if (this.manufacturerService.findById(id).isPresent()) {
            Manufacturer manufacturer = this.manufacturerService.findById(id).get();
            model.addAttribute("manufacturer", manufacturer);
            return "add-manufacturer";

        }
        return "redirect:/manufacturers?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addManufacturerPage(Model model) {
        return "add-manufacturer";
    }

    @PostMapping("/add")
    public String saveManufacturer(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String adress
            ) {
        if (id != null) {
            this.manufacturerService.edit(id, name, adress);
        } else {
            this.manufacturerService.save(name, adress);
        }
        return "redirect:/manufacturers";
    }


}
