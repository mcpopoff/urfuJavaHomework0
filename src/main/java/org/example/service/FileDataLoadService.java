package org.example.service;

import org.example.repository.UserRepository;
import org.example.utils.ConsoleReader;
import org.example.utils.FieldsValidator;
import org.example.utils.MyArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDataLoadService  implements IDataService {
    private final static String DEFAULT_INPUT_FILE = "input.csv";
    private final UserRepository data;

    public FileDataLoadService(UserRepository data) {
        this.data = data;
    }

    @Override
    public void start() {
        System.out.printf("Enter filename from app directory or press ENTER for default value (%s). \n" +
                "File must have comma as values separators", DEFAULT_INPUT_FILE);
        String filename = ConsoleReader.readString().trim();
        loadData(filename.isBlank() ? DEFAULT_INPUT_FILE : filename);
    }

    private void loadData(String filename) {
        int cnt = 0;
        MyArrayList<String> errorList = new MyArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                if (addUserToData(line)) {
                    cnt++;
                } else {
                    errorList.add(line);
                }
            }
        } catch (IOException e) {
            System.out.printf("Error while reading file %s: %s",  filename, e.getMessage());
        }
        System.out.println("Loaded " + cnt + " users from file " + filename);
        System.out.println("Errors: " + errorList);
    }

    private boolean addUserToData(String line) {
        String[] cells =  line.split(",");
        String name = null;
        String email = null;
        Long id = null;
        if (cells.length == 2) {
            name = cells[0].trim();
            email = cells[1].trim();
        } else if (cells.length == 3) {
            String idStr = cells[0].trim();
            try {
                id = Long.parseLong(idStr);
            }  catch (NumberFormatException e) {
                return false;
            }
            name = cells[1].trim();
            email = cells[2].trim();
        }
        if (!FieldsValidator.isNameAndEmailValid(name, email)) {
            System.out.println("Name or email address is invalid");
            return false;
        }

        try {
            data.addUser(id, name, email);
            return true;
        } catch (Exception e) {
            System.out.println("Error while adding user: " + e.getMessage());
            return false;
        }
    }
}