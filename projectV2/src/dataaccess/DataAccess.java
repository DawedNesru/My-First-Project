package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

public interface DataAccess {
	
    //user
	public HashMap<String, User> readUserMap();

    //author	
	public HashMap<String, Author> readAuthorMap();
	public void updateAuthor(Author author);

	//Library Member
	public HashMap<String, LibraryMember> readMemberMap();
	public LibraryMember searchMember(String id) throws LibrarySystemException;
	public void saveNewMember(LibraryMember member);
	public boolean addToMember(LibraryMember newMember);
	public void updateMember(String memid, LibraryMember member);
	public boolean checkBorrow(LibraryMember member, Book book) throws LibrarySystemException;
	public void updateMember(LibraryMember member);

	//Book
	public HashMap<String, Book> readBooksMap();
	public void saveNewBook(Book newBook);
	public void updateBook(Book updateBook);
	public void updateBook(String isbn, Book book);
	public Book searchBook(String isbn) throws LibrarySystemException;

}
