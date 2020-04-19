package challenges.api.video_rental_store.service;

import java.util.List;

import challenges.api.video_rental_store.service.dtos.CustomerDto;

public interface CustomerService {

	public CustomerDto createCustomer(CustomerDto customer);

	public List<CustomerDto> findCustomers();

	public CustomerDto findCustomerById(Integer id);

	public CustomerDto updateCustomer(CustomerDto customer, Integer id);

	public CustomerDto deleteCustomer(CustomerDto customer);

}
