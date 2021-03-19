package com.example.demo.web;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.model.exceptions.ProductIsAlreadyInShoppingCartException;
import com.example.demo.model.exceptions.ProductNotFoundException;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;


    public ProductController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;

    }

    @GetMapping
    public String getProductPage(
            Model model, @RequestParam(required = false) Category category,
            @RequestParam(required = false) String nameSearch,
            @RequestParam(required = false) Manufacturer manufacturer) {
            return findPaginated(1, model, nameSearch, category, manufacturer);


    }



    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model,
                                @RequestParam(required = false) String nameSearch,
                                @RequestParam(required = false) Category category,
                                @RequestParam(required = false) Manufacturer manufacturer){
        int pageSize=9;
        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        List<Product> listProducts;
        List<Product> productss = this.productService.findAll();
        if (nameSearch == null && category== null && manufacturer==null) {
           listProducts = page.getContent();
        } else {
            listProducts= this.productService.listProductsByNameLikeAndCategoryAndManufacturer(nameSearch,  category, manufacturer);
        }
        List<Category> categories = categoryService.findAll();
        List<Manufacturer> manufacturers = manufacturerService.findAll();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers",manufacturers);
        model.addAttribute("productss", productss);
        return "products";
    }



    @GetMapping("/details/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model){
        Product product = this.productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        List<Product> products;
        products = this.productService.findAll();
        List<Category> categories;
        categories=this.categoryService.findAll();
        List<Manufacturer> manufacturers;
        manufacturers=this.manufacturerService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        return "product-detail";
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        try {
            this.productService.deleteById(id);
        } catch (ProductIsAlreadyInShoppingCartException ex) {
            return String.format("redirect:/products?error=%s", ex.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.findById(id).isPresent()) {
            Product product = this.productService.findById(id).get();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            return "add-product";

        }
        return "redirect:/products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("categories", categories);
        return "add-product";
    }





    @PostMapping("/add")
    @Secured("ROLE_ADMIN")
    public String saveProduct(
            @Valid Product product,
            BindingResult bindingResult,
            @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("categories", categories);
            return "add-product";
        }
        try {
            this.productService.save(product, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.productService.saveProduct(name, price, quantity, manufacturerId);
//        List<Product> products = this.productService.findAll();
//        model.addAttribute("products", products);
//        return "products";
        return "redirect:/products";
    }




}
