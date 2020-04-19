package challenges.api.video_rental_store.common.exceptions;

public class WrongPaymentAmountException extends RuntimeException {

	public WrongPaymentAmountException(int price) {
		super("The payment amount must be higher than the price: " + price);
	}

}
