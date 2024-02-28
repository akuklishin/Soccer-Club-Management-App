package fsd.services;

import fsd.entities.User;
import fsd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //get user by ID
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    //get all users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //find all users with ROLE PLAYER
    public List<User> getAllPLayers() {
        return userRepository.findAllPlayers();
    }

    //find all users with ROLE COACH
    public List<User> getAllCoaches() {
        return userRepository.findAllCoaches();
    }

    //find all users with ROLE ADMIN
    public List<User> getAllAdmins() {
        return userRepository.findAllAdmins();
    }

    //find CURRENT (not "deleted") players
    public List<User> getAllCurrentPlayers() {
        return userRepository.findCurrentPlayers();
    }

    //find CURRENT (not "deleted") coaches
    public List<User> getAllCurrentCoaches() {
        return userRepository.findCurrentCoaches();
    }

    //find CURRENT (not "deleted") admins
    public List<User> getAllCurrentAdmins() {
        return userRepository.findCurrentAdmins();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean exists(String email) {
        boolean exists = userRepository.findByEmail(email).isPresent();
        if (exists) {
            return true;
        } else {
            return false;
        }
    }

    // save new user
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //delete user
    public void delete(User user) {

        user.setDeleted(User.Deleted.YES);
        userRepository.save(user);
    }
}
