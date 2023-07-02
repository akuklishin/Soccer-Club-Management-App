package fsd.repositories;

import fsd.entities.Match;
import fsd.entities.Training;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT u FROM Training u WHERE u.deleted = 'NO' and u.date >= local_date ")
    List<Training> findExistingTrainings(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date >= local_date or u.position = 'GOALKEEPER' and u.deleted = 'NO' and u.date >= local_date")
    List<Training> findAllGoalkeepersTrainings(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date >= local_date or u.position = 'DEFENDER' and u.deleted = 'NO' and u.date >= local_date")
    List<Training> findAllDefendersTrainings(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date >= local_date or u.position = 'ATTACKER' and u.deleted = 'NO' and u.date >= local_date")
    List<Training> findAllAttackersTrainings(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.deleted = 'NO' and u.date < local_date ")
    List<Training> getTrainingsArchive(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date < local_date or u.position = 'GOALKEEPER' and u.deleted = 'NO' and u.date < local_date")
    List<Training> findAllGoalkeepersTrainingsArchive(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date < local_date or u.position = 'DEFENDER' and u.deleted = 'NO' and u.date < local_date")
    List<Training> findAllDefendersTrainingsArchive(Sort sort);

    @Query("SELECT u FROM Training u WHERE u.position = 'GENERAL' and u.deleted = 'NO' and u.date < local_date or u.position = 'ATTACKER' and u.deleted = 'NO' and u.date < local_date")
    List<Training> findAllAttackersTrainingsArchive(Sort sort);

}
