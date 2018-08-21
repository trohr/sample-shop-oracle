package cz.osu.r15425.rela.shop.domain.customer;

import java.io.Serializable;


/**
 * The DTO class for the SHOP_ZAKAZNIK database table.
 * 
 */
@SuppressWarnings("serial")
public class CustomerDto implements Serializable
{

	/**
	 */
	static public class AddressDto
	{
//		@Size(max=64)
	    private String street;

//		@Size(max=32)
	    private String houseNo;

//		@Size(max=64)
	    private String town;

//		@Size(max=32)
	    private String postCode;

//		@Size(max=64)
	    private String district;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getHouseNo() {
			return houseNo;
		}

		public void setHouseNo(String houseNo) {
			this.houseNo = houseNo;
		}

		public String getTown() {
			return town;
		}

		public void setTown(String town) {
			this.town = town;
		}

		public String getPostCode() {
			return postCode;
		}

		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

	}

	static public class CompanyDataDto {
//		@Size(max=256)
	    private String companyName;
	
//		@Size(max=32)
	    private String vatNumber;

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getVatNumber() {
			return vatNumber;
		}

		public void setVatNumber(String vatNumber) {
			this.vatNumber = vatNumber;
		}
	
	}

	static public class PersonalDataDto {
//		@Size(max=60)
	    private String firstName;

//		@Size(max=60)
	    private String surname;

//		@Size(max=32)
	    private String titleBefore;

//		@Size(max=32)
	    private String titleAfter;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

		public String getTitleBefore() {
			return titleBefore;
		}

		public void setTitleBefore(String titleBefore) {
			this.titleBefore = titleBefore;
		}

		public String getTitleAfter() {
			return titleAfter;
		}

		public void setTitleAfter(String titleAfter) {
			this.titleAfter = titleAfter;
		}

	}

	/*
	static public class Country {
//		@Size(max=3)
	    private String code3;
	
//		@Size(max=256)
	    private String name;
	
	    @Column(name="valid")
	    private boolean valid;
	}
	 */

//	static public class EmailDto {
////		@Size(max=256)
//	    private String value;
//
//		public String getValue() {
//			return value;
//		}
//
//		public void setValue(String value) {
//			this.value = value;
//		}
//	}
//
//	static public class PhoneDto {
////		@Size(max=256)
//	    private String value;
//
//		public String getValue() {
//			return value;
//		}
//
//		public void setValue(String value) {
//			this.value = value;
//		}
//	}



	private Long id;

	// C / P
	private String type;
	
	private CompanyDataDto companyData;

	private PersonalDataDto personalData;

//	private EmailDto email;
	private String email;

//	private PhoneDto phone;
	private String phone;

	private AddressDto shippingAddress;
	private boolean invoiceAddressIsShippingAddress;
	private AddressDto invoiceAddress;



	// derived property
	private String name;


	public CustomerDto() {
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

	public CompanyDataDto getCompanyData() {
		return companyData;
	}

	public void setCompanyData(CompanyDataDto companyData) {
		this.companyData = companyData;
	}

	public PersonalDataDto getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalDataDto personalData) {
		this.personalData = personalData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public EmailDto getEmail() {
//		return email;
//	}
//
//	public void setEmail(EmailDto email) {
//		this.email = email;
//	}
//
//	public PhoneDto getPhone() {
//		return phone;
//	}
//
//	public void setPhone(PhoneDto phone) {
//		this.phone = phone;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AddressDto getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(AddressDto invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public boolean isInvoiceAddressIsShippingAddress() {
		return invoiceAddressIsShippingAddress;
	}

	public void setInvoiceAddressIsShippingAddress(boolean invoiceAddressIsShippingAddress) {
		this.invoiceAddressIsShippingAddress = invoiceAddressIsShippingAddress;
	}

	public AddressDto getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}