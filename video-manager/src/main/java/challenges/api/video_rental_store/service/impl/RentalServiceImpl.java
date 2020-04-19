package challenges.api.video_rental_store.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import challenges.api.video_rental_store.common.exceptions.*;
import challenges.api.video_rental_store.respository.*;
import challenges.api.video_rental_store.respository.entities.*;
import challenges.api.video_rental_store.service.*;
import challenges.api.video_rental_store.service.dtos.*;
import challenges.api.video_rental_store.service.dtos.VideoDto.VideoType;

@Service
@Transactional
public class RentalServiceImpl implements RentalService {

	private final RentalRepository rentalRepository;
	private final VideoRepository videoRepository;
	private final ModelMapper modelMapper;
	private final CustomerService customerService;

	@Autowired
	public RentalServiceImpl(RentalRepository rentalRepository, VideoRepository videoRepository, ModelMapper modelMapper,
			CustomerService customerService) {
		this.rentalRepository = rentalRepository;
		this.videoRepository = videoRepository;
		this.modelMapper = modelMapper;
		this.customerService = customerService;
	}

	@Override
	public List<VideoDto> findInventory() {
		List<VideoEntity> videoEs = videoRepository.findAll();
		List<VideoDto> videoDs = videoEs.stream().map(video -> modelMapper.map(video, VideoDto.class)).collect(Collectors.toList());
		return videoDs;
	}

	@Override
	public RentDto rentVideos(@NotNull RentDto rentDto) {

		if (rentDto.getVideos() == null || rentDto.getVideos().isEmpty())
			throw new EmptyMoviesListException();
		if (rentDto.getDaysToRent() <= 0)
			throw new IncorrectRentalPeriodException();

		RentDto rentDtoOut = new RentDto(0, Lists.newArrayList(), rentDto.getDaysToRent(), rentDto.getCustomerId());
		Integer price = 0;
		Integer bonus = 0;
		for (VideoDto videoDto : rentDto.getVideos()) {
			VideoDto persistedVideo = findVideoById(videoDto.getId());
			if (!persistedVideo.isAvailable())
				throw new VideoNotAvailableException(persistedVideo.getId());
			price += calculatePrice(videoDto, rentDto.getDaysToRent());
			rentDtoOut.getVideos().add(videoDto);
			bonus += videoDto.getType().getBonus();
		}

		if (rentDto.getPaymentAmount() <= 0 || price != rentDto.getPaymentAmount())
			throw new WrongPaymentAmountException(price);

		rentDtoOut.setPaymentAmount(price);

		CustomerDto customerDto = customerService.findCustomerById(rentDto.getCustomerId());
		CustomerEntity customerNtt = modelMapper.map(customerDto, CustomerEntity.class);
		customerNtt.setBonus(customerNtt.getBonus() + bonus);
		customerService.updateCustomer(modelMapper.map(customerNtt, CustomerDto.class), customerNtt.getId());

		LocalDate localDate = LocalDate.now();

		Set<VideoEntity> videos = rentDtoOut.getVideos().stream().map(video -> modelMapper.map(video, VideoEntity.class)).collect(Collectors.toSet());

		RentalEntity rentalEntity = new RentalEntity(0, Date.valueOf(localDate), Date.valueOf(localDate.plusDays(rentDto.getDaysToRent())),
				customerNtt, Date.valueOf(localDate), price, bonus, videos);
		rentalRepository.save(rentalEntity);

		return rentDtoOut;
	}

	@Override
	public VideoDto findVideoById(Integer id) {
		VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return modelMapper.map(videoEntity, VideoDto.class);
	}

	@Override
	public Integer returnMovies(RentDto rentDto) {
		// TODO Auto-generated method stub
		return null;
	}

	private Integer calculatePrice(VideoDto video, Integer days) {

		VideoType type = video.getType();

		if (type.getDaysInit() >= days)
			return type.getPriceInit();

		Integer daysExtra = days - type.getDaysInit();

		return type.getPriceInit() + daysExtra * type.getPriceDay();

	}

}
