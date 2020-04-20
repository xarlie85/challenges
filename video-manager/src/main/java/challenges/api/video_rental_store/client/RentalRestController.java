package challenges.api.video_rental_store.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import challenges.api.video_rental_store.common.client.SwaggerDocConstants;
import challenges.api.video_rental_store.common.exceptions.ApiError;
import challenges.api.video_rental_store.service.RentalService;
import challenges.api.video_rental_store.service.dtos.*;
import io.swagger.annotations.*;

@RestController
@RequestMapping(RentalRestController.URI_RENTALS_BASE)
public class RentalRestController {

	public static final String URI_RENTALS_BASE = "/rentals";
	public static final String URI_RENTALS_RENT = "/rent";
	public static final String URI_RENTALS_RETURN = "/{rentalId}/return";

	private final RentalService rentalService;

	@Autowired
	public RentalRestController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@GetMapping
	@ApiOperation(value = "API method for retrieving the movies inventory.")
	public List<VideoDto> findInventory() {
		return rentalService.findInventory();
	}

	@PutMapping(URI_RENTALS_RENT)
	@ApiOperation(value = "API method for renting movies.")
	@ApiResponses(value = { @ApiResponse(code = 404, message = SwaggerDocConstants.NOT_FOUND, response = ApiError.class),
			@ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class) })
	public RentDto rentMovie(@RequestBody RentDto rentDto) {
		return rentalService.rentVideos(rentDto);
	}

	@PutMapping(URI_RENTALS_RETURN)
	@ApiOperation(value = "API method for giving movies back. Surcharge is returned in the response's body "
			+ "if there exists any unexpected delay in regards to the previously scheduled renting period.")
	@ApiResponses(value = { @ApiResponse(code = 400, message = SwaggerDocConstants.BAD_REQUEST, response = ApiError.class) })
	public Integer returnMovie(@PathVariable Integer rentalId, @RequestBody RentDto rentDto) {
		return rentalService.returnMovies(rentDto, rentalId);
	}

}
