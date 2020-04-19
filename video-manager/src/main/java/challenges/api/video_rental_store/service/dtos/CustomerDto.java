package challenges.api.video_rental_store.service.dtos;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

	public Integer id;
	public String name;
	public Integer bonus;

}
