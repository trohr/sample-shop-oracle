/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.customer.impl;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring-data repository interface.
 * <p>
 * Supports CRUD and sorting operations.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public interface ShopZakaznikRepository extends PagingAndSortingRepository<ShopZakaznik, Long>
{
//	List<ShopZakaznik> findZakaznikByFullName(String name);
}
