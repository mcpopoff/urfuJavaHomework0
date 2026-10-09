package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utils.ConsoleReader;
import org.example.utils.MyArrayList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSaveService implements IDataService {
    private static final String DEFAULT_OUTPUT_FILE = "input.csv";

    private final UserRepository data;

    public FileSaveService(UserRepository data) {
        this.data = data;
    }

    @Override
    public void start() {
        System.out.printf("Enter filename to save or press ENTER for default (%s): ", DEFAULT_OUTPUT_FILE);
        String filename = ConsoleReader.readString().trim();
        saveData(filename.isBlank() ? DEFAULT_OUTPUT_FILE : filename);
    }

    private void saveData(String filename) {
        MyArrayList<User> users = data.getAllUsers();
        int cnt = 0;

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename), StandardCharsets.UTF_8)) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                bw.write(user.getId() + "," + user.getName() + "," + user.getEmail());
                bw.newLine();
                cnt++;
            }
        } catch (IOException e) {
            System.out.printf("Error while saving file %s: %s%n", filename, e.getMessage());
            return;
        }

        System.out.printf("Saved %d users to file %s%n", cnt, filename);
    }
}
