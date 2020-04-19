package challenges.api.common.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
public class MapperConfiguration {

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
