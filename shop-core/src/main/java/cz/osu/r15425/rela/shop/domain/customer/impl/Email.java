package cz.osu.r15425.rela.shop.domain.customer.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.eclipse.persistence.annotations.Struct;

@Embeddable
@Struct(name="Email_type", fields= {"value"})
public class Email {
    @Column(name="value", length=256)
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}