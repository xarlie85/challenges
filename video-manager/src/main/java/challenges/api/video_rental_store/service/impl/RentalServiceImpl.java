package challenges.api.video_rental_store.service.impl;

import java.sql.Date;
import java.time.*;
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

		RentDto rentDtoOut = new RentDto(0, 0, Lists.newArrayList(), rentDto.getDaysToRent(), rentDto.getCustomerId());
		Integer price = 0;
		Integer bonus = 0;
		for (VideoDto videoDto : rentDto.getVideos()) {
			VideoDto persistedVideo = findVideoById(videoDto.getId());
			if (!persistedVideo.isAvailable())
				throw new VideoNotAvailableException(persistedVideo.getId());
			persistedVideo.setAvailable(false);
			updateVideo(persistedVideo);
			price += calculatePrice(videoDto, rentDto.getDaysToRent());
			rentDtoOut.getVideos().add(videoDto);
			bonus += videoDto.getType().getBonus();
		}

		if (rentDto.getPaymentAmount() <= 0 || price != rentDto.getPaymentAmount())
			throw new WrongPaymentAmountException(price);

		rentDtoOut.setPaymentAmount(price);

		CustomerEntity customerNtt = updateCustomerBonus(rentDto.getCustomerId(), bonus);

		LocalDate localDate = LocalDate.now();

		Set<VideoEntity> videos = rentDtoOut.getVideos().stream().map(video -> modelMapper.map(video, VideoEntity.class)).collect(Collectors.toSet());

		RentalEntity rentalEntity = new RentalEntity(0, Date.valueOf(localDate), Date.valueOf(localDate.plusDays(rentDto.getDaysToRent() + 1)),
				customerNtt, Date.valueOf(localDate), price, bonus, videos);
		RentalEntity rNttOut = rentalRepository.saveAndFlush(rentalEntity);

		rentDtoOut.setId(rNttOut.getId());

		return rentDtoOut;
	}

	private CustomerEntity updateCustomerBonus(Integer customerId, Integer bonus) {
		CustomerDto customerDto = customerService.findCustomerById(customerId);
		CustomerEntity customerNtt = modelMapper.map(customerDto, CustomerEntity.class);
		customerNtt.setBonus(customerNtt.getBonus() + bonus);

		customerService.updateCustomer(modelMapper.map(customerNtt, CustomerDto.class), customerNtt.getId());

		return customerNtt;
	}

	private Integer calculatePrice(VideoDto video, Integer days) {

		VideoType type = video.getType();

		if (type.getDaysInit() >= days)
			return type.getPriceInit();

		Integer daysExtra = days - type.getDaysInit();

		return type.getPriceInit() + daysExtra * type.getPriceDay();
	}

	private void updateVideo(VideoDto video) {
		videoRepository.save(modelMapper.map(video, VideoEntity.class));
	}

	@Override
	public VideoDto findVideoById(Integer id) {
		VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return modelMapper.map(videoEntity, VideoDto.class);
	}

	@Override
	public Integer returnMovies(RentDto rentDto, Integer rentId) {

		RentalEntity rentalNtt = rentalRepository.findById(rentId).orElseThrow(() -> new ResourceNotFoundException(rentId));
		if (rentalNtt.getId() != rentDto.getId())
			throw new ResourceNotFoundException(rentDto.getId());

		checkVideoList(rentDto.getVideos(),
				rentalNtt.getMovies().stream().map(video -> modelMapper.map(video, VideoDto.class)).collect(Collectors.toList()));

		for (VideoDto videoDto : rentDto.getVideos()) {
			VideoDto persistedVideo = findVideoById(videoDto.getId());
			persistedVideo.setAvailable(true);
			updateVideo(persistedVideo);
		}

		Integer surcharge = 0;
		if (rentalNtt.getRentedUntil().before(Date.valueOf(LocalDate.now()))) {
			surcharge = calculateSurcharge(rentalNtt, rentDto.getDaysToRent());
			return surcharge;
		}

		return 0;
	}

	private void checkVideoList(List<VideoDto> videos, List<VideoDto> rentVideos) {
		if (!videos.equals(rentVideos))
			throw new RentedListDifferentFromReturnedException();
	}

	private Integer calculateSurcharge(RentalEntity rentalNtt, Integer daysRented) {

		Duration diffDates = Duration.between(rentalNtt.getRentedUntil().toLocalDate().atStartOfDay(), LocalDate.now().atStartOfDay());
		Integer daysNotCharged = new Long(diffDates.plusDays(1).toDays()).intValue();

		Integer surcharge = 0;
		for (VideoEntity video : rentalNtt.getMovies()) {
			switch (video.getType()) {
			case NEW_RELEASE:
				surcharge += video.getType().getPriceDay() * daysNotCharged;
				break;
			case REGULAR:
				surcharge += (daysRented >= 3) ? video.getType().getPriceDay() * daysNotCharged : 0;
				break;
			case OLD:
				surcharge += (daysRented >= 5) ? video.getType().getPriceDay() * daysNotCharged : 0;
				break;
			}
		}

		return surcharge;
	}

}
