package com.formacionbdi.springboot.app.item.clients;

import java.util.List;

import com.springboot.app.commons.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-products")
public interface ProductClientRest {

  @GetMapping("/getAll")
  List<Product> getAll();

  @GetMapping("/product/{id}")
  Product getProduct(@PathVariable Long id);

  @PostMapping("/create")
  Product createProduct(@RequestBody Product p);

  @PutMapping("/update/{id}")
  Product updateProduct(@RequestBody Product p, @PathVariable Long id);

  @DeleteMapping("/delete/{id}")
  Product deleteProduct(@PathVariable Long id);
}
