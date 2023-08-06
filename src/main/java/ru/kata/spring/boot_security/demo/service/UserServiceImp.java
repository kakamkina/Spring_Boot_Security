package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDaoImp;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.reopsitory.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Component
public class UserServiceImp implements UserService, UserDetailsService {

    private UserDaoImp userDao;


    @Autowired
    public void setUserDao(UserDaoImp userDao) {
        this.userDao = userDao;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User index(long id) {
        return userDao.index(id);
    }

    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }

    @Override
    public void updateUser(long id, User updatedUser) {
        userDao.updateUser(id, updatedUser);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getNameRole())).collect(Collectors.toList());
    }

}