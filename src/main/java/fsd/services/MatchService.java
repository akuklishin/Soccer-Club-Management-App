package fsd.services;

import fsd.entities.Match;
import fsd.entities.MatchDetail;
import fsd.repositories.MatchDetailRepository;
import fsd.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

//    @Autowired
//    private MatchDetailService matchDetailService;

    public Optional<Match> getById(Long id) {
        return matchRepository.findById(id);
    }

    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    public List<Match> getAllExistingMatches() {
        return matchRepository.findExistingMatches(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Match> getAllExistingMatchesByOldest() {
        return matchRepository.findExistingMatches(Sort.by(Sort.Direction.ASC, "date"));
    }

    public Match save(Match match) {
        return matchRepository.save(match);
    }


    public void delete(Match match) {

        match.setDeleted(Match.Deleted.YES);
        matchRepository.save(match);

    }

}
