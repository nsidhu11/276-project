package trackour.trackour.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUid(int uid);
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByDisplayName(String displayName);
    List<User> findByEmail(String email);
}
