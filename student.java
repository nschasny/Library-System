import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Book> borrowedBooks;

    public Student(String name, String userId) {
        super(name, userId);
        borrowedBooks = new ArrayList<>();
    }

    @Override
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
            book.setDueDate(LocalDate.now().plusDays(14)); // Due in 2 weeks
            System.out.println(getName() + " borrowed " + book);
        } else {
            System.out.println(book + " is not available.");
        }
    }

    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.setAvailable(true);
            book.setDueDate(null);
            System.out.println(getName() + " returned " + book);
        } else {
            System.out.println(getName() + " did not borrow " + book);
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean hasOverdueBooks() {
        LocalDate today = LocalDate.now();
        for (Book book : borrowedBooks) {
            if (book.getDueDate() != null && book.getDueDate().isBefore(today)) {
                return true;
            }
        }
        return false;
    }
}
