/*
 * BasketServiceOracleIntegrationTest.java
 * @created on 17. 8. 2018
 * 
 * Developed by Tomáš Rohrbacher.
 */
package cz.osu.r15425.rela.shop.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.osu.r15425.rela.shop.web.ShopBackofficeApplication;

/**
 * <h2>BasketServiceOracleIntegrationTest</h2>
 * @author rohrbacher (rohrbacher@elvoris.cz)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {ShopBackofficeApplication.class})
//@ContextConfiguration(classes= {
//		EclipseLinkJpaConfiguration.class,
//		DomainServicesConfiguration.class,
//})
//@DataJpaTest(showSql=true)
@Transactional
@Rollback
//@AutoConfigureTestDatabase
//@TestPropertySource("classpath:application-test.properties")
public class ProductsDefinitionServiceTest {

	static final private Logger LOGGER = LoggerFactory.getLogger(ProductsDefinitionServiceOracleIntegrationTest.class);
//	@Autowired TestEntityManager entityManager;
	@Autowired ProductsDefinitionService service;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService#loadAllProducts()}.
	 */
	@Test
	public void testLoadAllProducts_doesNotFail()
	{
		List<ProductDefinitionDto> allProducts = service.loadAllProducts();
		LOGGER.info ("Loaded all products: {}", allProducts);
		assertThat (allProducts).as("loadAllProducts result").isNotNull();
	}

}
