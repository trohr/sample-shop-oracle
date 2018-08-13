/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;
import cz.osu.r15425.rela.shop.domain.product.impl.ShopProdukt;
import cz.osu.r15425.rela.shop.domain.product.impl.ShopProduktRepository;
import cz.osu.r15425.rela.shop.domain.sorting.OrderBy;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
@Service
public class ProductsDefinitionServiceImpl implements ProductsDefinitionService {

	// Spring-data JPA repository
	private ShopProduktRepository repository;
	
	@Autowired
	public void setProductBackofficeRepository(ShopProduktRepository repository) {
		this.repository = repository;
	}



	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#loadAllProducts()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<ProductDefinitionDto> loadAllProducts()
	{
		final Iterable<ShopProdukt> allStoredTexts = repository.findAll();
		final List<ProductDefinitionDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(ProductsDefinitionServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<ProductDefinitionDto> loadAllProducts(OrderBy... order)
	{
		final Optional<Sort> sort = orderArrayToSpringData(order);
		
		final Iterable<ShopProdukt> allStoredTexts;
		if (sort.isPresent()) {
			allStoredTexts = repository.findAll(sort.get());
		} else {
			allStoredTexts = repository.findAll();
		}
		final List<ProductDefinitionDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(ProductsDefinitionServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<ProductDefinitionDto> loadProductsByName(String searchByName, OrderBy... order)
	{
		final Optional<Sort> sort = orderArrayToSpringData(order);
		
		final Iterable<ShopProdukt> allStoredTexts;
		if (searchByName == null)
		{
			if (sort.isPresent()) {
				allStoredTexts = repository.findAll(sort.get());
			} else {
				allStoredTexts = repository.findAll();
			}
		}
		else
		{
			if (sort.isPresent()) {
				allStoredTexts = repository.findProductByName(searchByName, sort.get());
			} else {
				allStoredTexts = repository.findProductByName(searchByName);
			}
		}
		final List<ProductDefinitionDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(ProductsDefinitionServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}
	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public ProductDefinitionDto getProduct(long id) throws EntityNotFoundException
	{
		ShopProdukt entity = repository.findOne(id);
		if (entity == null) {
			throw new EntityNotFoundException("Entity Product not found for id: "+id);
		}
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#storeNewProduct(cz.osu.r15425.rela.shop.domain.product.ProductDefinitionDto)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ProductDefinitionDto storeNewProduct(ProductDefinitionDto obj)
	{
		ShopProdukt entity = assembleJpaFromDto(null, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @return 
	 * @throws EntityNotFoundException 
	 * @see cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#updateProduct(cz.osu.r15425.rela.shop.domain.product.ProductDefinitionDto)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ProductDefinitionDto updateProduct(ProductDefinitionDto obj)
			throws EntityNotFoundException
	{
		ShopProdukt entity = repository.findOne(obj.getId());
		if (entity == null) {
			throw new EntityNotFoundException("Entity Product not found for id: "+obj.getId());
		}
		entity = assembleJpaFromDto(entity, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#deleteProduct(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProduct(long id) {
		// XXX jak se chova Spring-Data Repository, kdyz ji podsunu id neexistujiciho?
		repository.delete(id);
	}

	static ProductDefinitionDto assembleDtoFromJpa (ShopProdukt jpa)
	{
		final ProductDefinitionDto r = new ProductDefinitionDto();
		r.setId(jpa.getId());
		r.setName(jpa.getName());
		r.setCode(jpa.getCode());
		r.setCaption(jpa.getCaption());
		r.setPrice(jpa.getUnitPrice());
		r.setSpecification(jpa.getSpecification());
		r.setImageUrl(jpa.getImageUrl());
//		r.setTags(jpa.getTags());
		return r;
	}
	static ShopProdukt assembleJpaFromDto (ShopProdukt jpa, ProductDefinitionDto dto)
	{
		if (jpa == null) {
			jpa = new ShopProdukt();
		}
		jpa.setName(dto.getName());
		jpa.setCode(dto.getCode());
		jpa.setCaption(dto.getCaption());
		jpa.setUnitPrice(dto.getPrice());
		jpa.setSpecification(dto.getSpecification());
		jpa.setImageUrl(dto.getImageUrl());
//		jpa.setTags(dto.getTags());
		return jpa;
	}

	static Optional<Sort> orderArrayToSpringData (OrderBy... order) {
		if (order == null || order.length == 0) {
			return Optional.empty();
		}
		final List<Order> springOrders = Arrays.stream(order)
				.map(ProductsDefinitionServiceImpl::orderToSpringData)
				.collect(Collectors.toList());
		return Optional.of(new Sort(springOrders));
	}

	static Order orderToSpringData (OrderBy order) {
		final Sort.Direction direction = order.getDirection() == OrderBy.Direction.ASC
				? Sort.Direction.ASC : Sort.Direction.DESC;
		return new Order(direction, order.getProperty());
	}

}
