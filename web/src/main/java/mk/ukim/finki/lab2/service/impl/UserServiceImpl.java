package mk.ukim.finki.lab2.service.impl;


import mk.ukim.finki.lab2.model.User;
import mk.ukim.finki.lab2.model.enumerations.Role;
import mk.ukim.finki.lab2.repository.jpa.UserRepository;
import mk.ukim.finki.lab2.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> login(String username, String password) throws Exception {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new Exception("LOGIN ERROR");
        }

        Optional<User> user = this.userRepository.findByUsernameAndPassword(username, password);

        List<User> users = this.userRepository.findAll();

        return user;
    }

    @Override
    public Optional<User> register(String username, String password, String name, String surname, String role) {
        Role userRole;

        if(role.equals(Role.ROLE_USER.toString())) {
            userRole = Role.ROLE_USER;
        }else{
            userRole = Role.ROLE_LIBRARIAN;
        }


        return Optional.of(this.userRepository.save(new User(username, passwordEncoder.encode(password), name, surname, userRole)));
    }

    @Override
    public Optional<User> delete(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        this.userRepository.delete(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        return this.userRepository.findAll().stream().anyMatch(x-> x.getUsername().equals(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

