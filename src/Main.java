import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Logger.log("Initializing Ticketing System...");

        Configuration config = ConfigurationCLI.loadOrPromptConfiguration();

        if (!config.validateConfiguration()) {
            Logger.log("Invalid configuration parameters. Exiting.");
            System.out.println("Invalid configuration. Please restart the application and configure correctly.");
            return;
        }

        Logger.log("Configuration is valid. Starting the ticketing system...");

        TicketingSystem ticketingSystem = new TicketingSystem(config);

        // Create threads for vendors
        List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 0; i < config.getTotalVendors(); i++) {
            Thread vendorThread = new Thread(new Vendor(ticketingSystem, i + 1));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create threads for customers
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 0; i < config.getTotalCustomers(); i++) {
            Thread customerThread = new Thread(new Customer(ticketingSystem, i + 1));
            customerThreads.add(customerThread);
            customerThread.start();
        }

        // Wait for all threads to finish
        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            Logger.log("Thread execution interrupted: " + e.getMessage());
        }

        Logger.log("Ticketing system operations completed.");
        System.out.println("Ticketing system operations completed. Check logs for details.");
    }
}
