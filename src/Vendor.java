import java.util.ArrayList;
import java.util.List;

public class Vendor implements Runnable {
    private final TicketingSystem ticketingSystem;
    private final int vendorId;

    public Vendor(TicketingSystem ticketingSystem, int vendorId) {
        this.ticketingSystem = ticketingSystem;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        Logger.log("Vendor " + vendorId + " has started releasing tickets.");
        while (ticketingSystem.hasMoreTicketsToRelease()) {
            synchronized (ticketingSystem) {
                ticketingSystem.addTickets(vendorId);
            }
            try {
                Thread.sleep(1000); // Simulate ticket release rate
            } catch (InterruptedException e) {
                Logger.log("Vendor " + vendorId + " interrupted: " + e.getMessage());
            }
        }
        Logger.log("Vendor " + vendorId + " has finished releasing tickets.");
    }
}

public class Vendor {
    List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 0; i < config.getTotalVendors(); i++) {
        Thread vendorThread = new Thread(new Vendor(ticketingSystem, i + 1));
        vendorThreads.add(vendorThread);
        vendorThread.start();
        }
}