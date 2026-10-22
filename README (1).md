# Library Book Reservation and Waiting Queue System

A Java-based mini project demonstrating arrays and a FIFO (First-In-First-Out) queue in a library setting.

## Features

- Add and display book details
- Display available books
- Search by book ID or title
- Issue books to students
- Return issued books
- Reserve a book that is already issued
- View each book's waiting queue
- Automatically issue a returned book to the first waiting student
- HTML/CSS browser demo with book search, filters, and interactive sample actions

## Technologies

- Java (main application logic)
- Arrays (book records)
- Queue / `LinkedList` (FIFO waiting list)
- HTML
- CSS
- JavaScript (frontend demonstration only)

## Files

- `LibrarySystem.java` — Java console application
- `index.html` — browser interface and small standalone demo
- `style.css` — responsive page styling
- `README.md` — project instructions

## Run the Java application

1. Install a JDK (Java 17 or newer is recommended).
2. Open a terminal in the folder containing `LibrarySystem.java`.
3. Compile:

   ```bash
   javac LibrarySystem.java
   ```

4. Run:

   ```bash
   java LibrarySystem
   ```

The Java program starts with three sample books: Java Programming, Data Structures, and Computer Networks.

## Open the browser demo

Open `index.html` in a modern browser. It demonstrates the same basic workflow using in-browser sample data. Its data is temporary and resets when the page is reloaded.

**Important:** A normal browser cannot execute the Java console program directly. The HTML/CSS/JavaScript demo is separate from the Java console application. Connecting a real browser frontend to the Java backend would require a web server/API and additional setup.

## Publish the frontend with GitHub Pages

1. Create a GitHub repository and upload `index.html`, `style.css`, `LibrarySystem.java`, and `README.md` to the repository root.
2. Open the repository's **Settings → Pages**.
3. Under **Build and deployment**, choose **Deploy from a branch**.
4. Select branch `main` and folder `/ (root)`, then save.
5. Wait for GitHub Pages to publish the page. The published link will appear in the Pages section.

GitHub Pages hosts the static HTML/CSS/JavaScript frontend. It does **not** run `LibrarySystem.java`.

## Data structure logic

- **Array:** stores book ID, title, author, availability, and issued student details.
- **Queue:** stores students waiting for a book.
- **FIFO:** the first student to reserve is the first student served when the book is returned.

## Possible future improvements

- Save book records permanently in a database or file
- Add student IDs and due dates
- Add a Java web backend so the website calls the Java logic
- Add login and librarian roles

## Author

Student project — Library Book Reservation and Waiting Queue System.
