package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.CheckOutRecord;
import business.LibraryMember;
import business.LibrarySystemException;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "//src//dataaccess//storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	public void updateMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		mems.put(member.getMemberId(), member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	public void updateBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		books.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, books);
	}

	@Override
	public HashMap<String, Author> readAuthorMap() {
		DataAccess database = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(database.readBooksMap().keySet());
		HashMap<String, Author> tempAuthor = new HashMap<String, Author>();
		for (String isbn : retval) {
			Book book = database.readBooksMap().get(isbn);

			tempAuthor.putAll(book.SetAuthorToMap());
		}

		return tempAuthor;
	}

	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> nbook = readBooksMap();
		String isbn = book.getIsbn();
		nbook.put(isbn, book);
		saveToStorage(StorageType.BOOKS, nbook);

	}

	@Override
	public void updateAuthor(Author author) {
		HashMap<String, Author> authors = readAuthorMap();
		authors.put(author.getTelephone(), author);
		saveToStorage(StorageType.BOOKS, authors);

	}

	public boolean addToMember(LibraryMember newMember) {

		try {
			@SuppressWarnings("unchecked")
			HashMap<String, LibraryMember> existingMembers = (HashMap<String, LibraryMember>) readFromStorage(
					StorageType.MEMBERS);
			existingMembers.put(newMember.getMemberId(), newMember);
			saveToStorage(StorageType.MEMBERS, existingMembers);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateMember(String memid, LibraryMember member) {

		HashMap<String, LibraryMember> mem = readMemberMap();
		mem.put(memid, member);
		saveToStorage(StorageType.MEMBERS, mem);
	}

	@Override
	public void updateBook(String isbn, Book book) {
		HashMap<String, Book> calb = readBooksMap();
		calb.put(isbn, book);
		saveToStorage(StorageType.BOOKS, calb);

	}

	@Override
	public Book searchBook(String isbn) throws LibrarySystemException {
		List<String> retval = new ArrayList<>();
		retval.addAll(this.readBooksMap().keySet());
		if (!retval.contains(isbn))
			throw new LibrarySystemException("The book with isbn " + isbn + " is not available");
		return this.readBooksMap().get(isbn);
	}

	@Override
	public LibraryMember searchMember(String memberId) throws LibrarySystemException {
		List<String> retval = new ArrayList<>();
		retval.addAll(this.readMemberMap().keySet());
		if (!retval.contains(memberId))
			throw new LibrarySystemException("Member ID is not found");
		return this.readMemberMap().get(memberId);
	}

	@Override
	public boolean checkBorrow(LibraryMember member, Book book) throws LibrarySystemException {
		boolean check = false;
		List<CheckOutRecord> record = member.getRecord();
		for (CheckOutRecord entry : record) {
			if (entry.getIsbn().equals(book.getIsbn()))
				check = true;
		}
		return check;
	}

}
