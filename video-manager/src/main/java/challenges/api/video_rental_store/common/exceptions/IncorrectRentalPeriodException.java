package challenges.api.video_rental_store.common.exceptions;

public class IncorrectRentalPeriodException extends RuntimeException {

	public IncorrectRentalPeriodException() {
		super("The number of Days must be higher than nil");
	}

}
