package cz.osu.r15425.rela.shop.domain.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cz.osu.r15425.rela.shop.domain.basket.impl.ShopKosik;
import cz.osu.r15425.rela.shop.domain.basket.impl.ShopKosikRepository;
import cz.osu.r15425.rela.shop.domain.customer.impl.ShopZakaznik;
import cz.osu.r15425.rela.shop.domain.customer.impl.ShopZakaznikRepository;
import cz.osu.r15425.rela.shop.domain.product.impl.ShopProdukt;
import cz.osu.r15425.rela.shop.domain.product.impl.ShopProduktRepository;

/**
	Spring Data JPA CRUD Repository
	===============================
	
	The Spring Data JPA CRUD Repository is my favorite feature of Spring Data JPA.
	Similar to coding with a Spring Integration Gateway, you can just define an interface.
	Spring Data JPA uses generics and reflection to generate the concrete implementation 
	of the interface we define.
	
	Defining a repository for our JPA domain class is as simple as defining an interface 
	and extending the CrudRepository interface. You need to declare two classes in the 
	generics for this interface. They are used for the domain class the repository is 
	supporting, and the type of the id declared of the domain class.
	
	For our domain class we can define a Spring Data JPA repository as follows.
 */
@Configuration
@EnableAutoConfiguration
//@EntityScan(basePackages = {"cz.osu.r15425.rela.shop.domain.product.impl"})
//@EnableJpaRepositories(basePackages = {"cz.osu.r15425.rela.shop.domain.product.impl"})
@EntityScan(basePackageClasses= {
		ShopProdukt.class,
		ShopKosik.class,
		ShopZakaznik.class,
})
@EnableJpaRepositories(basePackageClasses= {
		ShopProduktRepository.class,
		ShopKosikRepository.class,
		ShopZakaznikRepository.class,
})
@EnableTransactionManagement
public class RepositoryConfiguration
{
	// no explicit configuration, all is spring-boot created
}