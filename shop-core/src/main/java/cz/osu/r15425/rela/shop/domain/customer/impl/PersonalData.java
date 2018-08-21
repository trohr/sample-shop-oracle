package cz.osu.r15425.rela.shop.domain.customer.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.eclipse.persistence.annotations.Struct;

@Embeddable
@Struct(name="PersonalData_type", fields= {"firstName", "surname", "titleBefore", "titleAfter"})
public class PersonalData {
    @Column(name="firstName", length=60)
    private String firstName;

    @Column(name="surname", length=60)
    private String surname;

    @Column(name="titleBefore", length=32)
    private String titleBefore;

    @Column(name="titleAfter", length=32)
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