package challenges.api.video_rental_store.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import org.assertj.core.util.Sets;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Lists;

import challenges.api.video_rental_store.common.exceptions.*;
import challenges.api.video_rental_store.respository.*;
import challenges.api.video_rental_store.respository.entities.*;
import challenges.api.video_rental_store.service.dtos.*;
import challenges.api.video_rental_store.service.dtos.VideoDto.VideoType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@RunWith(PowerMockRunner.class)
class RentalServiceImplTest {

	@Mock
	private RentalRepository rentalRepo;
	@Mock
	private VideoRepository videoRepo;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private VideoEntity videoNtt;
	@Mock
	private RentalEntity rentalNtt;
	@Mock
	private CustomerServiceImpl customerService;

	private RentalServiceImpl cut;

	@Before
	public void init() {
		cut = new RentalServiceImpl(rentalRepo, videoRepo, modelMapper, customerService);
	}

	@Test(expected = EmptyMoviesListException.class)
	public void rentVideos_moviesListNull_EmptyMoviesListException() {
		cut.rentVideos(new RentDto());
	}

	@Test(expected = EmptyMoviesListException.class)
	public void rentVideos_moviesListEmpty_EmptyMoviesListException() {
		cut.rentVideos(new RentDto(0, 0, Lists.newArrayList(), 0, 1));
	}

	@Test(expected = IncorrectRentalPeriodException.class)
	public void rentVideos_daysLowerEqualNil_IncorrectRentalPeriodException() {
		cut.rentVideos(new RentDto(0, 0, Lists.newArrayList(regularAvailableVideoDto()), 0, 1));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void rentVideos_incorrectVideoId_ResourceNotFoundException() {
		cut.rentVideos(new RentDto(0, 0, Lists.newArrayList(regularAvailableVideoDto()), 3, 1));
	}

	@Test(expected = VideoNotAvailableException.class)
	public void rentVideos_videoInTheListNotAvailable_VideoNotAvailableException() {
		given(videoRepo.findById(newReleaseNotAvailableVideoDto().getId())).willReturn(Optional.of(videoNtt));
		given(modelMapper.map(videoNtt, VideoDto.class)).willReturn(newReleaseNotAvailableVideoDto());
		cut.rentVideos(new RentDto(0, 0, Lists.newArrayList(newReleaseNotAvailableVideoDto()), 3, 1));
	}

	@Test(expected = WrongPaymentAmountException.class)
	public void rentVideos_incorrectPaymentAmount_WrongPaymentAmountException() {
		given(videoRepo.findById(regularAvailableVideoDto().getId())).willReturn(Optional.of(videoNtt));
		given(modelMapper.map(videoNtt, VideoDto.class)).willReturn(regularAvailableVideoDto());
		cut.rentVideos(new RentDto(0, 0, Lists.newArrayList(regularAvailableVideoDto()), 3, 1));
	}

	@Test(expected = RentedListDifferentFromReturnedException.class)
	public void returnMovies_videoListReturnedDifferentRented_Exception() {
		Integer rentId = 1;
		given(rentalRepo.findById(rentId)).willReturn(Optional.of(rentalNtt));
		given(rentalNtt.getId()).willReturn(rentId);
		given(rentalNtt.getMovies()).willReturn(Sets.newHashSet());
		given(modelMapper.map(videoNtt, VideoDto.class)).willReturn(newReleaseNotAvailableVideoDto());
		cut.returnMovies(new RentDto(1, 3, Lists.newArrayList(regularAvailableVideoDto()), 3, 1), 1);
	}

	@Test
	public void returnMovies_existsDelay_surchageOf5Minus3Days_AmountToPayHigherAmountPayed() {
		Integer rentId = 1;
		Set<VideoEntity> hashSet = new HashSet(Arrays.asList(videoNtt));
		Date rentedUntil = Date.valueOf(LocalDate.now().minusDays(1L));

		LocalDate todayFaked = LocalDate.now().plusDays(5L);
		PowerMockito.mockStatic(LocalDate.class);

		when(LocalDate.now()).thenReturn(todayFaked);
		given(rentalRepo.findById(rentId)).willReturn(Optional.of(rentalNtt));
		given(rentalNtt.getId()).willReturn(rentId);
		given(rentalNtt.getMovies()).willReturn(hashSet);
		given(modelMapper.map(videoNtt, VideoDto.class)).willReturn(regularAvailableVideoDto());
		given(videoRepo.findById(regularAvailableVideoDto().getId())).willReturn(Optional.of(videoNtt));
		given(rentalNtt.getRentedUntil()).willReturn(rentedUntil);
		given(videoNtt.getType()).willReturn(regularAvailableVideoDto().getType());

		Integer surcharge = cut.returnMovies(new RentDto(1, 3, Lists.newArrayList(regularAvailableVideoDto()), 3, 1), 1);

		assertTrue(surcharge == 2);
	}

	public static VideoDto regularAvailableVideoDto() {
		return VideoDto.builder().id(1).available(true).type(VideoType.REGULAR).build();
	}

	public static VideoDto newReleaseNotAvailableVideoDto() {
		return VideoDto.builder().id(2).type(VideoType.NEW_RELEASE).build();
	}

	public static VideoDto oldVideoDto() {
		return VideoDto.builder().id(3).type(VideoType.OLD).build();
	}

}
