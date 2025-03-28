public class Customer implements Runnable {
    private final TicketingSystem ticketingSystem;
    private final int customerId;

    public Customer(TicketingSystem ticketingSystem, int customerId) {
        this.ticketingSystem = ticketingSystem;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        Logger.log("Customer " + customerId + " has started attempting to purchase tickets.");
        while (ticketingSystem.hasMoreTicketsToRetrieve()) {
            synchronized (ticketingSystem) {
                ticketingSystem.removeTicket(customerId); // Pass the customerId here
            }
            try {
                Thread.sleep(1000); // Simulate ticket purchase rate
            } catch (InterruptedException e) {
                Logger.log("Customer " + customerId + " interrupted: " + e.getMessage());
            }
        }
        Logger.log("Customer " + customerId + " has finished purchasing tickets.");
    }
}
