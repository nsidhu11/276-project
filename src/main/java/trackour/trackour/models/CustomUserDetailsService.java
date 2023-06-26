package trackour.trackour.models;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> get(Long uid) {
        return repository.findByUid(uid);
    }

    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User update(User entity) {
        return repository.saveAndFlush(entity);
    }

    public void delete(Long uid) {
        repository.deleteByUid(uid);
    }

    public int count() {
        return (int) repository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), getAuthorities(user.get()));
        }
    }

    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(authority -> {
            return new SimpleGrantedAuthority(authority.getName());
        }).collect(Collectors.toList());
    }
    
    /**
     * Prettyy print {@link User} object
     * @param user
     */
    private void printUserObj(User user) {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT); //pretty print
        String objStr;
        try {
            objStr = objMapper.writeValueAsString(user);
            System.out.println(objStr);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * eg. Call {@code passwordEncoder().encode("rawPasscode");} to encrypt "rawPasscode".
     * To just get an instance of the encoder, call {@code passwordEncoder()}
     * @return {@link BCryptPasswordEncoder} encrypted String
     */
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Recieve new user credentials, validates then updates database with this new user,
     * @param username
     * @param password
     */
    public boolean registerUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        // doSecurePassword(newUser, password);
        // unable to properly implement hashing technique atm so will either drop that or tryb again later
        String encodedPassword = passwordEncoder().encode(password);
        String encodePasswordSalt = passwordEncoder().encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setPasswordSalt(encodePasswordSalt);
        printUserObj(newUser);
        // if user doesn't already exist do new registration
        Optional<User> existingUser = getByUsername(username);
        if (!existingUser.isPresent()){
            update(newUser);
            return true;
        }   
        return false;
    }

    // private void doSecurePassword(User newUser, String password) {
    //     // hash -(then)-> encrypt
    //     PassHasherSHA256 hasher1 = new PassHasherSHA256(password);
    //     String encodedPassword = passwordEncoder().encode(hasher1.getHashedPassword());
    //     String encodePasswordSalt = passwordEncoder().encode(hasher1.getHashedSalt());
    //     newUser.setPassword(encodedPassword);
    //     newUser.setPasswordSalt(encodePasswordSalt);
    // }
}
