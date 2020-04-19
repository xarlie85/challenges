package challenges.api.video_rental_store.respository.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_RENTAL")
public class RentalEntity {

	@Id
	private Integer id;
	@Column(name = "r_from")
	private Date rentedFrom;
	@Column(name = "r_until")
	private Date rentedUntil;
	@OneToOne(optional = false)
	@JoinColumn(name = "r_by")
	private CustomerEntity rentedBy;
	@Column
	private Date returned;
	@Column
	private Integer cost;
	@Column
	private Integer Bonus;

	@ManyToMany
	@JoinTable(name = "T_RENTAL_MOVIES", joinColumns = @JoinColumn(name = "id_r"), inverseJoinColumns = @JoinColumn(name = "id_m"))
	private Set<VideoEntity> movies;

}
