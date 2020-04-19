package challenges.api.video_rental_store.client;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.*;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "security.basic.enabled=true" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(RentalApiIT.DB_DATA)
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
class CustomerApiIT {
	
	public static final String DB_DATA = "classpath:challenges/api/video_rental_store/it/db/db_customer_data.xml";

	@Test
	void testFindCustomers() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateCustomer() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCustomer() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteCustomer() {
		fail("Not yet implemented");
	}

}
