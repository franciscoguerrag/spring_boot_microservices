package com.formacionbdi.springboot.app.commons.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="products")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrementals
	private long id;
	
	private String name;
	private Double price;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)  //formatting
	private Date createAT;
	
	@Transient
	private Integer port;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getCreateAT() {
		return createAT;
	}
	public void setCreateAT(Date createAT) {
		this.createAT = createAT;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6970238370618933600L;

	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}


}
