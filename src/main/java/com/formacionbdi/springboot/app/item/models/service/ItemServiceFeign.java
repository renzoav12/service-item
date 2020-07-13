package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.app.commons.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clients.ProductClientRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

  @Autowired
  private ProductClientRest clientFeign;

  @Override
  public List<Item> findAll() {
    return clientFeign.getAll().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer count) {
    return new Item(clientFeign.getProduct(id), count);
  }

  @Override
  public Product save(Product p) {
    return clientFeign.createProduct(p);
  }

  @Override
  public Product update(Product p, Long id) {
    return clientFeign.updateProduct(p, id);
  }

  @Override
  public void delete(Long id) {
    clientFeign.deleteProduct(id);
  }

}
