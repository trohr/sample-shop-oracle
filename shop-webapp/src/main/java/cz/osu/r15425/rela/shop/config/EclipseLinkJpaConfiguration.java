package cz.osu.r15425.rela.shop.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration { 
 
    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() { 
        return new EclipseLinkJpaVendorAdapter(); 
    }

	public EclipseLinkJpaConfiguration(
			DataSource dataSource, JpaProperties properties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers
	) {
		super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
	}


	@Override
	protected Map<String, Object> getVendorProperties() {
	    HashMap<String, Object> map = new HashMap<>();
	    //map.put(PersistenceUnitProperties.WEAVING, "true"); // TODO zapnout
	    //map.put(PersistenceUnitProperties.WEAVING, "static"); // TODO zapnout
	    // ale je problem: 
	    // org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [cz/osu/oop3/proj/config/EclipseLinkJpaConfiguration.class]: Invocation of init method failed; 
	    // nested exception is java.lang.IllegalStateException: Cannot apply class transformer without LoadTimeWeaver specified
	    map.put(PersistenceUnitProperties.WEAVING, "false");
	    //map.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
	    //map.put(PersistenceUnitProperties.DDL_GENERATION, "none");
	    return map;
	}
    
}