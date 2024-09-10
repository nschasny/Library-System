import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibrarySystem {
    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Librarian> librarians = new HashMap<>();
    private static Librarian librarian = new Librarian("Alice", "L001");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeLibrary();

        while (true) {
            System.out.println("Library System");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Librarian");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    studentLogin();
                    break;
                case 2:
                    librarianLogin();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeLibrary() {
        // Add some initial books
        librarian.addBook(new Book("1984", "George Orwell", "123456789"));
        librarian.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "987654321"));

        // Add some initial users
        students.put("S001", new Student("Bob", "S001"));
        students.put("S002", new Student("Jane", "S002"));
        librarians.put("L001", librarian);
    }

    private static void studentLogin() {
        System.out.println("Enter Student ID:");
        String userId = scanner.nextLine();
        Student student = students.get(userId);

        if (student != null) {
            studentMenu(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void librarianLogin() {
        System.out.println("Enter Librarian ID:");
        String userId = scanner.nextLine();
        Librarian lib = librarians.get(userId);

        if (lib != null) {
            librarianMenu(lib);
        } else {
            System.out.println("Librarian not found.");
        }
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("Student Menu");
            System.out.println("1. View Borrowed Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. View Overdue Books");
            System.out.println("5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewBorrowedBooks(student);
                    break;
                case 2:
                    borrowBook(student);
                    break;
                case 3:
                    returnBook(student);
                    break;
                case 4:
                    viewOverdueBooks(student);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void librarianMenu(Librarian lib) {
        while (true) {
            System.out.println("Librarian Menu");
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. List All Books");
            System.out.println("4. View Overdue Books");
            System.out.println("5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook(lib);
                    break;
                case 2:
                    removeBook(lib);
                    break;
                case 3:
                    lib.listBooks();
                    break;
                case 4:
                    viewOverdueBooks(lib);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewBorrowedBooks(Student student) {
        if (student.getBorrowedBooks().isEmpty()) {
            System.out.println("You have not borrowed any books.");
        } else {
            for (Book book : student.getBorrowedBooks()) {
                System.out.println(book + " (Due: " + book.getDueDate() + ")");
            }
        }
    }

    private static void borrowBook(Student student) {
        System.out.println("Enter ISBN of the book to borrow:");
        String isbn = scanner.nextLine();
        Book book = librarian.findBookByIsbn(isbn);

        if (book != null) {
            student.borrowBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void returnBook(Student student) {
        System.out.println("Enter ISBN of the book to return:");
        String isbn = scanner.nextLine();
        Book book = librarian.findBookByIsbn(isbn);

        if (book != null) {
            student.returnBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void viewOverdueBooks(User user) {
        if (user instanceof Student) {
            Student student = (Student) user;
            if (student.hasOverdueBooks()) {
                System.out.println("Overdue Books:");
                for (Book book : student.getBorrowedBooks()) {
                    if (book.getDueDate() != null && book.getDueDate().isBefore(LocalDate.now())) {
                        System.out.println(book + " (Due: " + book.getDueDate() + ")");
                    }
                }
            } else {
                System.out.println("No overdue books.");
            }
        } else if (user instanceof Librarian) {
            Librarian lib = (Librarian) user;
            System.out.println("Overdue Books in Library:");
            for (Book book : lib.getLibraryBooks()) {
                if (book.getDueDate() != null && book.getDueDate().isBefore(LocalDate.now()) && !book.isAvailable()) {
                    System.out.println(book + " (Due: " + book.getDueDate() + ")");
                }
            }
        }
    }

    private static void addBook(Librarian lib) {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();
        Book book = new Book(title, author, isbn);
        lib.addBook(book);
    }

    private static void removeBook(Librarian lib) {
        System.out.println("Enter ISBN of the book to remove:");
        String isbn = scanner.nextLine();
        Book book = lib.findBookByIsbn(isbn);

        if (book != null) {
            lib.removeBook(book);
        } else {
            System.out.println("Book not found.");
        }
    }
}
