package challenges.api.video_rental_store.common.exceptions;

public class WrongPaymentAmountException extends RuntimeException {

	public WrongPaymentAmountException(int price) {
		super("The payment amount must be equal to the price: " + price);
	}

}
