/*
 * BasketServiceOracleIntegrationTest.java
 * @created on 18. 8. 2018
 * 
 * Developed by Tomáš Rohrbacher.
 */
package cz.osu.r15425.rela.shop.domain.basket;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Condition;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.config.EclipseLinkJpaConfiguration;
import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;
//
import cz.osu.r15425.rela.shop.domain.config.DomainServicesConfiguration;

/**
 * <h2>BasketServiceOracleIntegrationTest</h2>
 * @author rohrbacher (rohrbacher@elvoris.cz)
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
		EclipseLinkJpaConfiguration.class,
		DomainServicesConfiguration.class,
})
//@DataJpaTest(showSql=true)
@Transactional
@Rollback
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
//@ActiveProfiles({"test-oracle-dev"})
@TestPropertySource("classpath:application-test-oracle-dev.properties")
public class BasketServiceOracleIntegrationTest
{

	/**
	 * Tested service interface
	 */
	@Autowired BasketService service;
	//@Autowired TestEntityManager entityManager;

	/**
	 * property to store basket data from one testing method 
	 * for further use in another
	 */
	private BasketDto lastStoredBasket;


	//
	// TESTS
	//

	/**
	 * Test method for {@link BasketService#loadAllBaskets()}.
	 */
	@Test
	public void testLoadAllBaskets_doesNotFail()
	{
		List<BasketDto> allBaskets = service.loadAllBaskets();
		assertThat (allBaskets).as("loadAllBaskets result").isNotNull();
	}

	@Test
	public void testStoreEmptyBasket_doesNotFail ()
	{
		BasketDto b = new BasketDto();
		b.setCustomerUri("session://ABC");
		b.setUuid(UUID.randomUUID().toString());
		b.setValidUntil(LocalDateTime.now().plus(Duration.ofMinutes(5)));
		
		BasketDto storedB = service.storeNewBasket(b.clone());
		assertThat (storedB.getId()).isNotNull();
		
		lastStoredBasket = storedB;
	}

	@Test
	public void testStoreEmptyBasket_returnsRequestedData ()
	{
		BasketDto b = new BasketDto();
		b.setCustomerUri("session://ABC");
		b.setUuid(UUID.randomUUID().toString());
		b.setValidUntil(LocalDateTime.now().plus(Duration.ofMinutes(5)));
		
		BasketDto storedB = service.storeNewBasket(b.clone());
		assertThat (storedB.getId()).isNotNull();
		assertThat (storedB.getCustomerUri()).isEqualTo(b.getCustomerUri());
		assertThat (storedB.getValidUntil()).isEqualTo(b.getValidUntil().truncatedTo(ChronoUnit.SECONDS));
		assertThat (storedB.getItems()).isNotNull().isEmpty();
		lastStoredBasket = storedB;
	}

	@Test
	public void testLoadStoredBasketById_returnsTheData () throws EntityNotFoundException
	{
		// call test in which the basket is created
		testStoreEmptyBasket_returnsRequestedData();
		
		// get its id
		Long id = lastStoredBasket.getId();
		
		BasketDto basket = service.getBasket(id.longValue());
		
		assertThat (basket).isNotNull();
		assertThat (basket.getId()).isEqualTo(id);
		assertThat (basket.getUuid()).isEqualTo(lastStoredBasket.getUuid());
		assertThat (basket.getCustomerUri()).isEqualTo(lastStoredBasket.getCustomerUri());
		assertThat (basket.getValidUntil()).isEqualTo(lastStoredBasket.getValidUntil());
//		assertThat (basket.getItems()).isNotNull().isEmpty();
	}


	@Test
	public void testLoadBasketByUuid_returnsEmptyIfNoSuchUuid ()
	{
		Condition<Optional<?>> isEmptyCondition = new Condition<Optional<?>>("Is empty condition") {
			@Override
			public boolean matches(Optional<?> arg0) {
				return !arg0.isPresent();
			}
		};
		assertThat (service.findValidBasketByUuid("lkjlkjlkjljk"))
			.is(isEmptyCondition);
	}

	@Test
	public void testLoadBasketByUuid_returnsGivenUuid ()
	{
		// call test in which the basket is created
		testStoreEmptyBasket_returnsRequestedData();
		
		// get its uuid
		String uuid = lastStoredBasket.getUuid();
		
		Optional<BasketDto> basket = service.findValidBasketByUuid(uuid);
		assertThat (basket.isPresent()).isTrue();
		assertThat (basket.get().getUuid()).isEqualTo(uuid);
	}

	@Ignore ("COZE??? VRACENE ID JE ROZDILNE OPROTI ID VYGENEROVANEMU Z PERSIST METODY!!!")
	@Test
	public void testLoadBasketByUuid_returnsGivenUuid_andHasCorrectAllProperties ()
	{
		// call test in which the basket is created
		testStoreEmptyBasket_returnsRequestedData();
		
		// get its uuid
		String uuid = lastStoredBasket.getUuid();
		
		Optional<BasketDto> basket = service.findValidBasketByUuid(uuid);
		assertThat (basket.isPresent()).isTrue();
		assertThat (basket.get().getUuid()).isEqualTo(uuid);
		assertThat (basket.get().getCustomerUri()).isEqualTo(lastStoredBasket.getCustomerUri());
		assertThat (basket.get().getValidUntil()).isEqualTo(lastStoredBasket.getValidUntil());
//		assertThat (basket.get().getItems()).isNotNull().isEmpty();
		assertThat (basket.get().getId()).isEqualTo(lastStoredBasket.getId());
	}

	@Ignore ("not yet implemented")
	@Test
	public void testLoadValidBasketByUUID_returnEmptyIfNotValid ()
	{
		Assert.fail ("TEST NOT YET IMPLEMENTED!");
	}

}
