package hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task0 {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void printGreeting() {
        LOGGER.info("Привет, мир!");
    }

    public static void main(String[] args) {
        printGreeting();
    }

    private Task0() {
    }

}
