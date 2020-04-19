package challenges.api.video_rental_store.common.exceptions;

public class VideoNotAvailableException extends RuntimeException {

	public VideoNotAvailableException(Integer id) {
		super("This video [id:" + id + "] is not available");
	}
}
