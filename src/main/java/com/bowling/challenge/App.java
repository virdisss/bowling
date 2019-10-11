package com.bowling.challenge;

import com.bowling.challenge.dao.Bowling;
import com.bowling.challenge.dao.Game;
import com.bowling.challenge.dao.impl.BowlingImpl;
import com.bowling.challenge.exception.BowlingException;
import com.bowling.challenge.services.impl.BowlingServiceImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private Bowling bowling;
    private Map<String, String> optionMap;
    
    public App() {
        optionMap = new HashMap<>();
        bowling = new BowlingImpl(new BowlingServiceImpl());
        optionMap.put("1", "Insert a file path>> ");
        optionMap.put("2", "Exiting...\n\n");
    }
    
    // Delegate Methods
    public List<String> loadFrames(String path) throws IOException, BowlingException {
        return bowling.loadFrames(path);
    }

    public Map<String, Game> populateGameMap(List<String> lines) {
        return bowling.populateGameMap(lines);
    }

    public void handleScore() {
        bowling.handleScore();
    }

    public void display() {
        bowling.display();
    }
    
    public void init() {
        bowling.init();
    }
    
    private void setMenu() {
        System.out.println("\n_______________________MENU_______________________");
        System.out.print("Press:\n1 - Run Bowling Game\n2 - Exit\nOption>> ");
    }

    private boolean validate(String optionString) {
        return optionString.equals("1") || optionString.equals("2");
    }
    
    //Useful Methods
    private boolean exit(String optionString) {
        return optionString.equals("2");
    }

    private void displaySelectedOption(String optionString) {
        System.out.print(optionMap.get(optionString));
    }

    // Entry Main method
    public static void main(String[] args) {

        App app = new App();
        Scanner scanner = new Scanner(System.in);
        String optionString;     
        
        do {
            app.init();
            app.setMenu();
            optionString = scanner.nextLine();
            if (!app.validate(optionString)) {
                System.out.println(optionString + " is not a valid option. Available options {1, 2}");
            } else {
                app.displaySelectedOption(optionString);
                if (optionString.equals("1")) {
                    try {
                        String filePath = scanner.nextLine();
                        app.populateGameMap(app.loadFrames(filePath));
                        app.handleScore();
                        app.display();
                    } catch (IOException | BowlingException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }   
                }
            }
        } while (!app.exit(optionString));
    }
}
