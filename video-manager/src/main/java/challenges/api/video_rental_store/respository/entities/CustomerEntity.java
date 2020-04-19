package challenges.api.video_rental_store.respository.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER")
public class CustomerEntity implements Serializable {

	@Id
	private Integer id;
	@Column
	private String name;
	@Column
	private Integer bonus;

	public CustomerEntity() {
	}

	public CustomerEntity(Integer id, String name, Integer bonus) {
		this.id = id;
		this.name = name;
		this.bonus = bonus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

}
