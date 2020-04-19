package challenges.api.video_rental_store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenges.api.video_rental_store.respository.CustomerRepository;
import challenges.api.video_rental_store.service.CustomerService;
import challenges.api.video_rental_store.service.dtos.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDto> findCustomers() {
		return customerRepository.findAll().stream().map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
	}

	@Override
	public CustomerDto findCustomer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customer, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerDto deleteCustomer(CustomerDto customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
