package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/product")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("product",productService.findAll());
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }
    @PostMapping("/create-product")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("message","New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editProduct(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("product",product.get());
        return modelAndView;
        }
        else {
            return new ModelAndView("/error");

        }
    }

    @PostMapping("/edit")
    public ModelAndView updateProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView =new ModelAndView("/edit");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message","New customer update successfully");
        return  modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteProduct(@PathVariable Long id){
        Optional<Product> product= productService.findById(id);
        productService.remove(product.get().getId());
        return new ModelAndView("redirect:/product");
    }
}
