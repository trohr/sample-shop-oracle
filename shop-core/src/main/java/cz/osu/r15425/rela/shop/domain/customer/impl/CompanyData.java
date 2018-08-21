package cz.osu.r15425.rela.shop.domain.customer.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.eclipse.persistence.annotations.Struct;

@Embeddable
@Struct(name="CompanyData_type", fields= {"companyName", "vatNumber"})
public class CompanyData {
    @Column(name="companyName", length=256)
    private String companyName;

    @Column(name="vatNumber", length=32)
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