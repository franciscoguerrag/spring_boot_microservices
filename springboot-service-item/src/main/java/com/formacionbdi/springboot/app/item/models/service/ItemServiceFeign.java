package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clients.ProductClientRest;
import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Product;

@Service("serviceFeign")
@Primary  //es una opción la otra es con el qualifier de arriba
public class ItemServiceFeign implements ItemService {
	
	@Autowired
	private ProductClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		return clientFeign.listar().stream().map(p -> new Item(p,1)).collect(Collectors.toList() );
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(clientFeign.detalle(id),quantity);
	}

	@Override
	public Product save(Product product) {
		return clientFeign.create(product);
	}

	@Override
	public Product update(Product product, Long id) {
		return clientFeign.update(product, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);
	}

}
