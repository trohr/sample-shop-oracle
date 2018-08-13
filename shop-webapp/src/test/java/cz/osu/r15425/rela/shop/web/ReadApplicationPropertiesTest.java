package cz.osu.r15425.rela.shop.web;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import cz.osu.r15425.rela.shop.web.ShopBackofficeApplication;

/**
 * 
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopBackofficeApplication.class)
@ActiveProfiles("mock")
@TestPropertySource(properties={
		"test.hello=HOLA"
		, "spring.config.additional-location=classpath:/test-config/"
}
)
public class ReadApplicationPropertiesTest {

	@Autowired
	Environment environment;

	@Test
	public void readsTestPropertyFromMainApplicationProperties() {
		assertEquals ("123", environment.getRequiredProperty("test.version"));
	}

	@Test
	public void readsTestPropertyFromTestPropertySource() {
		assertEquals ("HOLA", environment.getRequiredProperty("test.hello"));
	}

	@Ignore
	@Test
	public void readsSpringAdditionalConfigLocationProperties () {
//		environment.get
		// Value of this property is written in file src/test/resources/application-test.properties
		assertEquals ("TEST_PROPERTY", environment.getRequiredProperty("testprop"));
	}

}
