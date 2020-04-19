package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto implements Serializable {

	@Getter
	public enum VideoType {
		NEW_RELEASE(0, 0, 3), REGULAR(3, 3, 1), OLD(3, 5, 1);

		private Integer daysInit;
		private Integer priceInit;
		private Integer priceDay;

		private VideoType(Integer daysInit, Integer priceInit, Integer priceDay) {
			this.daysInit = daysInit;
			this.priceInit = priceInit;
			this.priceDay = priceDay;
		}

		@Override
		public String toString() {
			return this.name();
		}

	}

	private Integer id;
	private String title;
	private String descr;
	private boolean available;
	private Date availableFrom;
	private VideoType type;

}
