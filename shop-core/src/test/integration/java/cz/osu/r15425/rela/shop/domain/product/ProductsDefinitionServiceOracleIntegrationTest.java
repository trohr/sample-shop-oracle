/*
 * BasketServiceOracleIntegrationTest.java
 * @created on 17. 8. 2018
 * 
 * Developed by Tomáš Rohrbacher.
 */
package cz.osu.r15425.rela.shop.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.config.EclipseLinkJpaConfiguration;
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
public class ProductsDefinitionServiceOracleIntegrationTest
{

	@Autowired TestEntityManager entityManager;
	@Autowired ProductsDefinitionService service;


	/**
	 * Test method for {@link cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#loadAllProducts()}.
	 */
	@Test
	public void testLoadAllProducts_doesNotFail()
	{
		List<ProductDefinitionDto> allProducts = service.loadAllProducts();
		assertThat (allProducts).as("loadAllProducts result").isNotNull();
	}

}
