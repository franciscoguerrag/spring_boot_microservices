package com.formacionbdi.springboot.app.products.models.dao;

import com.formacionbdi.springboot.app.commons.models.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long>{

}
