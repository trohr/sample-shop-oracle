package cz.osu.r15425.rela.shop.domain.basket.impl;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SHOP_KOSIK database table.
 * 
 */
@Entity
@Table(name="SHOP_KOSIK")
@NamedQuery(name="ShopKosik.findAll", query="SELECT s FROM ShopKosik s")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name=ShopKosik.NSPQ_Kosik_addItem, procedureName="SHOP_Kosik_add_item",
		parameters= {
				@StoredProcedureParameter(mode=ParameterMode.IN, name="kosik_id", type=Long.class),
				@StoredProcedureParameter(mode=ParameterMode.IN, name="produkt_id", type=Long.class),
				@StoredProcedureParameter(mode=ParameterMode.IN, name="quantity", type=Integer.class),
		}),
	@NamedStoredProcedureQuery(name=ShopKosik.NSPQ_Kosik_removeLine, procedureName="SHOP_Kosik_remove_line",
		parameters= {
				@StoredProcedureParameter(mode=ParameterMode.IN, name="p_kosik_id", type=Long.class),
				@StoredProcedureParameter(mode=ParameterMode.IN, name="p_line_no", type=Integer.class),
		})
})
//@Nam
public class ShopKosik implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String NSPQ_Kosik_addItem = "ShopKosik.addItem";
	public static final String NSPQ_Kosik_removeLine = "ShopKosik.removeLine";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	private Date createdOn;

	@Column(name="CUSTOMER_URI", length=256)
	private String customerUri;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_USED")
	private Date lastUsed;

	@Column(name="\"UUID\"", length=60)
	private String uuid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_UNTIL")
	private Date validUntil;

	//bi-directional many-to-one association to BasketItemDto
	@OneToMany(mappedBy="kosik", cascade={CascadeType.ALL})
	@OrderBy ("lineNo ASC")
	private List<ShopKosikItem> items;

	public ShopKosik() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCustomerUri() {
		return this.customerUri;
	}

	public void setCustomerUri(String customerUri) {
		this.customerUri = customerUri;
	}

	public Date getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getValidUntil() {
		return this.validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public List<ShopKosikItem> getItems() {
		return this.items;
	}

	public void setItems(List<ShopKosikItem> items) {
		this.items = items;
	}

	public ShopKosikItem addItem(ShopKosikItem item) {
		getItems().add(item);
//		item.setKosik(this);

		return item;
	}

	public ShopKosikItem removeItem(ShopKosikItem item) {
		getItems().remove(item);
//		item.setKosik(null);

		return item;
	}

}