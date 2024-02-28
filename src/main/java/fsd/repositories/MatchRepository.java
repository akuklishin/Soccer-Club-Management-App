package fsd.repositories;

import fsd.entities.Match;
import fsd.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT u FROM Match u WHERE u.deleted = 'NO'")
    List<Match> findExistingMatches(Sort sort);
}
