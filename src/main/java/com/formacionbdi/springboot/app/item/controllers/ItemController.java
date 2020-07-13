package com.formacionbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.app.commons.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

@RefreshScope
@RestController
public class ItemController {

  private static Logger log = LoggerFactory.getLogger(ItemController.class);

  @Autowired
  private Environment env;

  @Autowired
  @Qualifier("serviceFeign")
  private ItemService itemService;

  @Value("${config.text}")
  private String text;

  @GetMapping("/getAll")
  public List<Item> getAll() {
    return itemService.findAll();
  }

  //@HystrixCommand(fallbackMethod = "metodoAlternativo")
  @GetMapping("/product/{id}/count/{count}")
  public Item getItem(@PathVariable Long id, @PathVariable Integer count) {
    return itemService.findById(id, count);
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody Product product) {
    return itemService.save(product);
  }

  @PutMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
    return itemService.update(product, id);
  }

  @DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
		itemService.delete(id);
  }

  public Item metodoAlternativo(Long id, Integer cantidad) {
    Item item = new Item();
    Product product = new Product();

    item.setCount(cantidad);
    product.setId(id);
    product.setName("Camara Sony");
    product.setPrice(500.00);
    item.setProduct(product);
    return item;
  }

  @GetMapping("/get-config")
  public ResponseEntity<?> getConfig(@Value("${server.port}") String port){
    log.info(text);
    Map<String, String > map = new HashMap<>();
    map.put("text", text);
    map.put("port", port);

    if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
      map.put("name", env.getProperty("config.author.name"));
      map.put("email", env.getProperty("config.author.email"));
    }
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

}
