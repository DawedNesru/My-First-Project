package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			JOptionPane.showMessageDialog(null, "ID " + id + " not found", "ID " + id + " not found", JOptionPane.ERROR_MESSAGE);
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			JOptionPane.showMessageDialog(null, "Password incorrect", "Password incorrect", JOptionPane.ERROR_MESSAGE);
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	@Override
	public List<String> getAllMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> geAllBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<Book> getAllBooks() throws LibrarySystemException {
		DataAccess database = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(database.readBooksMap().keySet());
		List<Book> result = new ArrayList<Book>();
		for (String isbn : retval) {
			Book book = database.readBooksMap().get(isbn);
			Book temp = Book.getBook(book.getIsbn(), book.getTitle(), Integer.toString(book.getMaxCheckoutLength()),
					book.authorsToString(), Integer.toString(book.getCopies().length));
			result.add(temp);

		}
		return result;

	}

	@Override
	public void addMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);

	}

	@Override
	public void addBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		DataAccess da = new DataAccessFacade();
		Book book = da.readBooksMap().get(isbn);
		return book;
	}

	@Override
	public List<Author> getAllAuthors() {
		DataAccess da = new DataAccessFacade();
		List<Author> authors = new ArrayList<Author>(da.readAuthorMap().values());
		System.out.println("authors" + da.readAuthorMap().values());
		return authors;
	}

	@Override
	public LibraryMember getMemberById(String memberId) {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.readMemberMap().get(memberId);
		return member;
	}

	@Override
	public Author getAuthorById(String authorId) {
		DataAccess da = new DataAccessFacade();
		Author author = da.readAuthorMap().get(authorId);
		return author;
	}

	@Override
	public void addCopyofBook(Book book, int num) {
		DataAccess da = new DataAccessFacade();
		for (int i = 0; i < num; i++) {
			book.addCopy();
		}
		da.updateBook(book);
	}

	@Override
	public List<LibraryMember> getAllMembers() throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readMemberMap().keySet());
		List<LibraryMember> result = new ArrayList<LibraryMember>();
		for (String id : retval) {
			result.add(da.readMemberMap().get(id));
		}
		return result;
	}

	@Override
	public List<CheckOutRecord> getBookStatus(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readBooksMap().keySet());
		if (!retval.contains(isbn))
			throw new LibrarySystemException("Book with isbn " + isbn + " not found!!");
		retval.removeAll(da.readBooksMap().keySet());
		retval.addAll(da.readMemberMap().keySet());
		List<CheckOutRecord> record = new ArrayList<CheckOutRecord>();
		for (String id : retval) {
			LibraryMember member = da.readMemberMap().get(id);
			List<CheckOutRecord> temp = member.getRecord();
			for (CheckOutRecord rec : temp) {
				if (rec.getIsbn().equals(isbn)) {
					if (rec.getDueDate().isBefore(LocalDate.now()))
						rec.setStatus("Overdue");
					else
						rec.setStatus("Not overdue");
					record.add(rec);
				}
			}
		}
		return record;
	}

	@Override
	public HashMap<String, LibraryMember> getAllMembersHashMap() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}

	@Override
	public List<CheckOutRecord> checkoutBook(String id, String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		Book book = da.searchBook(isbn);
		BookCopy copy = book.getAvailableCopy();
		boolean isBorrow = da.checkBorrow(member, book);
		List<CheckOutRecord> record = member.getRecord();

		if (!isBorrow) {
			CheckOutEntry entry = new CheckOutEntry(LocalDate.now(),
					LocalDate.now().plusDays(book.getMaxCheckoutLength()));
			CheckOutRecord rec = new CheckOutRecord(id, isbn, entry.getCheckoutDate(), entry.dueDate, 0, 0, "");
			
			record.add(rec);
		} else
			throw new LibrarySystemException("This member" + id + " has already borrowed this book!!");
		if (copy != null) {
			member.setRecord(record);
			da.updateMember(member);

			copy.setAvailable(false);
				book.removeOneCopy(copy);

			da.updateBook(book);
		}
		return record;
	}

	@Override
	public List<CheckOutRecord> getMemberRecord(String id) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		return member.getRecord();
	}

}
