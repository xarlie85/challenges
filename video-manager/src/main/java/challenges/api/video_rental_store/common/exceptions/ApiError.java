package challenges.api.video_rental_store.common.exceptions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiError {

	public enum ApiErrorLabel {
		RENT_ACTION_FAILURE, RETURN_ACTION_FAILURE, RESOURCE_NOT_FOUND, VIDEO_NOT_AVAILABLE, UNHANDLED_EXCEPTION
	}

	private String errorLabel;
	private String errorMsg;

	public ApiError(String errorLabel, String errorMsg) {
		this.errorLabel = errorLabel;
		this.errorMsg = errorMsg;
	}

}
