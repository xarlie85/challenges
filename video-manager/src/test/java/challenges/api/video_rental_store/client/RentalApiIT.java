package challenges.api.video_rental_store.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;

import challenges.api.video_rental_store.service.dtos.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "security.basic.enabled=true" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup({ RentalApiIT.DB_DATA, RentalApiIT.DB_DATA_CUST })
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DirtiesContext
public class RentalApiIT {

	public static final String DB_DATA = "classpath:challenges/api/video_rental_store/it/db/db_rental_data.xml";
	public static final String DB_DATA_CUST = "classpath:challenges/api/video_rental_store/it/db/db_customer_data.xml";

	@Autowired
	private TestRestTemplate restTemplate;

	private List<VideoDto> retrieveInventory() {
		ResponseEntity<List<VideoDto>> response = restTemplate.exchange(
				UriComponentsBuilder.fromUriString(RentalRestController.URI_RENTALS_BASE).toUriString(), HttpMethod.GET, new HttpEntity<>(null),
				new ParameterizedTypeReference<List<VideoDto>>() {
				});
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		List<VideoDto> videoDtos = response.getBody();
		return videoDtos;
	}

	@Test
	public void GET_findInventory_inventoryNotEmpty_ok() {
		List<VideoDto> videoDtos = retrieveInventory();
		assertNotNull(videoDtos);
		assertTrue(!videoDtos.isEmpty());
	}

	private RentDto rentMovie(RentDto rentDto) {
		String Uri = UriComponentsBuilder.fromUriString(RentalRestController.URI_RENTALS_BASE + RentalRestController.URI_RENTALS_RENT).toUriString();
		ResponseEntity<RentDto> response = restTemplate.exchange(Uri, HttpMethod.PUT, new HttpEntity<>(rentDto), RentDto.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		rentDto = response.getBody();
		return rentDto;
	}

	@Test
	public void PUT_rentMovie_availableMoviesList_PaymentAmountEqualsPrice_ok() {
		List<VideoDto> videoDtos = retrieveInventory();

		RentDto rentDtoIn = new RentDto(0, 22, videoDtos, 6, 1);
		RentDto rentDtoOut = rentMovie(rentDtoIn);

		System.out.println(rentDtoIn);
		System.out.println(rentDtoOut);

		assertTrue(rentDtoIn.getPaymentAmount().equals(rentDtoOut.getPaymentAmount()));
	}

	@Test
	public void PUT_returnMovies_noSurcharge_ok() {

		List<VideoDto> videoDtos = retrieveInventory();

		RentDto rentDtoIn = new RentDto(0, 22, videoDtos, 6, 1);
		RentDto rentDtoOut = rentMovie(rentDtoIn);

		String Uri = UriComponentsBuilder.fromUriString(
				RentalRestController.URI_RENTALS_BASE + RentalRestController.URI_RENTALS_RETURN.replace("{rentalId}", rentDtoOut.getId().toString()))
				.toUriString();
		ResponseEntity<Integer> response = restTemplate.exchange(Uri, HttpMethod.PUT, new HttpEntity<>(rentDtoOut), Integer.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Integer surcharge = response.getBody();

		assertTrue(surcharge == 0);
	}

}
