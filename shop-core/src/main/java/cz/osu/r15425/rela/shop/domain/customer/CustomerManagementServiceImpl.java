/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;
import cz.osu.r15425.rela.shop.domain.customer.CustomerDto.AddressDto;
import cz.osu.r15425.rela.shop.domain.customer.CustomerDto.CompanyDataDto;
import cz.osu.r15425.rela.shop.domain.customer.CustomerDto.PersonalDataDto;
import cz.osu.r15425.rela.shop.domain.customer.impl.Address;
import cz.osu.r15425.rela.shop.domain.customer.impl.CompanyData;
import cz.osu.r15425.rela.shop.domain.customer.impl.Email;
import cz.osu.r15425.rela.shop.domain.customer.impl.PersonalData;
import cz.osu.r15425.rela.shop.domain.customer.impl.Phone;
import cz.osu.r15425.rela.shop.domain.customer.impl.ShopZakaznik;
import cz.osu.r15425.rela.shop.domain.customer.impl.ShopZakaznikRepository;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

	// Spring-data JPA repository
	private ShopZakaznikRepository repository;
	
	@Autowired
	public void setShopZakaznikRepository(ShopZakaznikRepository repository) {
		this.repository = repository;
	}



	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.customer.CustomerManagementService#loadAllCustomers()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<CustomerDto> loadAllCustomers()
	{
		final Iterable<ShopZakaznik> allStoredTexts = repository.findAll();
		final List<CustomerDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(CustomerManagementServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public CustomerDto getCustomer(long id) throws EntityNotFoundException
	{
		Optional<ShopZakaznik> entity = repository.findById(id);
		return entity.map(CustomerManagementServiceImpl::assembleDtoFromJpa)
			.orElseThrow(() -> new EntityNotFoundException("Entity Customer not found for id: "+id));
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.customer.CustomerManagementService#storeNewCustomer(cz.osu.r15425.rela.shop.domain.customer.CustomerDto)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerDto storeNewCustomer(CustomerDto obj)
	{
		ShopZakaznik entity = assembleJpaFromDto(null, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @return 
	 * @throws EntityNotFoundException 
	 * @see cz.osu.r15425.rela.shop.domain.customer.CustomerManagementService#updateCustomer(cz.osu.r15425.rela.shop.domain.customer.CustomerDto)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerDto updateCustomer(CustomerDto obj)
			throws EntityNotFoundException
	{
		long id = obj.getId().longValue();
		ShopZakaznik entity = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entity Customer not found for id: "+id));
		entity = assembleJpaFromDto(entity, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.r15425.rela.shop.domain.customer.CustomerManagementService#deleteCustomer(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCustomer(long id) {
		// XXX jak se chova Spring-Data Repository, kdyz ji podsunu id neexistujiciho?
		repository.deleteById(id);
	}

	static CustomerDto assembleDtoFromJpa (ShopZakaznik jpa)
	{
		if (jpa == null) {
			return null;
		}
		final CustomerDto r = new CustomerDto();
		r.setId(jpa.getId());
		r.setType(jpa.getType());
		
		r.setPersonalData(assembleDtoFromJpa(jpa.getPersonal_Data()));
		r.setCompanyData(assembleDtoFromJpa(jpa.getCompany_Data()));

		r.setEmail(assembleDtoFromJpa(jpa.getEmail()));
		r.setPhone(assembleDtoFromJpa(jpa.getPhone()));

		r.setShippingAddress(assembleDtoFromJpa(jpa.getShipping_Address()));
		r.setInvoiceAddress(assembleDtoFromJpa(jpa.getInvoice_Address()));
		r.setInvoiceAddressIsShippingAddress(r.getInvoiceAddress() == null);

		r.setName(jpa.getDerivedName());
		return r;
	}

	static ShopZakaznik assembleJpaFromDto (ShopZakaznik jpa, CustomerDto dto)
	{
		if (jpa == null) {
			jpa = new ShopZakaznik();
		}
		
		jpa.setType(dto.getType());
		
		jpa.setPersonal_Data(assembleJpaFromDto(jpa.getPersonal_Data(), dto.getPersonalData()));
		jpa.setCompany_Data(assembleJpaFromDto(jpa.getCompany_Data(), dto.getCompanyData()));

		jpa.setEmail(assembleJpaFromDto(jpa.getEmail(), dto.getEmail()));
		jpa.setPhone(assembleJpaFromDto(jpa.getPhone(), dto.getPhone()));

		jpa.setShipping_Address(assembleJpaFromDto(jpa.getShipping_Address(), dto.getShippingAddress()));
		if (dto.isInvoiceAddressIsShippingAddress()) {
			jpa.setInvoice_Address(null);
		} else {
			jpa.setInvoice_Address(assembleJpaFromDto(jpa.getInvoice_Address(), dto.getInvoiceAddress()));
		}

		return jpa;
	}


	static PersonalDataDto assembleDtoFromJpa (PersonalData jpa)
	{
		if (jpa == null) {
			return null;
		}
		final PersonalDataDto r = new PersonalDataDto();
		r.setFirstName(jpa.getFirstName());
		r.setSurname(jpa.getSurname());
		r.setTitleAfter(jpa.getTitleAfter());
		r.setTitleBefore(jpa.getTitleBefore());
		
		return r;
	}

	static PersonalData assembleJpaFromDto (PersonalData jpa, PersonalDataDto dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new PersonalData();
		}
		jpa.setFirstName(dto.getFirstName());
		jpa.setSurname(dto.getSurname());
		jpa.setTitleAfter(dto.getTitleAfter());
		jpa.setTitleBefore(dto.getTitleBefore());
		
		return jpa;
	}

	static CompanyDataDto assembleDtoFromJpa (CompanyData jpa)
	{
		if (jpa == null) {
			return null;
		}
		final CompanyDataDto r = new CompanyDataDto();
		r.setCompanyName(jpa.getCompanyName());
		r.setVatNumber(jpa.getVatNumber());
		return r;
	}

	static CompanyData assembleJpaFromDto (CompanyData jpa, CompanyDataDto dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new CompanyData();
		}
		jpa.setCompanyName(dto.getCompanyName());
		jpa.setVatNumber(dto.getVatNumber());
		
		return jpa;
	}

	static AddressDto assembleDtoFromJpa (Address jpa)
	{
		if (jpa == null) {
			return null;
		}
		final AddressDto dto = new AddressDto();
		dto.setStreet(jpa.getStreet());
		dto.setHouseNo(jpa.getHouseNo());
		dto.setTown(jpa.getTown());
		dto.setPostCode(jpa.getPostCode());
		dto.setDistrict(jpa.getDistrict());
		return dto;
	}

	static Address assembleJpaFromDto (Address jpa, AddressDto dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new Address();
		}
		jpa.setStreet(dto.getStreet());
		jpa.setHouseNo(dto.getHouseNo());
		jpa.setTown(dto.getTown());
		jpa.setPostCode(dto.getPostCode());
		jpa.setDistrict(dto.getDistrict());
		return jpa;
	}

	static String assembleDtoFromJpa (Email jpa)
	{
		if (jpa == null) {
			return null;
		}
		return jpa.getValue();
	}

	static Email assembleJpaFromDto (Email jpa, String dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new Email();
		}
		jpa.setValue(dto);
		
		return jpa;
	}

	static String assembleDtoFromJpa (Phone jpa)
	{
		if (jpa == null) {
			return null;
		}
		return jpa.getValue();
	}

	static Phone assembleJpaFromDto (Phone jpa, String dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new Phone();
		}
		jpa.setValue(dto);
		
		return jpa;
	}
	/*
	static EmailDto assembleDtoFromJpa (Email jpa)
	{
		if (jpa == null) {
			return null;
		}
		final EmailDto r = new EmailDto();
		r.setValue(jpa.getValue());
		return r;
	}

	static Email assembleJpaFromDto (Email jpa, EmailDto dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new Email();
		}
		jpa.setValue(dto.getValue());
		
		return jpa;
	}

	static PhoneDto assembleDtoFromJpa (Phone jpa)
	{
		if (jpa == null) {
			return null;
		}
		final PhoneDto r = new PhoneDto();
		r.setValue(jpa.getValue());
		return r;
	}

	static Phone assembleJpaFromDto (Phone jpa, PhoneDto dto)
	{
		if (dto == null) {
			return null;
		}
		if (jpa == null) {
			jpa = new Phone();
		}
		jpa.setValue(dto.getValue());
		
		return jpa;
	}
	*/

}
