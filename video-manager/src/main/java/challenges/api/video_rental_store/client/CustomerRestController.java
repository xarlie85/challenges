package challenges.api.video_rental_store.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import challenges.api.video_rental_store.common.client.SwaggerDocConstants;
import challenges.api.video_rental_store.common.exceptions.ApiError;
import challenges.api.video_rental_store.service.CustomerService;
import challenges.api.video_rental_store.service.dtos.CustomerDto;
import io.swagger.annotations.*;

@RestController
@RequestMapping(CustomerRestController.URI_CUSTOMER_BASE)
public class CustomerRestController {
	
	public static final String URI_CUSTOMER_BASE = "/customers";
	public static final String URI_CUSTOMER_UPDATE = URI_CUSTOMER_BASE + "/{id}";
	
	private final CustomerService customerService;
	
	@Autowired
	public CustomerRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	@ApiOperation(value = "API method for retrieving list of customers.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class)})
	public List<CustomerDto> findCustomers() {
		List<CustomerDto> customers = this.customerService.findCustomers();
		return customers;
	}
	
	
	@PostMapping
	@ApiOperation(value = "API method for creating a new customer.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class)})
	public CustomerDto createCustomer(CustomerDto customer) {
		return customerService.createCustomer(customer);
	}
	
	@PutMapping(URI_CUSTOMER_UPDATE)
	@ApiOperation(value = "API method for updating an existing customer.")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = SwaggerDocConstants.NOT_FOUND, response = ApiError.class),
			@ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class)})
	public CustomerDto updateCustomer(@RequestParam Integer id, @RequestBody CustomerDto customer) {
		return customerService.updateCustomer(customer, id);
	}
	
	@DeleteMapping
	@ApiOperation(value = "API method for deleting an existing customer.")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = SwaggerDocConstants.NOT_FOUND, response = ApiError.class),
			@ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class)})
	public CustomerDto deleteCustomer(@RequestParam Integer id, @RequestBody CustomerDto customer) {
		return customerService.deleteCustomer(customer);
	}
	
}
