package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentDto implements Serializable {

	private Integer paymentAmount;
	private List<VideoDto> movies;
	private int daysToRent;

}
