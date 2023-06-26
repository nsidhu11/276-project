package trackour.trackour.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import trackour.trackour.security.PassHasherSHA256;

@Entity
@Data
@Table(
    name="Users", 
    uniqueConstraints=
        @UniqueConstraint(
            columnNames={"uid", "username", "email"}
            )
        )
public class User {

    public User() {
        this.initRoles();
    }

    public User(String username, String displayName, String password, String password_salt, String email, Set<Role> roles) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.email = email;
    }

    public User(String username, String displayName, String password, String password_salt, String email) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.email = email;
        this.initRoles();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "username")
    private String username;
    
    private String displayName;


    private String password;
    private String passwordSalt;

    @Column(name = "email")
    private String email;

    /**
     * Create a one-to-many relationship of {@link User} entity to {@link Role}
     * called "user_roles". This table stores only a foreign key representing the user uid
     * and a role. Users can have an indefinite number of roles and so there can be an
     * indefinite number of the same uid in this table but each 
     * representing a different role for that particular user
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private void initRoles() {
        // initialize default role as ["USER"]
        Set<Role> test = new HashSet<>();
        test.add(Role.USER);
        setRoles(test);
    }

    // set pass
    public void setPassword(String password) {
        PassHasherSHA256 passHasher = new PassHasherSHA256(password);
        this.password = passHasher.getHashedPassword();
        setPasswordSalt(passHasher.getHashedSalt());
    }

    private void setPasswordSalt(String saltHashed) {
        this.passwordSalt = saltHashed;
    }
}
