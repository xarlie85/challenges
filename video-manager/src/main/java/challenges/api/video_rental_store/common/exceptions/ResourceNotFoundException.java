package challenges.api.video_rental_store.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(Integer id) {
		super("There exist no resource with this id: " + id);
	}
}
