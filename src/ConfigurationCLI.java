import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigurationCLI {
    private static final Scanner scanner = new Scanner(System.in);

    public static Configuration loadOrPromptConfiguration() {
        System.out.println("Welcome to the Ticketing System Configuration.");
        System.out.println("Would you like to load the existing configuration or set up a new one?");
        System.out.println("1. Load Existing Configuration");
        System.out.println("2. Set Up New Configuration");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = getIntInput(1, 2); // Use the new method here for input validation
        Configuration config;

        if (choice == 1) {
            config = loadConfiguration();
            if (config != null) {
                System.out.println("Loaded configuration successfully.");
                return config;
            }
            System.out.println("No valid configuration found. Proceeding to set up a new configuration.");
        }

        config = promptConfiguration();
        saveConfiguration(config);
        return config;
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Configuration promptConfiguration() {
        System.out.println("Setting up a new configuration...");
        int totalTickets = promptForValue("Enter the total number of tickets: ", 1, Integer.MAX_VALUE);
        int ticketReleaseRate = promptForValue("Enter the ticket release rate (tickets per vendor): ", 1, totalTickets);
        int customerRetrievalRate = promptForValue("Enter the customer retrieval rate (tickets per customer): ", 1, Integer.MAX_VALUE);
        int maxTicketCapacity = promptForValue("Enter the maximum ticket pool capacity: ", ticketReleaseRate, Integer.MAX_VALUE);
        int totalVendors = promptForValue("Enter the total number of vendors: ", 1, totalTickets / ticketReleaseRate);
        int totalCustomers = promptForValue("Enter the total number of customers: ", 1, Integer.MAX_VALUE);

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, totalVendors, totalCustomers);
    }

    private static Configuration loadConfiguration() {
        try (Scanner fileScanner = new Scanner(new File("config.txt"))) {
            int totalTickets = fileScanner.nextInt();
            int ticketReleaseRate = fileScanner.nextInt();
            int customerRetrievalRate = fileScanner.nextInt();
            int maxTicketCapacity = fileScanner.nextInt();
            int totalVendors = fileScanner.nextInt();
            int totalCustomers = fileScanner.nextInt();

            return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, totalVendors, totalCustomers);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load configuration. " + e.getMessage());
            return null;
        }
    }

    private static void saveConfiguration(Configuration config) {
        try (FileWriter writer = new FileWriter("config.txt")) {
            writer.write(config.getTotalTickets() + "\n");
            writer.write(config.getTicketReleaseRate() + "\n");
            writer.write(config.getCustomerRetrievalRate() + "\n");
            writer.write(config.getMaxTicketCapacity() + "\n");
            writer.write(config.getTotalVendors() + "\n");
            writer.write(config.getTotalCustomers() + "\n");
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save configuration. " + e.getMessage());
        }
    }

    private static int promptForValue(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Configuration setUpNewConfiguration() {
        System.out.println("Setting up a new configuration...");

        int totalTickets = promptForValue("Enter the total number of tickets: ", 1, Integer.MAX_VALUE);
        int ticketReleaseRate = promptForValue("Enter the ticket release rate (tickets per vendor): ", 1, totalTickets);
        int customerRetrievalRate = promptForValue("Enter the customer retrieval rate (tickets per customer): ", 1, totalTickets);
        int maxTicketCapacity = promptForValue("Enter the maximum ticket pool capacity: ", 1, totalTickets);

        // Remove any hardcoded or invalid max limit here
        int totalVendors = promptForValue("Enter the total number of vendors: ", 1, Integer.MAX_VALUE);
        int totalCustomers = promptForValue("Enter the total number of customers: ", 1, Integer.MAX_VALUE);

        Configuration config = new Configuration(
                totalTickets,
                ticketReleaseRate,
                customerRetrievalRate,
                maxTicketCapacity,
                totalVendors,
                totalCustomers
        );

        saveConfiguration(config);
        System.out.println("Configuration saved successfully.");
        return config;
    }

}


