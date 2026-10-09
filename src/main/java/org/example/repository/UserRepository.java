package org.example.repository;

import org.example.model.User;
import org.example.utils.MyArrayList;

import java.util.Objects;

public class UserRepository {
    long seqId = 0L;

    private final MyArrayList<User> users;

    public UserRepository() {
        this.users = new MyArrayList<>();
    }

    /**
     * Добавит пользователя
     */
    public void addUser(Long id, String name, String email) {
        long finalId = id == null ? seqId++ : id;
        User user = new User(finalId, name, email);
        if (users.contains(user)) {
            throw new RuntimeException("User with id " + finalId + " already exists");
        }
        users.add(user);
        if (finalId >  seqId) {
            seqId = ++finalId;
        }
    }

    /**
     * Добавит пользователя
     */
    public void addUser(String name, String email) {
        addUser(null, name, email);
    }

    /**
     * Вернет всех пользователей
     */
    public MyArrayList<User> getAllUsers() {
        MyArrayList<User> list = new MyArrayList<>();
        users.stream()
                .filter(Objects::nonNull)
                .forEach(list::add);
        return list;
    }

    /**
     * Вернет всех пользователей с указанным именем
     */
    public MyArrayList<User> getUsersByName(String name) {
        MyArrayList<User> list = new MyArrayList<>();
        users.stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getName().equals(name))
                .forEach(list::add);
        return list;
    }

    /**
     * Вернет отсортированный список email'ов
     */
    public MyArrayList<String> getSortedEmails() {
        MyArrayList<String> list = new MyArrayList<>();
        users.stream()
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .filter(Objects::nonNull)
                .sorted()
                .forEach(list::add);
        return list;
    }

    public boolean deleteUser(long id) {
        return users.remove(new User(id));
    }

    /**
     * Поиск пользователя по ид
     */
    public User findUserById(long id) {
        int ind = users.indexOf(new User(id));
        return ind >= 0 ? users.get(ind) : null;
    }
}
