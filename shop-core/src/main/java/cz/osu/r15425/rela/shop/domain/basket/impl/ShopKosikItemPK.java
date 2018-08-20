package cz.osu.r15425.rela.shop.domain.basket.impl;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SHOP_KOSIK_ITEM database table.
 * 
 */
@Embeddable
public class ShopKosikItemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SHOP_KOSIK_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private Long shopKosikId;

	@Column(name="SHOP_PRODUKT_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private Long shopProduktId;

	public ShopKosikItemPK() {
	}
	public Long getShopKosikId() {
		return this.shopKosikId;
	}
	public void setShopKosikId(Long shopKosikId) {
		this.shopKosikId = shopKosikId;
	}
	public Long getShopProduktId() {
		return this.shopProduktId;
	}
	public void setShopProduktId(Long shopProduktId) {
		this.shopProduktId = shopProduktId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ShopKosikItemPK)) {
			return false;
		}
		ShopKosikItemPK castOther = (ShopKosikItemPK)other;
		return 
			(this.shopKosikId == castOther.shopKosikId)
			&& (this.shopProduktId == castOther.shopProduktId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.shopKosikId ^ (this.shopKosikId >>> 32)));
		hash = hash * prime + ((int) (this.shopProduktId ^ (this.shopProduktId >>> 32)));
		
		return hash;
	}
}