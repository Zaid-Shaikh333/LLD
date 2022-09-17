
import java.util.*;

class Book {
    String title;
    Date publishedDate;
    int uniqueId;
    List<Author> authors;

    public Book(String title, int uniqueId, Date publshedDate)
    {
        this.title = title;
        this.uniqueId = uniqueId;
        this.publishedDate = publshedDate;
    }

    public String getBookTitle()
    {
        return this.title;
    }

    public int getBookId()
    {
        return this.uniqueId;
    }

    public Date getPublishedDate()
    {
        return this.publishedDate;
    }

}

class BookItem{
    Book bookItem;
    int bookItemId;
    BookStatus status;

    public BookItem(Book bookItem, int bookItemId)
    {
        this.bookItem = bookItem;
        this.bookItemId = bookItemId;
        this.status = BookStatus.AVAILABLE;
    }

    public int getBookItemId()
    {
        return this.bookItemId;
    }

    public Book getBookItem()
    {
        return this.bookItem;
    }
}

class Address{
    String street;
    String city;
    String state;
    String country;
    String zipcode;
    String landmark;
}

class Person{
    String firstname;
    String lastname;
    String emailId;
}

class Librarian extends Person{

    public boolean createMember(String firstname, String lastname)
    {
        int uniqueId = (int)Math.random();
        LibraryMember member = new LibraryMember(firstname, lastname, uniqueId, MemberStatus.ACTIVE);
        Library.addMember(uniqueId, member);
        return true;
    }

    public boolean blockMember(LibraryMember member)
    {
        LibraryMember toBeBlocked = Library.getMember(member.memberId);
        if(toBeBlocked == null)
            return false;
        toBeBlocked.status = MemberStatus.BLOCKED;
        return true;
    }

    public boolean unblockMember(LibraryMember member)
    {
        LibraryMember toBeUnblocked = Library.getMember(member.memberId);
        if(toBeUnblocked == null)
            return false;
        toBeUnblocked.status = MemberStatus.ACTIVE;
        return true;
    }

    public boolean addBook(String title, Date publishedDate, Author author)
    {
        int uniqueId = (int)Math.random();
        Book book = new Book(title, uniqueId, publishedDate);
        BookItem newBook = new BookItem(book, uniqueId);

        Library.books.put(uniqueId, newBook);
        return true;
    }
}

class LibraryMember extends Person{
    int memberId;
    MemberStatus status;
    int totalBooksCheckedOut;

    public LibraryMember(String firstname, String lastname, int memberId, MemberStatus status)
    {
        this.status = status;
        this.memberId = memberId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName()
    {
        return this.firstname + this.lastname;
    }

    public int getMemberShipId()
    {
        return this.memberId;
    }

    public MemberStatus getMemberShipStatus()
    {
        return this.status;
    }

    public int getTotalBooksCheckedOut()
    {
        return this.totalBooksCheckedOut;
    }

}

class Author extends Person{
    List<Book> publishedBooks;

    public List<Book> getPublishedBooks()
    {
        return this.publishedBooks;
    }
}

enum MemberStatus{
    ACTIVE, BLOCKED, DISABLED;
}

enum BookStatus{
    LENDED, AVAILABLE, LOST;  
}

class Search{
    HashMap<String, List<BookItem>> queryByBookTitle;
    HashMap<Author, List<BookItem>> queryByAuthor;
    
    public List<BookItem> getByBookTitle(String title)
    {
        if(!queryByBookTitle.containsKey(title))
            return null;
        
        return queryByBookTitle.get(title);
    }

    public List<BookItem> getByAuthor(Author author)
    {
        if(!queryByAuthor.containsKey(author))
            return null;
        
        return queryByAuthor.get(author);
    }

}

class Library{
    static HashMap<Integer, LibraryMember> users;
    static HashMap<Integer, BookItem> books;
    static int maxBooks = 5;

    public static boolean addMember(int memberId, LibraryMember member)
    {
        if(users.containsKey(memberId))
            return false;
        
        users.put(memberId, member);
        return true;
    }

    public static LibraryMember getMember(int memberId)
    {
        if(!users.containsKey(memberId))
            return null;
        
        return users.get(memberId);
    }

    public static boolean addBook(int bookId, BookItem book)
    {
        if(books.containsKey(bookId))
            return false;
        
        books.put(bookId, book);
        return true;
    }

    public static BookItem getBook(int bookId)
    {
        if(!books.containsKey(bookId))
            return null;
        
        return books.get(bookId);
    }
}

class BookIssueServive{

    public boolean LendBook(BookItem bookItem, LibraryMember member)
    {
        BookItem toLend = Library.getBook(bookItem.bookItemId);

        if(toLend.status == BookStatus.LENDED)
            return false;
        if(member.totalBooksCheckedOut >= Library.maxBooks)
            return false;
        
        member.totalBooksCheckedOut++;
        toLend.status = BookStatus.LENDED;
        return true;
    }

    public boolean returnBook(BookItem bookItem, LibraryMember member)
    {
        BookItem toReturn = Library.getBook(bookItem.bookItemId);
        if(toReturn == null)
            return false;
        
        Book bookDetails = bookItem.getBookItem();
        Date current = new Date();
        Date purchased = bookDetails.getPublishedDate();

        if(current.compareTo(purchased) > 10)
        {
            long difference = current.getTime() - purchased.getTime();
            long days = difference/(24*60*60*1000);
            Fine.CollectFine(member, days);
        }
        
        member.totalBooksCheckedOut--;
        toReturn.status = BookStatus.AVAILABLE;
        return true;
    }
}

class Fine{

    public static void CollectFine(LibraryMember member, long days)
    {

    }
}