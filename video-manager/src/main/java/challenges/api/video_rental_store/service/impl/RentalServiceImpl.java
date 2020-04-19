package challenges.api.video_rental_store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenges.api.video_rental_store.respository.*;
import challenges.api.video_rental_store.respository.entities.VideoEntity;
import challenges.api.video_rental_store.service.RentalService;
import challenges.api.video_rental_store.service.dtos.*;

@Service
public class RentalServiceImpl implements RentalService {

	private final RentalRepository rentalRepository;
	private final VideoRepository videoRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public RentalServiceImpl(RentalRepository rentalRepository, VideoRepository videoRepository, ModelMapper modelMapper) {
		this.rentalRepository = rentalRepository;
		this.videoRepository = videoRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<VideoDto> findInventory() {
		List<VideoEntity> videoEs = videoRepository.findAll();
		List<VideoDto> videoDs = videoEs.stream().map(video -> modelMapper.map(video, VideoDto.class)).collect(Collectors.toList());
		return videoDs;
	}

	@Override
	public Integer rentMovies(RentDto rentDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer returnMovies(RentDto rentDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
