package challenges.api.video_rental_store.common.exceptions;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import challenges.api.video_rental_store.common.exceptions.ApiError.ApiErrorLabel;

@RestControllerAdvice
public class ApiErrorHandler {

	private final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);

	@ExceptionHandler(value = { PriceHigherThanPaymentAmountException.class, EmptyMoviesListException.class, WrongPaymentAmountException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiError handleItemNodeForbiddenActionException(Exception ex, WebRequest request) {
		return createErrorResponse(ApiErrorLabel.RENT_ACTION_FAILURE.name(), ex);
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ApiError resourceNotFoundException(Exception ex, WebRequest request) {
		return createErrorResponse(ApiErrorLabel.RESOURCE_NOT_FOUND.name(), ex);
	}

	@ExceptionHandler(value = { VideoNotAvailableException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ApiError videoNotAvailableException(VideoNotAvailableException ex, WebRequest request) {
		return createErrorResponse(ApiErrorLabel.VIDEO_NOT_AVAILABLE.name(), ex);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ApiError handleUnhandledException(Exception ex, WebRequest request) {
		log.error("A client request caused an exception.", ex);
		return new ApiError(ApiErrorLabel.UNHANDLED_EXCEPTION.name(), ex.getMessage());
	}

	private ApiError createErrorResponse(String apiErrorLabel, Exception e) {
		log.error(e.getMessage());
		return new ApiError(apiErrorLabel, e.getMessage());
	}
}
