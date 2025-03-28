public class Configuration {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;
    private final int totalVendors;
    private final int totalCustomers;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate,
                         int maxTicketCapacity, int totalVendors, int totalCustomers) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalVendors = totalVendors;
        this.totalCustomers = totalCustomers;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalVendors() {
        return totalVendors;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * Validates the configuration parameters.
     *
     * @return true if the configuration is valid, false otherwise.
     */
    public boolean validateConfiguration() {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 ||
                maxTicketCapacity <= 0 || totalVendors <= 0 || totalCustomers <= 0) {
            System.out.println("All values must be positive integers greater than 0.");
            return false;
        }

        if (totalTickets < totalVendors * ticketReleaseRate) {
            System.out.println("Total tickets must be greater than or equal to the tickets released by all vendors ("
                    + totalVendors * ticketReleaseRate + ").");
            return false;
        }

        if (maxTicketCapacity < ticketReleaseRate) {
            System.out.println("Maximum ticket capacity must be greater than or equal to the ticket release rate.");
            return false;
        }

        if (customerRetrievalRate > maxTicketCapacity) {
            System.out.println("Customer retrieval rate cannot exceed the maximum ticket pool capacity.");
            return false;
        }

        return true;
    }
}
