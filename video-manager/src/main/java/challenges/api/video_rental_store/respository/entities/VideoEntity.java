package challenges.api.video_rental_store.respository.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import challenges.api.video_rental_store.service.dtos.VideoDto.VideoType;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
