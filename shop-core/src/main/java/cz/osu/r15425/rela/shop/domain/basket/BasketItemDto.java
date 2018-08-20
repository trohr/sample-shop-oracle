package cz.osu.r15425.rela.shop.domain.basket;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The DTO class for the SHOP_KOSIK_ITEM database table.
 * 
 */
@SuppressWarnings("serial")
public class BasketItemDto implements Serializable
{

	private Integer lineNo;

//	@Column(nullable=false)
	private Integer quantity;

//	@Column(name="UNIT_PRICE", nullable=false, precision=18, scale=2)
	private BigDecimal unitPrice;

	//uni-directional many-to-one association to ShopProdukt
//	@ManyToOne
//	@JoinColumn(name="SHOP_PRODUKT_ID", nullable=false, insertable=false, updatable=false)
	private ProductInfoDto product;

	public BasketItemDto() {
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

	public ProductInfoDto getProduct() {
		return this.product;
	}

	public void setProduct(ProductInfoDto produkt) {
		this.product = produkt;
	}

}