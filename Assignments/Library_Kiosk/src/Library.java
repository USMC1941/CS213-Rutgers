public class Library {

    /**
     * Array-based implementation of the bag data structure
     */
    private Book[] books;

    /**
     * The number of books currently in the bag
     */
    private int numBooks;

    /**
     * Default constructor to create an empty bag
     */
    public Library() {}

    /**
     * Helper method to find a book in the bag
     */
    private int find(Book book) {}

    /**
     * Helper method to grow the capacity by 4
     */
    private void grow() {}

    public void add(Book book) {}

    public boolean remove(Book book) {}

    public boolean checkOut(Book book) {}

    public boolean returns(Book book) {}

    /**
     * Print the list of books in the bag
     */
    public void print() {}

    /**
     * Print the list of books by datePublished (ascending)
     */
    public void printByDate() {}

    /**
     * Print the list of books by number (ascending)
     */
    public void printByNumber() {}
}
