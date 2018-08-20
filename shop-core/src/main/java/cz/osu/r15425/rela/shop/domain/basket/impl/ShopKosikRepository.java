/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.basket.impl;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Spring-data repository interface.
 * <p>
 * Supports CRUD and sorting operations.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public interface ShopKosikRepository extends CrudRepository<ShopKosik, Long>
{

	ShopKosik findOneByUuid(String string);

	@Procedure (name=ShopKosik.NSPQ_Kosik_addItem)
	void addItem(
			@Param("kosik_id") long basketId,
			@Param("produkt_id") long productId,
			@Param("quantity") int quantity
	);

}
