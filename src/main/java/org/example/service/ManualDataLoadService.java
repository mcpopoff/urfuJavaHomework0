package org.example.service;

import org.example.repository.UserRepository;
import org.example.utils.ConsoleReader;
import org.example.utils.FieldsValidator;


public class ManualDataLoadService  implements IDataService {
    private static final String EXIT_INPUT = "n";
    private final UserRepository data;

    public ManualDataLoadService(UserRepository data) {
        this.data = data;
    }

    @Override
    public void start() {
        while (true) {
            addUser();
            String agreed = ConsoleReader.readString();
            if (agreed.equalsIgnoreCase(EXIT_INPUT)) {
                return;
            }
        }
    }

    private void addUser() {
        System.out.println("enter User name:");
        String name = ConsoleReader.readString();
        System.out.println("enter User email");
        String email = ConsoleReader.readString();
        if (FieldsValidator.isNameAndEmailValid(name, email )) {
            data.addUser(name,email);
            System.out.printf("User successfully added! Add another one?(type '%s' to exit)", EXIT_INPUT);
        } else {
            System.out.printf("invalid input, try again?(type '%s' to exit)", EXIT_INPUT);
        }
    }
}
