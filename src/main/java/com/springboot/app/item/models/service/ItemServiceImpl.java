package com.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.springboot.app.commons.model.Product;
import com.springboot.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

  @Autowired
  private RestTemplate restClient;

  @Override
  public List<Item> findAll() {
    List<Product> products = Arrays.asList(restClient.getForObject("http://service-products/getAll", Product[].class));

    return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer cantidad) {
    Map<String, String> pathVariables = new HashMap<String, String>();
    pathVariables.put("id", id.toString());
    Product product = restClient.getForObject("http://service-products/product/{id}", Product.class, pathVariables);
    return new Item(product, cantidad);
  }

  @Override
  public Product save(Product p) {
    HttpEntity<Product> body = new HttpEntity<>(p);
    ResponseEntity<Product> responseEntity =
        restClient.exchange("http://service-products/create", HttpMethod.POST, body, Product.class);
    return responseEntity.getBody();
  }

  @Override
  public Product update(Product p, Long id) {
    HttpEntity<Product> body = new HttpEntity<>(p);
    Map<String, String> pathVariables = new HashMap<String, String>();
    pathVariables.put("id", id.toString());
    ResponseEntity<Product> responseEntity =
        restClient.exchange("http://service-products/update/{id}",
            HttpMethod.PUT, body, Product.class, pathVariables);
    return responseEntity.getBody();
  }

  @Override
  public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		restClient.delete("http://service-products/delete/{id}", pathVariables);
  }

}
