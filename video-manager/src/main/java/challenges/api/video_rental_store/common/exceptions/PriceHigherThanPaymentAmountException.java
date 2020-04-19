package challenges.api.video_rental_store.common.exceptions;

public class PriceHigherThanPaymentAmountException extends RuntimeException {

	public PriceHigherThanPaymentAmountException() {
		super("Price is higher than payment amount received");
	}

}
