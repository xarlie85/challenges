package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;
import java.util.List;

public class RentDto implements Serializable {

	private Integer paymentAmount;
	private List<VideoDto> movies;

	public RentDto() {
	}

	public RentDto(Integer paymentAmount, List<VideoDto> movies) {
		this.paymentAmount = paymentAmount;
		this.movies = movies;
	}

	public Integer getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public List<VideoDto> getMovies() {
		return movies;
	}

	public void setMovies(List<VideoDto> movies) {
		this.movies = movies;
	}

}
