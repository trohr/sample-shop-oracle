package cz.osu.r15425.rela.shop.domain.basket.impl;

import java.io.Serializable;
import javax.persistence.*;

import cz.osu.r15425.rela.shop.domain.product.impl.ShopProdukt;

import java.math.BigDecimal;


/**
 * The persistent class for the SHOP_KOSIK_ITEM database table.
 * 
 */
@Entity
@Table(name="SHOP_KOSIK_ITEM")
@NamedQuery(name="ShopKosikItem.findAll", query="SELECT s FROM ShopKosikItem s")
public class ShopKosikItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ShopKosikItemPK id;

	@Column(name="LINE_NO", nullable=false)
	private Integer lineNo;

	@Column(nullable=false)
	private Integer quantity;

	@Column(name="UNIT_PRICE", nullable=false, precision=18, scale=2)
	private BigDecimal unitPrice;

	//bi-directional many-to-one association to ShopKosik
	@ManyToOne
	@JoinColumn(name="SHOP_KOSIK_ID", nullable=false) //, insertable=false, updatable=false)
	private ShopKosik kosik;

	//uni-directional many-to-one association to ShopProdukt
	@ManyToOne
	@JoinColumn(name="SHOP_PRODUKT_ID", nullable=false) //, insertable=false, updatable=false)
	private ShopProdukt produkt;

	public ShopKosikItem() {
	}

	public ShopKosikItemPK getId() {
		return this.id;
	}

	public void setId(ShopKosikItemPK id) {
		this.id = id;
	}

	public Integer getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ShopKosik getKosik() {
		return this.kosik;
	}

	public void setKosik(ShopKosik kosik) {
		this.kosik = kosik;
	}

	public ShopProdukt getProdukt() {
		return this.produkt;
	}

	public void setProdukt(ShopProdukt produkt) {
		this.produkt = produkt;
	}

}