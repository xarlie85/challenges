package challenges.api.video_rental_store.respository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import challenges.api.video_rental_store.respository.entities.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>, JpaSpecificationExecutor<CustomerEntity> {

}
