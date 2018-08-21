/**
 * 
 */
package cz.osu.r15425.rela.shop.domain.customer;

import java.util.List;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
public interface CustomerManagementService {

	List<CustomerDto> loadAllCustomers();
//	List<CustomerDto> loadAllCustomers(OrderBy... order);
	
//	List<CustomerDto> loadCustomersByName(String searchByName, OrderBy... order);
	
	
	CustomerDto getCustomer(long id) throws EntityNotFoundException;

	CustomerDto storeNewCustomer(CustomerDto obj);
	CustomerDto updateCustomer(CustomerDto obj) throws EntityNotFoundException;
	void deleteCustomer(long id) throws EntityNotFoundException;

}
