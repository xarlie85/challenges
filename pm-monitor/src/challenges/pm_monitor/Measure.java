package challenges.pm_monitor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Measure {

	private final Long timeStamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
	private GeoPoint location;
	@JsonIgnore
	private Integer pmAmount;
	private String level;

	protected final String source = "robot";

	public String toJSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = mapper.writeValueAsString(this);
		return json;
	}

	/**
	 * Constructor for single measures.
	 * 
	 * @param geoPoint The GeoPoint of the measure
	 * @param pmAmount The local PM2.5 amount
	 */
	public Measure(GeoPoint geoPoint, int pmAmount) {
		this.location = geoPoint;
		this.pmAmount = pmAmount;
		calculateLevel(new Double(pmAmount));
	}

	/**
	 * Constructor for mean measures for Report.
	 * 
	 * @param geoPoint The GeoPoint of the measure
	 * @param pmAmount The local PM2.5 amount
	 */
	public Measure(GeoPoint geoPoint, Double pmAmount) {
		this.location = geoPoint;
		calculateLevel(pmAmount);
	}

	private void calculateLevel(Double pmAmount) {
		if (pmAmount > 0 && pmAmount <= 50) {
			this.level = "Good";
		} else if (pmAmount > 51 && pmAmount <= 100) {
			this.level = "Moderate";
		} else if (pmAmount > 101 && pmAmount <= 150) {
			this.level = "USG";
		} else if (pmAmount > 150) {
			this.level = "Unhealthy";
		}
	}

}
