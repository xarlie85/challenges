package challenges.api.video_rental_store.common.exceptions;

public class RentedListDifferentFromReturnedException extends RuntimeException {

	public RentedListDifferentFromReturnedException() {
		super("Rented list of videos was different form currently delivered.");
	}
}
