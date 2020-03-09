package com.formacionbdi.springboot.app.products.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Product;

public interface IProductoService {
	
	public List<Product> findALL();
	public Product findById(Long id);
	public Product save(Product product);
	public void deleteById(Long id);

}
