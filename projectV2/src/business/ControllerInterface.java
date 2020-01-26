package business;

import java.util.HashMap;
import java.util.List;

import business.Book;

public interface ControllerInterface {
	
	// login
	public void login(String id, String password) throws LoginException;

	// author
	public List<Author> getAllAuthors();
	public Author getAuthorById(String authorId);
	
	// member
	public List<String> getAllMemberIds();
	public LibraryMember getMemberById(String memberId);
	public List<LibraryMember> getAllMembers() throws LibrarySystemException;
	public void addMember(LibraryMember member);
	HashMap<String, LibraryMember> getAllMembersHashMap();
	
	
    // books
	public List<String> geAllBookIds();
	List<Book> getAllBooks() throws LibrarySystemException;
	public Book getBookByIsbn(String isbn);
	public void addBook(Book book);
	public void addCopyofBook(Book book, int num);
	
	
	List<CheckOutRecord> getBookStatus(String isbn) throws LibrarySystemException;
	public List<CheckOutRecord> getMemberRecord(String id)throws LibrarySystemException;
	public List<CheckOutRecord> checkoutBook(String id, String isbn) throws LibrarySystemException;


	
}
