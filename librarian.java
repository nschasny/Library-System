import java.util.ArrayList;
import java.util.List;

public class Librarian extends User {
    private List<Book> libraryBooks;

    public Librarian(String name, String userId) {
        super(name, userId);
        libraryBooks = new ArrayList<>();
    }

    public void addBook(Book book) {
        libraryBooks.add(book);
        System.out.println("Added " + book);
    }

    public void removeBook(Book book) {
        libraryBooks.remove(book);
        System.out.println("Removed " + book);
    }

    public void listBooks() {
        if (libraryBooks.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : libraryBooks) {
                System.out.println(book);
            }
        }
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : libraryBooks) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void borrowBook(Book book) {
        System.out.println("Librarians cannot borrow books.");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("Librarians cannot return books.");
    }

    public List<Book> getLibraryBooks() {
        return libraryBooks;
    }
}
