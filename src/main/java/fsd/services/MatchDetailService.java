package fsd.services;

import fsd.entities.MatchDetail;
import fsd.repositories.MatchDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchDetailService {

    @Autowired
    MatchDetailRepository matchDetailRepository;

    public Optional<MatchDetail> getById(Long id) {
        return matchDetailRepository.findById(id);
    }

    public List<MatchDetail> getMatchDetailByMatch(Long id) {
        return matchDetailRepository.findMatchDetailByMatch(id, Sort.by(Sort.Direction.ASC, "minute"));
    }

    public List<MatchDetail> getAll() {
        return matchDetailRepository.findAll();
    }

    public MatchDetail save(MatchDetail matchDetail) {
        return matchDetailRepository.save(matchDetail);
    }

    public long totalGoalsByPLayer(Long id) {
        return matchDetailRepository.findAmountOfGoalsForSpecificPLayer(id);
    }

    public long totalAssistsByPLayer(Long id) {
        return matchDetailRepository.findAmountOfAssistsForSpecificPLayer(id);
    }

    public long totalYellowsByPLayer(Long id) {
        return matchDetailRepository.findAmountOfYellowsForSpecificPLayer(id);
    }

    public long totalRedsByPLayer(Long id) {
        return matchDetailRepository.findAmountOfRedsForSpecificPLayer(id);
    }

    public void delete(MatchDetail matchDetail) {

        matchDetail.setDeleted(MatchDetail.Deleted.YES);
        matchDetailRepository.save(matchDetail);
    }

}
