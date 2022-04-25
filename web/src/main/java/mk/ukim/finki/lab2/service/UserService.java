package mk.ukim.finki.lab2.service;

import mk.ukim.finki.lab2.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> login(String username, String password) throws Exception;
    Optional<User> register(String username, String password, String name, String surname, String role);
    Optional<User> delete(String username);
    Optional<User> findByUsername(String username);
    boolean checkIfUsernameExists(String username);

    @Override
    default UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
