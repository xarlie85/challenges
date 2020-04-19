package challenges.api.video_rental_store.respository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import challenges.api.video_rental_store.respository.entities.RentalEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Integer>, JpaSpecificationExecutor<RentalEntity> {

}
