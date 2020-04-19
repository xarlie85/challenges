package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

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

	public VideoDto() {
	}

	public VideoDto(Integer id, String title, String desc, boolean available, Date availableFrom, VideoType type) {
		this.id = id;
		this.title = title;
		this.descr = desc;
		this.available = available;
		this.availableFrom = availableFrom;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String desc) {
		this.descr = desc;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}

	public VideoType getType() {
		return type;
	}

	public void setType(VideoType type) {
		this.type = type;
	}

}
