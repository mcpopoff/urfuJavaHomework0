package org.example;

import org.example.repository.UserRepository;
import org.example.service.DataProcessingService;
import org.example.service.FileDataLoadService;
import org.example.service.FileSaveService;
import org.example.service.IDataService;
import org.example.service.ManualDataLoadService;
import org.example.utils.ConsoleReader;

public class App {
    private final int MANUAL_ADDING = 1;
    private final int ADDING_FROM_FILE = 2;
    private final int DATA_PROCESSING = 3;
    private final int SAVE_TO_FILE = 4;
    private final int EXIT = 0;

    private static IDataService processingService;
    private static IDataService fileService;
    private static IDataService manualService;
    private static IDataService saveService;
    UserRepository repo;

    public App() {
        repo = new UserRepository();
        processingService = new DataProcessingService(repo);
        fileService = new FileDataLoadService(repo);
        manualService = new ManualDataLoadService(repo);
        saveService = new FileSaveService(repo);
    }

    private void printMenu() {
        System.out.printf("""
                ===== MAIN MENU =====
                 %d. Manual adding user
                 %d. Load data from file
                 %d. Process data
                 %d. Save to file
                 %d. Exit
                """, MANUAL_ADDING, ADDING_FROM_FILE, DATA_PROCESSING, SAVE_TO_FILE, EXIT);

        System.out.print("Choose option and press enter: ");
    }

    public void run() {
        while (true) {
            printMenu();
            int command;
            try {
                command = ConsoleReader.readInt();
            } catch (NumberFormatException e) {
                System.out.println("You need to type a number");
                continue;
            }
            if (command == EXIT) {
                System.out.println("Application closing...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {

                }
                return;
            }
            processCommand(command);
        }
    }

    /**
     * Обработка команды
     */
    private void processCommand(int command) {
        switch (command) {
            case MANUAL_ADDING:
                manualService.start();
                break;
            case ADDING_FROM_FILE:
                fileService.start();
                break;
            case DATA_PROCESSING:
                processingService.start();
                break;
            case SAVE_TO_FILE:
                saveService.start();
                break;
            default:
                System.out.println("Command not recognized!");
        }
    }
}
