package cz.osu.r15425.rela.shop.domain.basket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The DTO class for the SHOP_KOSIK database table.
 * 
 */
@SuppressWarnings("serial")
public class BasketDto implements Serializable, Cloneable
{
	private Long id;
	private String uuid;

	//private LocalDateTime createdOn;
	private String customerUri;

	private LocalDateTime lastUsed;
	private LocalDateTime validUntil;

	//bi-directional many-to-one association to BasketItemDto
//	@OneToMany(mappedBy="kosik", cascade={CascadeType.ALL})
	private List<BasketItemDto> items = new java.util.ArrayList<>();

	public BasketDto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public LocalDateTime getCreatedOn() {
//		return this.createdOn;
//	}
//
//	public void setCreatedOn(LocalDateTime createdOn) {
//		this.createdOn = createdOn;
//	}

	public String getCustomerUri() {
		return this.customerUri;
	}

	public void setCustomerUri(String customerUri) {
		this.customerUri = customerUri;
	}

	public LocalDateTime getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getValidUntil() {
		return this.validUntil;
	}

	public void setValidUntil(LocalDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public List<BasketItemDto> getItems() {
		return this.items;
	}

//	public void setItems(Set<BasketItemDto> items) {
//		this.items = items;
//	}

	public BasketItemDto addItem(BasketItemDto item) {
		getItems().add(item);
		//item.setKosik(this);
		// TODO pridat poradi
		return item;
	}

	public BasketItemDto removeItem(BasketItemDto item) {
		getItems().remove(item);
		//item.setKosik(null);
		// TODO odebrat poradi

		return item;
	}

	@Override
	public BasketDto clone() {
		try {
			return (BasketDto) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Should be cloneable", e);
		}
	}

}