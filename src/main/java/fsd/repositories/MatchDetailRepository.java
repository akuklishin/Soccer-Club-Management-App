package fsd.repositories;

import fsd.entities.Match;
import fsd.entities.MatchDetail;
import fsd.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDetailRepository extends JpaRepository<MatchDetail, Long> {
    @Query("SELECT u FROM MatchDetail u WHERE u.match.id = ?1 and u.deleted = 'NO'")
    List<MatchDetail> findMatchDetailByMatch(Long id, Sort sort);

    //select amount of specific match event details under player specific id
    @Query("SELECT COUNT(u.event) FROM MatchDetail u WHERE u.event = 'GOAL' and u.deleted = 'NO' and u.user.id = ?1")
    long findAmountOfGoalsForSpecificPLayer(Long id);

    @Query("SELECT COUNT(u.event) FROM MatchDetail u WHERE u.event = 'ASSIST' and u.deleted = 'NO' and u.user.id = ?1")
    long findAmountOfAssistsForSpecificPLayer(Long id);

    @Query("SELECT COUNT(u.event) FROM MatchDetail u WHERE u.event = 'YELLOW' and u.deleted = 'NO' and u.user.id = ?1")
    long findAmountOfYellowsForSpecificPLayer(Long id);

    @Query("SELECT COUNT(u.event) FROM MatchDetail u WHERE u.event = 'RED' and u.deleted = 'NO' and u.user.id = ?1")
    long findAmountOfRedsForSpecificPLayer(Long id);
}
