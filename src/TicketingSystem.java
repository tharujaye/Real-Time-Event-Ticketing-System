import java.util.concurrent.atomic.AtomicInteger;

public class TicketingSystem {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;

    private final AtomicInteger ticketsReleased = new AtomicInteger(0);
    private final AtomicInteger ticketsRetrieved = new AtomicInteger(0);
    private int currentTicketPool = 0;

    public TicketingSystem(Configuration config) {
        this.totalTickets = config.getTotalTickets();
        this.ticketReleaseRate = config.getTicketReleaseRate();
        this.customerRetrievalRate = config.getCustomerRetrievalRate();
        this.maxTicketCapacity = config.getMaxTicketCapacity();
    }

    public synchronized void addTickets(int vendorId) {
        int ticketsToAdd = Math.min(ticketReleaseRate, totalTickets - ticketsReleased.get());
        if (currentTicketPool + ticketsToAdd <= maxTicketCapacity) {
            ticketsReleased.addAndGet(ticketsToAdd);
            currentTicketPool += ticketsToAdd;
            Logger.log("Vendor " + vendorId + " added " + ticketsToAdd + " tickets. Current pool size: " + currentTicketPool);
        } else {
            Logger.log("Vendor " + vendorId + " could not add tickets. Pool is full.");
        }
    }

    public synchronized void removeTicket(int customerId) {
        if (currentTicketPool > 0) {
            ticketsRetrieved.incrementAndGet();
            currentTicketPool--;
            Logger.log("Customer " + customerId + " purchased a ticket. Tickets left in pool: " + currentTicketPool);
        } else {
            Logger.log("Customer " + customerId + " could not purchase a ticket. Pool is empty.");
        }
    }

    public synchronized boolean hasMoreTicketsToRelease() {
        return ticketsReleased.get() < totalTickets;
    }

    public synchronized boolean hasMoreTicketsToRetrieve() {
        return ticketsRetrieved.get() < totalTickets;
    }
}
