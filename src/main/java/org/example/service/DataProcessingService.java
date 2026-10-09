package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utils.ConsoleReader;
import org.example.utils.MyArrayList;

public class DataProcessingService implements IDataService {
    private final int GET_ALL_USERS = 1;
    private final int GET_USER_BY_NAME = 2;
    private final int GET_ALL_EMAILS = 3;
    private final int GET_USER_BY_ID = 4;
    private final int DELETE_USER_BY_ID = 5;
    private final int EXIT = 0;

    private final UserRepository repo;

    public DataProcessingService(UserRepository repo) {
        this.repo = repo;
    }

    private void printMenu() {
        System.out.printf("""
                ===== PROCESSING MENU =====
                 %d. Get all users
                 %d. Get user by name
                 %d. Get all emails
                 %d. Get user by id
                 %d. Delete user
                 %d. Exit processing
                %n""",
                GET_ALL_USERS, GET_USER_BY_NAME, GET_ALL_EMAILS, GET_USER_BY_ID, DELETE_USER_BY_ID, EXIT);

        System.out.print("Choose option and press enter: ");
    }

    @Override
    public void start() {
        while (true) {
            printMenu();
            int command;
            try {
                command = ConsoleReader.readInt();
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                continue;
            }
            if (command == EXIT) {
                return;
            }
            processCommand(command);
        }
    }

    /**
     * Обработка команды
     */
    private void processCommand(int command) {
        try {
            switch (command) {
                case GET_ALL_USERS:
                    getUsersByName(null);
                    break;
                case GET_USER_BY_NAME:
                    getUsersByName();
                    break;
                case GET_ALL_EMAILS:
                    repo.getSortedEmails().forEach(System.out::println);
                    break;
                case GET_USER_BY_ID:
                    getUserById();
                    break;
                case DELETE_USER_BY_ID:
                    deleteUser();
                    break;
                default:
                    System.out.println("Command not recognized!");
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getUsersByName(String name) {
        MyArrayList<User> users = name == null || name.isEmpty()
                ? repo.getAllUsers()
                : repo.getUsersByName(name);
        if (users == null || users.size() == 0) {
            System.out.println("Users not found!");
        } else {
            users.forEach(System.out::println);
        }
    }

    /**
     * Поиск пользователей по имени
     */
    private void getUsersByName() {
        System.out.println("Enter user name");
        String userName = ConsoleReader.readString();
        getUsersByName(userName);
    }


    /**
     * Поиск пользователя по id
     */
    private void getUserById() {
        System.out.println("Enter user ID");
        User user = repo.findUserById(ConsoleReader.readLong());
        if (user != null) {
            System.out.println(user);
        }  else {
            System.out.println("User not found!");
        }
    }

    /**
     * Удалениие пользователя
     */
    private void deleteUser() {
        System.out.println("Enter user ID");
        long id;
        id = ConsoleReader.readLong();
        if (repo.deleteUser(id)) {
            System.out.println("User has been deleted!");
        } else {
            System.out.println("User is not found!");
        }
    }
}
