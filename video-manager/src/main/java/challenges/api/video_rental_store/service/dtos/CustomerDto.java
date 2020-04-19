package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;

public class CustomerDto implements Serializable {

	public Integer id;
	public String name;
	public Integer bonus;

	public CustomerDto() {
	}

	public CustomerDto(Integer id, String name, Integer bonus) {
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
