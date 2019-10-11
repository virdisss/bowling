package com.bowling.challenge;

import com.bowling.challenge.dao.Bowling;
import com.bowling.challenge.dao.impl.BowlingImpl;
import com.bowling.challenge.exception.BowlingException;
import com.bowling.challenge.services.impl.BowlingServiceImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private Bowling bowling;
    
    private void setMenu() {
        System.out.println("\n_______________________MENU_______________________");
        System.out.print("Press:\n1 - Run Bowling Game\n2 - Exit\nOption>> ");
    }

    private boolean validate(String optionString) {
        if (!optionString.matches("-?\\d+(\\.\\d+)?")) {
            return false;
        }
        return optionString.equals("1") || optionString.equals("2");
    }
    
    private boolean exit(String optionString) {
        return optionString.equals("2");
    }

    private int processOption(String optionString) {
        Map<Integer, String> optionMap = new HashMap<>();
        optionMap.put(1, "Insert a file path>> ");
        optionMap.put(2, "Exiting...\n\n");
        int opt = Integer.parseInt(optionString);
        System.out.print(optionMap.get(opt));
        return opt;
    }

    private void runBowlingGame(String filePath) {
        try {
            bowling = new BowlingImpl(new BowlingServiceImpl());
            bowling.populateGameMap(bowling.loadFrames(filePath));
            bowling.handleScore();
            bowling.display();
        } catch (IOException | BowlingException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }
    
    public static void main(String[] args) {

        App app = new App();
        Scanner scanner = new Scanner(System.in);
        String optionString;     
        
        do {
            app.setMenu();
            optionString = scanner.nextLine();
            if (!app.validate(optionString)) {
                System.out.println("Invalid option");
            } else if (app.processOption(optionString) == 1) {
                String filePath = scanner.nextLine();
                System.out.println();
                app.runBowlingGame(filePath);
            }
        } while (!app.exit(optionString));
    }
}
