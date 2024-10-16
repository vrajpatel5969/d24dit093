import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Ticket {
    private int seatNumber;
    private double price;
    private boolean isBooked;

    public Ticket(int seatNumber, double price) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.isBooked = false;
    }

    public boolean book() {
        if (!isBooked) {
            isBooked = true;
            return true;
        }
        return false;
    }

    public boolean cancel() {
        if (isBooked) {
            isBooked = false;
            return true;
        }
        return false;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }
}

class Event {
    private String name;
    private String date;
    private List<Ticket> tickets;

    public Event(String name, String date, int ticketCount, double price) {
        this.name = name;
        this.date = date;
        tickets = new ArrayList<>();
        for (int i = 1; i <= ticketCount; i++) {
            tickets.add(new Ticket(i, price));
        }
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void displayAvailableTickets() {
        for (Ticket ticket : tickets) {
            if (!ticket.isBooked()) {
                System.out.println("Seat " + ticket.getSeatNumber() + " - Price: $" + ticket.getPrice());
            }
        }
    }
}

class BookingSystem {
    private List<Event> events;

    public BookingSystem() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void displayEvents() {
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            System.out.println((i + 1) + ". " + event.getName() + " on " + event.getDate());
        }
    }

    public boolean bookTicket(int eventIndex, int seatNumber) {
        Event event = events.get(eventIndex);
        for (Ticket ticket : event.getTickets()) {
            if (ticket.getSeatNumber() == seatNumber && ticket.book()) {
                System.out.println("Ticket booked for " + event.getName() + " at seat " + seatNumber);
                return true;
            }
        }
        System.out.println("Failed to book ticket. Seat may be already booked.");
        return false;
    }

    public boolean cancelTicket(int eventIndex, int seatNumber) {
        Event event = events.get(eventIndex);
        for (Ticket ticket : event.getTickets()) {
            if (ticket.getSeatNumber() == seatNumber && ticket.cancel()) {
                System.out.println("Ticket canceled for " + event.getName() + " at seat " + seatNumber);
                return true;
            }
        }
        System.out.println("Failed to cancel ticket. Seat may not be booked.");
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        system.addEvent(new Event("Concert", "2023-12-01", 100, 50.0));
        system.addEvent(new Event("Play", "2023-11-15", 50, 30.0));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAvailable Events:");
            system.displayEvents();
            System.out.println("Select an event number to book tickets, or type -1 to exit:");

            int eventChoice = scanner.nextInt();
            if (eventChoice == -1) break;

            if (eventChoice < 1 || eventChoice > system.getEvents().size()) {
                System.out.println("Invalid event choice. Please try again.");
                continue;
            }

            Event selectedEvent = system.getEvents().get(eventChoice - 1);
            System.out.println("Available tickets for " + selectedEvent.getName() + ":");
            selectedEvent.displayAvailableTickets();

            System.out.println("Enter seat number to book:");
            int seatNumber = scanner.nextInt();

            if (seatNumber < 1 || seatNumber > selectedEvent.getTickets().size()) {
                System.out.println("Invalid seat number. Please try again.");
                continue;
            }

            system.bookTicket(eventChoice - 1, seatNumber);
        }

        scanner.close();
    }
}