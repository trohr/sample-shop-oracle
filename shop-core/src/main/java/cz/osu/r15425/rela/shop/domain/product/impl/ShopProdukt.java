package cz.osu.r15425.rela.shop.domain.product.impl;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SHOP_PRODUKT database table.
 * 
 */
@Entity
@Table(name="SHOP_PRODUKT")
@NamedQuery(name="ShopProdukt.findAll", query="SELECT s FROM ShopProdukt s")
public class ShopProdukt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=256)
	private String name;

	@Column(length=256)
	private String caption;

	@Column(unique=true, nullable=false, length=32)
	private String code;

	@Column(name="IMAGE_URL", length=256)
	private String imageUrl;

	@Lob
	private String specification;

	@Column(name="UNIT_PRICE", precision=18, scale=2)
	private BigDecimal unitPrice;

	@Column(nullable=false, length=1)
	private String valid;

	public ShopProdukt() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}