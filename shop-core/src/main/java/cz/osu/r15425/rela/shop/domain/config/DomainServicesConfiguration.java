/*
 * DomainServicesConfiguration.java
 * @created on 13. 8. 2018
 * 
 * Developed by Tomáš Rohrbacher.
 */
package cz.osu.r15425.rela.shop.domain.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.osu.r15425.rela.shop.domain.basket.BasketServiceImpl;
import cz.osu.r15425.rela.shop.domain.customer.CustomerManagementServiceImpl;
import cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionServiceImpl;

/**
 * <h2>DomainServicesConfiguration</h2>
 * @author rohrbacher (rohrbacher@elvoris.cz)
 */
@Configuration
@Import({
	RepositoryConfiguration.class
})
@ComponentScans({
	@ComponentScan(basePackageClasses= {
			//"cz.osu.r15425.rela.shop.domain.product"
			ProductsDefinitionServiceImpl.class,
			BasketServiceImpl.class,
			CustomerManagementServiceImpl.class,
	})
})
public class DomainServicesConfiguration
{

}
