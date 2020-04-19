package challenges.api.video_rental_store.common.exceptions;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import challenges.api.video_rental_store.common.exceptions.ApiError.ApiErrorLabel;

@RestControllerAdvice
public class ApiErrorHandler {

	private final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);
	
    @ExceptionHandler(value = { PriceHigherThanPaymentAmountException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleItemNodeForbiddenActionException(PriceHigherThanPaymentAmountException ex, WebRequest request) {
        return createErrorResponse(ApiErrorLabel.PRICE_HIGHER_THAN_PAYMENT_AMOUNT.name(), ex);
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
