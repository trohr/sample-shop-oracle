package cz.osu.r15425.rela.shop.domain.customer.impl;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Structure;
import org.springframework.util.StringUtils;


/**
 * The persistent class for the SHOP_ZAKAZNIK database table.
 * 
 */
@Entity
@Table(name="SHOP_ZAKAZNIK")
@NamedQuery(name="ShopZakaznik.findAll", query="SELECT s FROM ShopZakaznik s")
public class ShopZakaznik implements Serializable {
	private static final long serialVersionUID = 1L;

	// FIND: ^([\t ]+)([\w\d]+)\s+VARCHAR2\s*\((\d+)\s*\w*\) ,
	// REPLACE WITH: $1@Column(name="$2", length=$3)\n$1private String $2;\n

	

	/*
	static public class Country {
	    @Column(name="code3", length=3)
	    private String code3;
	
	    @Column(name="name", length=256)
	    private String name;
	
	    @Column(name="valid")
	    private boolean valid;
	}
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="\"TYPE\"", nullable=false, length=1)
	private String type;

	@Column(name="COMPANY_DATA")
	@Structure
	private CompanyData company_Data;

	@Column(name="PERSONAL_DATA")
	@Structure
	private PersonalData personal_Data;

	@Column(name="email")
	@Structure
	private Email email;

	@Column(name="phone")
	@Structure
	private Phone phone;


	@Structure
	@Column(name="INVOICE_ADDRESS")
	private Address invoice_Address;

	@Structure
	@Column(name="SHIPPING_ADDRESS")
	private Address shipping_Address;



	public ShopZakaznik() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CompanyData getCompany_Data() {
		return company_Data;
	}

	public void setCompany_Data(CompanyData companyData) {
		this.company_Data = companyData;
	}

	public PersonalData getPersonal_Data() {
		return personal_Data;
	}

	public void setPersonal_Data(PersonalData personalData) {
		this.personal_Data = personalData;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Address getInvoice_Address() {
		return invoice_Address;
	}

	public void setInvoice_Address(Address invoiceAddress) {
		this.invoice_Address = invoiceAddress;
	}

	public Address getShipping_Address() {
		return shipping_Address;
	}

	public void setShipping_Address(Address shippingAddress) {
		this.shipping_Address = shippingAddress;
	}


	// INFO lze nechat na databazi
	public String getDerivedName ()
	{
		if ("P".equalsIgnoreCase(type))
		{
			if (personal_Data != null)
			{
				StringBuilder sb = new StringBuilder();
				if (StringUtils.hasLength(personal_Data.getTitleBefore()))
				{
					sb.append(personal_Data.getTitleBefore()).append(" ");
				}
				sb.append(personal_Data.getSurname());
				sb.append(personal_Data.getFirstName());
				if (StringUtils.hasLength(personal_Data.getTitleAfter()))
				{
					sb.append(", ")
						.append(personal_Data.getTitleAfter());
				}
				
				return sb.toString();
			}
		}
		else
		if ("C".equalsIgnoreCase(type))
		{
			if (company_Data != null)
			{
				return company_Data.getCompanyName();
			}
		}
		return null;
	}
}