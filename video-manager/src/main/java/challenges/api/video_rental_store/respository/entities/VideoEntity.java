package challenges.api.video_rental_store.respository.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import challenges.api.video_rental_store.service.dtos.VideoDto.VideoType;

@Entity
@Table(name = "T_MOVIE")
public class VideoEntity {

	@Id
	private Integer id;
	@Column
	private String title;
	@Column
	private String descr;
	@Column(name = "avail")
	private boolean available;
	@Column(name = "avail_f")
	private Date availableFrom;
	@Column
	@Enumerated(EnumType.STRING)
	private VideoType type;

	@ManyToMany
	Set<RentalEntity> rentals;

	public VideoEntity() {
	}

	public VideoEntity(Integer id, String title, String descr, boolean available, Date availableFrom, VideoType type) {
		this.id = id;
		this.title = title;
		this.descr = descr;
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
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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

	public Set<RentalEntity> getRentals() {
		return rentals;
	}

	public void setRentals(Set<RentalEntity> rentals) {
		this.rentals = rentals;
	}

}
