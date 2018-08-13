/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.product.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring-data repository interface.
 * <p>
 * Supports CRUD and sorting operations.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public interface ShopProduktRepository extends PagingAndSortingRepository<ShopProdukt, Long>
{
	List<ShopProdukt> findProductByName(String name);
	List<ShopProdukt> findProductByName(String name, Sort sort);
}
