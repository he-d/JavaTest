package at.ac.uibk.estore.repository;

import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u.username as username, u.email as email, u.firstname as firstname, u.lastname as lastname, u.address as address FROM User u WHERE u.username = :username")
    Optional<UserProjection> getUserInfo(String username);

    Iterable<User> findByNotificationTrue();
}
