import java.util.*;

/**
 * Library Book Reservation and Waiting Queue System
 * Uses arrays for book records and FIFO queues for reservations.
 */
public class LibrarySystem {
    private static final int MAX_BOOKS = 100;
    private static final int MAX_QUEUE = 100;

    private static final int[] bookIds = new int[MAX_BOOKS];
    private static final String[] titles = new String[MAX_BOOKS];
    private static final String[] authors = new String[MAX_BOOKS];
    private static final boolean[] available = new boolean[MAX_BOOKS];
    private static final String[] issuedTo = new String[MAX_BOOKS];
    private static final Queue<String>[] waitingQueues = new Queue[MAX_BOOKS];
    private static int bookCount = 0;

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        for (int i = 0; i < MAX_BOOKS; i++) {
            waitingQueues[i] = new LinkedList<>();
        }

        addSampleBooks();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addBook();
                case 2 -> displayBooks(false);
                case 3 -> displayBooks(true);
                case 4 -> searchBook();
                case 5 -> issueBook();
                case 6 -> returnBook();
                case 7 -> reserveBook();
                case 8 -> showWaitingQueue();
                case 9 -> {
                    running = false;
                    System.out.println("Thank you for using the Library System!");
                }
                default -> System.out.println("Invalid choice. Please select 1-9.");
            }
        }
        input.close();
    }

    private static void printMenu() {
        System.out.println("\n==========================================");
        System.out.println(" LIBRARY BOOK RESERVATION & WAITING QUEUE");
        System.out.println("==========================================");
        System.out.println("1. Add a book");
        System.out.println("2. Display all books");
        System.out.println("3. Display available books");
        System.out.println("4. Search for a book");
        System.out.println("5. Issue a book");
        System.out.println("6. Return a book");
        System.out.println("7. Reserve an unavailable book");
        System.out.println("8. View a book's waiting queue");
        System.out.println("9. Exit");
    }

    private static void addSampleBooks() {
        addBookRecord(101, "Java Programming", "Herbert Schildt");
        addBookRecord(102, "Data Structures", "Seymour Lipschutz");
        addBookRecord(103, "Computer Networks", "Andrew S. Tanenbaum");
    }

    private static void addBook() {
        if (bookCount == MAX_BOOKS) {
            System.out.println("Book storage is full.");
            return;
        }
        int id = readInt("Enter book ID: ");
        if (findBookIndex(id) != -1) {
            System.out.println("A book with this ID already exists.");
            return;
        }
        String title = readText("Enter book title: ");
        String author = readText("Enter author name: ");
        addBookRecord(id, title, author);
        System.out.println("Book added successfully.");
    }

    private static void addBookRecord(int id, String title, String author) {
        if (bookCount >= MAX_BOOKS) return;
        bookIds[bookCount] = id;
        titles[bookCount] = title;
        authors[bookCount] = author;
        available[bookCount] = true;
        issuedTo[bookCount] = "";
        waitingQueues[bookCount] = new LinkedList<>();
        bookCount++;
    }

    private static void displayBooks(boolean onlyAvailable) {
        System.out.println("\n---------------- BOOK LIST ----------------");
        boolean found = false;
        for (int i = 0; i < bookCount; i++) {
            if (!onlyAvailable || available[i]) {
                printBook(i);
                found = true;
            }
        }
        if (!found) {
            System.out.println(onlyAvailable ? "No books are currently available." : "No books found.");
        }
    }

    private static void printBook(int i) {
        System.out.println("ID: " + bookIds[i]
                + " | Title: " + titles[i]
                + " | Author: " + authors[i]
                + " | Status: " + (available[i] ? "Available" : "Issued to " + issuedTo[i])
                + " | Waiting: " + waitingQueues[i].size());
    }

    private static void searchBook() {
        String query = readText("Enter book ID or title: ").toLowerCase(Locale.ROOT);
        boolean found = false;
        for (int i = 0; i < bookCount; i++) {
            if (String.valueOf(bookIds[i]).equals(query)
                    || titles[i].toLowerCase(Locale.ROOT).contains(query)) {
                printBook(i);
                found = true;
            }
        }
        if (!found) System.out.println("No matching book found.");
    }

    private static void issueBook() {
        int index = findBookIndex(readInt("Enter book ID to issue: "));
        if (index == -1) {
            System.out.println("Book ID not found.");
            return;
        }
        if (!available[index]) {
            System.out.println("This book is already issued. You can reserve it instead.");
            return;
        }
        if (!waitingQueues[index].isEmpty()) {
            System.out.println("A waiting student has priority. Use Return Book to serve the queue.");
            return;
        }
        String student = readText("Enter student name: ");
        available[index] = false;
        issuedTo[index] = student;
        System.out.println("Book issued successfully to " + student + ".");
    }

    private static void returnBook() {
        int index = findBookIndex(readInt("Enter book ID to return: "));
        if (index == -1) {
            System.out.println("Book ID not found.");
            return;
        }
        if (available[index]) {
            System.out.println("This book is not currently issued.");
            return;
        }

        String previousStudent = issuedTo[index];
        issuedTo[index] = "";
        if (!waitingQueues[index].isEmpty()) {
            String nextStudent = waitingQueues[index].poll();
            available[index] = false;
            issuedTo[index] = nextStudent;
            System.out.println("Book returned by " + previousStudent + ".");
            System.out.println("FIFO reservation served: book issued to " + nextStudent + ".");
        } else {
            available[index] = true;
            System.out.println("Book returned successfully. It is now available.");
        }
    }

    private static void reserveBook() {
        int index = findBookIndex(readInt("Enter book ID to reserve: "));
        if (index == -1) {
            System.out.println("Book ID not found.");
            return;
        }
        if (available[index] && waitingQueues[index].isEmpty()) {
            System.out.println("The book is available. You can issue it directly.");
            return;
        }
        String student = readText("Enter student name: ");
        if (waitingQueues[index].contains(student) || student.equals(issuedTo[index])) {
            System.out.println("This student already has this book or is in its queue.");
            return;
        }
        if (waitingQueues[index].size() >= MAX_QUEUE) {
            System.out.println("Waiting queue is full.");
            return;
        }
        waitingQueues[index].offer(student);
        System.out.println("Reservation added. Position in queue: " + waitingQueues[index].size());
    }

    private static void showWaitingQueue() {
        int index = findBookIndex(readInt("Enter book ID: "));
        if (index == -1) {
            System.out.println("Book ID not found.");
            return;
        }
        System.out.println("Waiting queue for \"" + titles[index] + "\":");
        if (waitingQueues[index].isEmpty()) {
            System.out.println("No students waiting.");
            return;
        }
        int position = 1;
        for (String student : waitingQueues[index]) {
            System.out.println(position++ + ". " + student);
        }
    }

    private static int findBookIndex(int id) {
        for (int i = 0; i < bookCount; i++) {
            if (bookIds[i] == id) return i;
        }
        return -1;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }
}
