package challenges.api.video_rental_store.common.exceptions;

public class EmptyMoviesListException extends RuntimeException {

	public EmptyMoviesListException() {
		super("Movies list is empty");
	}

}
