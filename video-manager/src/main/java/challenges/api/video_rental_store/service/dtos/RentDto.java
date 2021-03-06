package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentDto implements Serializable {

	private Integer id;
	private Integer paymentAmount;
	private List<VideoDto> videos;
	private int daysToRent;
	private Integer customerId;

}
