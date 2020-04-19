package challenges.api.video_rental_store.common.exceptions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiError {
	
	public enum ApiErrorLabel {
		PRICE_HIGHER_THAN_PAYMENT_AMOUNT, UNHANDLED_EXCEPTION
	}
	
	private String errorLabel;
	private String errorMsg;
	
	public ApiError(String errorLabel, String errorMsg) {
		this.errorLabel = errorLabel; 
		this.errorMsg = errorMsg;
	}
	
}
