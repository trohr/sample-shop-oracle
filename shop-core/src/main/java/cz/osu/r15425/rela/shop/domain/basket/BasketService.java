/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.basket;

import java.util.List;
import java.util.Optional;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
public interface BasketService {

	List<BasketDto> loadAllBaskets();
	
	BasketDto getBasket(long id) throws EntityNotFoundException;

//	BasketDto getOrCreateBasketForCustomer(String customerUri);


	BasketDto storeNewBasket(BasketDto obj);
	BasketDto updateBasket(BasketDto obj) throws EntityNotFoundException;
//	void deleteBasket(long id) throws EntityNotFoundException;

	Optional<BasketDto> findValidBasketByUuid(String string);

	BasketItemDto addItemToBasket(long basketId, long productId, int quantity);

//	static public class BasketOrdering
}
