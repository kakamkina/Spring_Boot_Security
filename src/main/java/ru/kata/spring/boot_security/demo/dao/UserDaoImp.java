package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e", User.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public User index(long id) {
        Query query = entityManager.createQuery("SELECT e FROM User e WHERE id =:id")
                .setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Transactional
    public void createUser(User user) {
        entityManager.createNativeQuery
                        ("INSERT INTO User (name, age, cityOfBirth, username, password) VALUES (:name, :age, :cityOfBirth, :username, :password)")
                .setParameter("name", user.getName())
                .setParameter("age", user.getAge())
                .setParameter("cityOfBirth", user.getCityOfBirth())
                .setParameter("username", user.getUsername())
                .setParameter("password", passwordEncoder.encode(user.getPassword()))
                .executeUpdate();
    }

    @Transactional
    public void updateUser(long id, User updatedUser) {

        if (updatedUser.getPassword() == null) {

        }
        else {}

        entityManager.createQuery("UPDATE User u SET u.name=:name, u.age=:age, u.cityOfBirth=:cityOfBirth, u.username=:username, u.password=:password WHERE u.id=:id")
                .setParameter("id", updatedUser.getId())
                .setParameter("name", updatedUser.getName())
                .setParameter("age", updatedUser.getAge())
                .setParameter("cityOfBirth", updatedUser.getCityOfBirth())
                .setParameter("username", updatedUser.getUsername())
                .setParameter("password", passwordEncoder.encode(updatedUser.getPassword()))
                .executeUpdate();
    }

    @Transactional
    public void deleteUser(long id) {
        entityManager.createQuery
                        ("DELETE FROM User u WHERE u.id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public User findById(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

}
