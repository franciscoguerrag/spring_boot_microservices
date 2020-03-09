package com.formacionbdi.springboot.app.products.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.commons.models.entity.Product;
import com.formacionbdi.springboot.app.products.models.service.IProductoService;

@RestController
public class ProductController {
	
//	@Autowired
//	private Environment env;
//	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductoService iProductoService;
	
	@GetMapping("/listar")
	public List<Product> listar() {
		return iProductoService.findALL()
				.stream().map(product -> {
					//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					product.setPort(port);
					return product;
				}).collect(Collectors.toList());
	}
	
	@GetMapping("ver/{id}")
	public Product detalle(@PathVariable Long id) throws Exception {
		Product product = iProductoService.findById(id);
		//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		product.setPort(port);
		/*boolean ok= false;
		if(!ok) {
			throw new Exception("No se pudo cargar el producto");
		}*/
		/*try {
			Thread.sleep(2000L);
		}
		catch (Exception e) {
			e.printStackTrace();
		}*/
		
		return product;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Product crear(@RequestBody 	Product product) {
		return iProductoService.save(product);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product editar(@RequestBody 	Product product, @PathVariable Long id) {
		Product productoDb = iProductoService.findById(id);
		productoDb.setName(product.getName());
		productoDb.setPrice(product.getPrice());
		return iProductoService.save(productoDb);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		iProductoService.deleteById(id);
	}
}
