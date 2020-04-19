package challenges.api.video_rental_store.service;

import java.util.List;

import challenges.api.video_rental_store.service.dtos.*;

public interface RentalService {

	public List<VideoDto> findInventory();

	public VideoDto findVideoById(Integer id);

	public RentDto rentVideos(RentDto rentDto);

	public Integer returnMovies(RentDto rentDto);
}
