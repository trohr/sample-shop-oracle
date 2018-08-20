/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.basket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;
import cz.osu.r15425.rela.shop.domain.basket.impl.ShopKosik;
import cz.osu.r15425.rela.shop.domain.basket.impl.ShopKosikItem;
import cz.osu.r15425.rela.shop.domain.basket.impl.ShopKosikRepository;
import cz.osu.r15425.rela.shop.domain.product.impl.ShopProdukt;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
@Service
public class BasketServiceImpl implements BasketService {

	static final private Logger LOGGER = LoggerFactory.getLogger(BasketServiceImpl.class);

	// Spring-data JPA repository
	private ShopKosikRepository repository;
	
	@Autowired
	public void setBasketBackofficeRepository(ShopKosikRepository repository) {
		this.repository = repository;
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<BasketDto> loadAllBaskets()
	{
		final Iterable<ShopKosik> allBaskets = repository.findAll();
		final List<BasketDto> dtoList = StreamSupport.stream(allBaskets.spliterator(), false)
			.map(BasketServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public BasketDto getBasket(long id) throws EntityNotFoundException
	{
		ShopKosik entity = repository.findOne(id);
		if (entity == null) {
			throw new EntityNotFoundException("Entity Basket not found for id: "+id);
		}
		return assembleDtoFromJpa(entity);
	}

	@Override
	public Optional<BasketDto> findValidBasketByUuid(String string)
	{
		return Optional.ofNullable(
				repository.findOneByUuid (string))
				.map (BasketServiceImpl::assembleDtoFromJpa);
	}

	@Override
	public BasketItemDto addItemToBasket(long basketId, long productId, int quantity)
	{
		LOGGER.debug(String.format("AddItemToBasket. basketId=%s, productId=%s, quantity=%s", basketId, productId, quantity));
		repository.addItem (basketId, productId, quantity);
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.basket.BasketsDefinitionService#storeNewBasket(cz.osu.r15425.rela.shop.domain.basket.BasketDto)
	 */
//	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public BasketDto storeNewBasket(BasketDto obj)
	{
		LOGGER.debug(String.format("StoreNewBasket. Data=%s", obj));
		ShopKosik entity = assembleJpaFromDto(null, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @return 
	 * @throws EntityNotFoundException 
	 * @see cz.osu.r15425.rela.shop.domain.basket.BasketsDefinitionService#updateBasket(cz.osu.r15425.rela.shop.domain.basket.BasketDto)
	 */
//	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public BasketDto updateBasket(BasketDto obj)
			throws EntityNotFoundException
	{
		ShopKosik entity = repository.findOne(obj.getId());
		if (entity == null) {
			throw new EntityNotFoundException("Entity Basket not found for id: "+obj.getId());
		}
		entity = assembleJpaFromDto(entity, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see cz.osu.r15425.rela.shop.domain.basket.BasketsDefinitionService#deleteBasket(long)
//	 */
////	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void deleteBasket(long id) {
//		// XXX jak se chova Spring-Data Repository, kdyz ji podsunu id neexistujiciho?
//		repository.delete(id);
//	}

	static BasketDto assembleDtoFromJpa (ShopKosik jpa)
	{
		if (jpa == null) {
			return null;
		}
		final BasketDto r = new BasketDto();
		r.setId(jpa.getId());
		r.setCustomerUri(jpa.getCustomerUri());
		r.setUuid(jpa.getUuid());
		r.setLastUsed(convertJpaTimestampToLocalDateTime(jpa.getLastUsed()));
		r.setValidUntil(convertJpaTimestampToLocalDateTime(jpa.getValidUntil()));
		
		// TODO items
		for (ShopKosikItem item : jpa.getItems())
		{
			r.addItem(assembleDtoFromJpa(item));
		}
		return r;
	}


	static ShopKosik assembleJpaFromDto (ShopKosik jpa, BasketDto dto)
	{
		if (jpa == null) {
			jpa = new ShopKosik();
		}
		//jpa.setId(dto.getId());
		jpa.setCustomerUri(dto.getCustomerUri());
		jpa.setUuid(dto.getUuid());
		
		// ???
		jpa.setLastUsed(convertLocalDateTimeToJpaTimestamp(dto.getLastUsed()));
		jpa.setValidUntil(convertLocalDateTimeToJpaTimestamp(dto.getValidUntil()));
//		jpa.setCreatedOn(convertLocalDateTimeToJavaDate(createdOn));

		// ITEMY NECHAME BYT PRI UKLADANI DO DB
		//jpa.setItems(items);
		return jpa;
	}

	// https://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date#23885950
	static java.time.LocalDateTime convertJpaTimestampToLocalDateTime (java.util.Date in)
	{
		if (in == null) {
			return null;
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(
				in.toInstant(), ZoneId.systemDefault());
		return ldt;
	}

	// https://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date#23885950
	static java.util.Date convertLocalDateTimeToJpaTimestamp (java.time.LocalDateTime in)
	{
		if (in != null) {
			in = in.truncatedTo(ChronoUnit.SECONDS);
		}
		return convertLocalDateTimeToJavaDate(in);
	}

	static java.util.Date convertLocalDateTimeToJavaDate (java.time.LocalDateTime in)
	{
		if (in == null) {
			return null;
		}
		java.util.Date out = java.util.Date.from(
				in.atZone(ZoneId.systemDefault()).toInstant());
		return out;
	}

	static BasketItemDto assembleDtoFromJpa (ShopKosikItem jpa)
	{
		final BasketItemDto r = new BasketItemDto();
		r.setLineNo(jpa.getLineNo());
		r.setQuantity(jpa.getQuantity());
		r.setUnitPrice(jpa.getUnitPrice());
		
		r.setProduct(assembleDtoFromJpa(jpa.getProdukt()));
		return r;
	}

	static ProductInfoDto assembleDtoFromJpa (ShopProdukt jpa)
	{
		if (jpa == null) {
			return null;
		}
		final ProductInfoDto r = new ProductInfoDto();
		r.setId(jpa.getId());
		r.setName(jpa.getName());
		r.setCode(jpa.getCode());
		r.setCaption(jpa.getCaption());
		r.setPrice(jpa.getUnitPrice());
//		r.setSpecification(jpa.getSpecification());
		r.setImageUrl(jpa.getImageUrl());
//		r.setTags(jpa.getTags());
		return r;
	}

}
