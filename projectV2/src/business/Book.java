package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


final public class Book implements Serializable {

	private static final long serialVersionUID = 6110690276685962829L;
	private BookCopy[] copies;
	private List<Author> authors;
	private String author;
	private String copy;
	private String isbn;
	private String title;
	private int maxCheckoutLength;

	public Book() {};
	
	// constructor
	public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
		this.isbn = isbn;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
		this.authors = Collections.unmodifiableList(authors);
		copies = new BookCopy[] { new BookCopy(this, 1, true) };
	}

	// using factory constructor
	public static Book getBook(String isbn, String title, String maxDay, String author, String copy) {
		Book b = new Book();
		b.setIsbn(isbn);
		b.setTitle(title);
		b.setMaxCheckoutLength(Integer.parseInt(maxDay));
		b.setAuthor(author);
		b.setCopy(copy);

		return b;
	}

	
	// all setter
	public void setCopy(String copy) {
		this.copy = copy;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCopies(BookCopy[] copies) {
		this.copies = copies;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMaxCheckoutLength(int maxCheckoutLength) {
		this.maxCheckoutLength = maxCheckoutLength;
	}

	public int getNumCopies() {
		return copies.length;
	}
	
	// all getter

	public String getTitle() {
		return title;
	}

	public BookCopy[] getCopies() {
		return copies;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAuthor() {
		return author;
	}

	public String getCopy() {
		return copy;
	}

	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}
	
	// main function

	public void updateCopies(BookCopy copy) { // call by system controller
		for (int i = 0; i < copies.length; ++i) {
			BookCopy c = copies[i];
			if (c.equals(copy)) {
				copies[i] = copy;

			}
		}
	}

	public List<Integer> getCopyNums() { // call by book controller
		List<Integer> retVal = new ArrayList<>();
		for (BookCopy c : copies) {
			retVal.add(c.getCopyNum());
		}
		return retVal;

	}

	public void addCopy() { // call by system controller
		BookCopy[] newArr = new BookCopy[copies.length + 1];
		System.arraycopy(copies, 0, newArr, 0, copies.length);
		newArr[copies.length] = new BookCopy(this, copies.length + 1, true);
		copies = newArr;
	}
	
	public void removeOneCopy(BookCopy copy) { // call by system controller
		BookCopy[] newArr = new BookCopy[copies.length - 1];
	     for(int i = 0, j = 0; i < copies.length; i++) {
	    	 if(copies[i].equals(copy)) {
	    		 newArr[j] = copies[i];
	    		 j++;
	    	 }
	     }
//		System.arraycopy(copies, 0, newArr, 0, copies.length);
//		newArr[copies.length] = new BookCopy(this, copies.length + 1, true);
		copies = newArr;
		System.out.println("The book suppose to be removed!");
	}

	public BookCopy getAvailableCopy() { // call by system controller
		 for (BookCopy bookCopy : copies) {
	            if (bookCopy.isAvailable()) {
	                return bookCopy;
	            }
	        }
	        return null;
		
		
	}

	public String authorsToString() {
		String result = "";
		for (Author author : authors) {
			result += author.getFirstName() + " " + author.getLastName() + ", ";
		}
		return result.substring(0, result.length() - 3);
	}

	public HashMap<String, Author> SetAuthorToMap() {

		HashMap<String, Author> temp = new HashMap<String, Author>();
		for (Author a : authors) {
			Author tempA = new Author(a.getFirstName(), a.getLastName(), a.getTelephone(), a.getAddress(), a.getBio());
			temp.put(a.getTelephone(), tempA);
		}
		return temp;
	}

	@Override
	public String toString() {
		return "Book [author=" + author + ", copy=" + copy + ", isbn=" + isbn + ", title=" + title
				+ ", maxCheckoutLength=" + maxCheckoutLength + "]";
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null)
			return false;
		if (ob.getClass() != getClass())
			return false;
		Book b = (Book) ob;
		return b.isbn.equals(isbn);
	}

}
