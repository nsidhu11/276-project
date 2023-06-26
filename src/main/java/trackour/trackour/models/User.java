package trackour.trackour.models;

import jakarta.persistence.*;

@Entity
@Table(
    name="Users", 
    uniqueConstraints=
        @UniqueConstraint(
            columnNames={"uid", "username", "email"}
            )
        )
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int uid;

    @Column(name = "username")
    private String username;

    
    private String displayName;
    private String password;

    @Column(name = "email")
    private String email;

    public User() {}

    public User(String username, String displayName, String password, String email) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
