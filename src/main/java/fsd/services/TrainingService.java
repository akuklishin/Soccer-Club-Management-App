package fsd.services;

import fsd.entities.Training;
import fsd.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    public Optional<Training> getById(Long id) {
        return trainingRepository.findById(id);
    }

    public List<Training> getAll() {
        return trainingRepository.findAll();
    }

    public List<Training> getAllExistingTrainings() {
        return trainingRepository.findExistingTrainings(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<Training> getAllGoalkeepersTrainings() {
        return trainingRepository.findAllGoalkeepersTrainings(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<Training> getAllDefendersTrainings() {
        return trainingRepository.findAllDefendersTrainings(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<Training> getAllAttackersTrainings() {
        return trainingRepository.findAllAttackersTrainings(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<Training> getTrainingsArchive() {
        return trainingRepository.getTrainingsArchive(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Training> getAllGoalkeepersTrainingsArchive() {
        return trainingRepository.findAllGoalkeepersTrainingsArchive(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Training> getAllDefendersTrainingsArchive() {
        return trainingRepository.findAllDefendersTrainingsArchive(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Training> getAllAttackersTrainingsArchive() {
        return trainingRepository.findAllAttackersTrainingsArchive(Sort.by(Sort.Direction.DESC, "date"));
    }

    public Training save(Training training) {
        return trainingRepository.save(training);
    }


    public void delete(Training training) {

        training.setDeleted(Training.Deleted.YES);
        trainingRepository.save(training);
    }

}
