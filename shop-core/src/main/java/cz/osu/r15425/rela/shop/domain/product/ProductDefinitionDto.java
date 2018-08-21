/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.product;

import java.math.BigDecimal;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
// TODO priste pouzit Kotlin a jeho data class
public class ProductDefinitionDto {

	private Long id;

	private String code;
	private String name;
	private String caption;
	private BigDecimal unitPrice;
	private String specification;
	private String imageUrl;
//	private String tags;
	
	public ProductDefinitionDto() {
		super();
	}
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal price) {
		this.unitPrice = price;
	}

	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDefinitionDto= {\"");
		builder.append("id\"=\"").append(id).append("\", ");
		builder.append("code\"=\"").append(code).append("\", ");
		builder.append("name\"=\"").append(name).append("\", ");
		//sbuilder.append("caption\"=\"").append(caption).append("\", ");
		builder.append("unitPrice\"=\"").append(unitPrice).append("\"");
		
//		if (specification != null)
//			builder.append("specification\"=\"").append(specification).append("\", ");
//		if (imageUrl != null)
//			builder.append("imageUrl\"=\"").append(imageUrl);
		builder.append("}");
		return builder.toString();
	}

//	public String getTags() {
//		return tags;
//	}
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
//	
	
}
