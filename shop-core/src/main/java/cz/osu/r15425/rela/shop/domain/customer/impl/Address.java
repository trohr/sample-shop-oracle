package cz.osu.r15425.rela.shop.domain.customer.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.eclipse.persistence.annotations.Struct;

/**
 */
@Embeddable
@Struct(name="Adresa_type", fields= {"street", "houseNo", "town", "postCode", "district"})
public class Address
{
	@Column(name="street", length=64)
    private String street;

    @Column(name="houseNo", length=32)
    private String houseNo;

    @Column(name="town", length=64)
    private String town;

    @Column(name="postCode", length=32)
    private String postCode;

    @Column(name="district", length=64)
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