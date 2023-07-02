package fsd.repositories;

import fsd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role = 'PLAYER'")
    List<User> findAllPlayers();

    @Query("SELECT u FROM User u WHERE u.role = 'COACH'")
    List<User> findAllCoaches();

    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN'")
    List<User> findAllAdmins();

    @Query("SELECT u FROM User u WHERE u.deleted = 'NO'")
    List<User> findCurrentUsers();

    @Query("SELECT u FROM User u WHERE u.deleted = 'NO' and u.role = 'PLAYER'")
    List<User> findCurrentPlayers();

    @Query("SELECT u FROM User u WHERE u.deleted = 'NO' and u.role = 'COACH'")
    List<User> findCurrentCoaches();

    @Query("SELECT u FROM User u WHERE u.deleted = 'NO' and u.role = 'ADMIN'")
    List<User> findCurrentAdmins();

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

}
