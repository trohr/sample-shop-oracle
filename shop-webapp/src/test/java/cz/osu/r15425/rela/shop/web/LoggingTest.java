package cz.osu.r15425.rela.shop.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.osu.r15425.rela.shop.web.ShopBackofficeApplication;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopBackofficeApplication.class)
public class LoggingTest
{
	static Logger LOGGER = LoggerFactory.getLogger(LoggingTest.class);

	@Test
	public void logTrace() {
		LOGGER.trace("trace msg");
	}
	@Test
	public void logDebug() {
		LOGGER.debug("debug msg");
	}
	@Test
	public void logInfo() {
		LOGGER.info("info msg");
	}

	@Test
	public void logWarn() {
		LOGGER.warn("warn msg");
	}

	@Test
	public void logError() {
		LOGGER.error("error msg");
	}

}