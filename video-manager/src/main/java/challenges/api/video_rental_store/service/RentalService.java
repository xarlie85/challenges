package challenges.api.video_rental_store.service;

import java.util.List;

import challenges.api.video_rental_store.service.dtos.*;

public interface RentalService {

	public List<VideoDto> findInventory();

	public RentDto rentMovies(RentDto rentDto);

	public Integer returnMovies(RentDto rentDto);
}
