package challenges.api.configuration;

import javax.sql.DataSource;

import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.springtestdbunit.bean.*;

@TestConfiguration
public class TestDbConnectionConfiguration {

	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig(final DataSource dataSource) {
		final DatabaseConfigBean dbConfig = new DatabaseConfigBean();
		dbConfig.setDatatypeFactory(new H2DataTypeFactory());
		dbConfig.setAllowEmptyFields(Boolean.TRUE);
		return dbConfig;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(final DataSource dataSource,
			final DatabaseConfigBean databaseConfigBean) {
		final DatabaseDataSourceConnectionFactoryBean connection = new DatabaseDataSourceConnectionFactoryBean();
		connection.setDataSource(dataSource);
		connection.setDatabaseConfig(databaseConfigBean);
		return connection;
	}

}