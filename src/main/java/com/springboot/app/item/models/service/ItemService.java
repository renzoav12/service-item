package com.springboot.app.item.models.service;

import java.util.List;

import com.springboot.app.item.models.Item;
import com.springboot.app.commons.model.Product;

public interface ItemService {

  List<Item> findAll();

  Item findById(Long id, Integer quantity);

  Product save(Product p);

  Product update(Product p, Long id);

  void delete(Long id);
}
