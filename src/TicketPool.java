import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new LinkedList<>());
        this.maxCapacity = maxCapacity;
    }

    public synchronized boolean addTickets(int vendorId, int ticketCount) {
        if (tickets.size() + ticketCount > maxCapacity) {
            Logger.log("Vendor " + vendorId + " tried to add tickets, but the pool is full.");
            return false;
        }
        for (int i = 0; i < ticketCount; i++) {
            tickets.add("Ticket-" + (tickets.size() + 1));
        }
        Logger.log("Vendor " + vendorId + " added " + ticketCount + " tickets. Total: " + tickets.size());
        notifyAll(); // Notify waiting customers
        return true;
    }

    public synchronized String removeTicket(int customerId) {
        while (tickets.isEmpty()) {
            Logger.log("Customer " + customerId + " is waiting for tickets...");
            try {
                wait(); // Wait for tickets to be added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        String ticket = tickets.remove(0);
        Logger.log("Customer " + customerId + " purchased " + ticket + ". Remaining: " + tickets.size());
        notifyAll(); // Notify vendors if space becomes available
        return ticket;
    }

    public synchronized boolean isSoldOut() {
        return tickets.isEmpty();
    }
}
