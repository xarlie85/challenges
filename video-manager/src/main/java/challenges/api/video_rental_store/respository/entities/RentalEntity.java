package challenges.api.video_rental_store.respository.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
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

	public RentalEntity() {
	}

	public RentalEntity(Integer id, Date rentedFrom, Date rentedUntil, CustomerEntity rentedBy, Date returned, Integer cost, Integer bonus) {
		this.id = id;
		this.rentedFrom = rentedFrom;
		this.rentedUntil = rentedUntil;
		this.rentedBy = rentedBy;
		this.returned = returned;
		this.cost = cost;
		Bonus = bonus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRentedFrom() {
		return rentedFrom;
	}

	public void setRentedFrom(Date rentedFrom) {
		this.rentedFrom = rentedFrom;
	}

	public Date getRentedUntil() {
		return rentedUntil;
	}

	public void setRentedUntil(Date rentedUntil) {
		this.rentedUntil = rentedUntil;
	}

	public CustomerEntity getRentedBy() {
		return rentedBy;
	}

	public void setRentedBy(CustomerEntity rentedBy) {
		this.rentedBy = rentedBy;
	}

	public Date getReturned() {
		return returned;
	}

	public void setReturned(Date returned) {
		this.returned = returned;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getBonus() {
		return Bonus;
	}

	public void setBonus(Integer bonus) {
		Bonus = bonus;
	}

	public Set<VideoEntity> getMovies() {
		return movies;
	}

	public void setMovies(Set<VideoEntity> movies) {
		this.movies = movies;
	}

}
