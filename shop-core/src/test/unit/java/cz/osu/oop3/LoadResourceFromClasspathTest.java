package cz.osu.oop3;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadResourceFromClasspathTest {

	static final private Logger LOGGER = LoggerFactory.getLogger(LoadResourceFromClasspathTest.class);
	
	public LoadResourceFromClasspathTest() {
	}

	@Test
	public void shouldFindResourceInClasspath ()
	{
		LOGGER.info("shouldFindResourceInClasspath start");
		
		final URL res = LoadResourceFromClasspathTest.class.getResource(
				"/cz/osu/oop3/ClasspathResource.xml");
		assertNotNull ("The classpath resource should have been loaded", res);
		
		LOGGER.info("shouldFindResourceInClasspath end");
	}

	@Test
	public void shouldFindResourceInClasspath_IN_JAVA_DIR ()
	{
		LOGGER.info("shouldFindResourceInClasspath start");
		
		final URL res = LoadResourceFromClasspathTest.class.getResource(
				"/cz/osu/oop3/ClasspathResourceInJavaDir.xml");
		assertNotNull ("The classpath resource should have been loaded", res);
		
		LOGGER.info("shouldFindResourceInClasspath end");
	}

}
