package challenges.api.video_rental_store.respository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import challenges.api.video_rental_store.respository.entities.VideoEntity;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Integer>, JpaSpecificationExecutor<VideoEntity> {

}
