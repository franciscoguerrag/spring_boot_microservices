package com.formacionbdi.springboot.app.products.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.formacionbdi.springboot.app.products.models.dao.ProductDao;
import com.formacionbdi.springboot.app.commons.models.entity.Product;

@Service
public class ProductServiceImpl implements IProductoService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findALL() {
		return (List<Product>) productDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return productDao.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productDao.deleteById(id);
	}

}
