package challenges.api.video_rental_store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenges.api.video_rental_store.common.exceptions.ResourceNotFoundException;
import challenges.api.video_rental_store.respository.CustomerRepository;
import challenges.api.video_rental_store.respository.entities.CustomerEntity;
import challenges.api.video_rental_store.service.CustomerService;
import challenges.api.video_rental_store.service.dtos.CustomerDto;

@Service
@Transactional
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
	public CustomerDto findCustomerById(Integer id) {
		CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return modelMapper.map(customer, CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customer, Integer id) {
		CustomerEntity customerNtt = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		if (customer.getId() != id || customer.getId() != customerNtt.getId())
			throw new ResourceNotFoundException(id);
		customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
		return customer;
	}

	@Override
	public CustomerDto deleteCustomer(CustomerDto customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
