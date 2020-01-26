package ui;

import business.Address;
import business.LibraryMember;
import business.LibrarySystemException;

public class Validator {

	public static void addressValidation(Address address) throws LibrarySystemException {
		if (address.getStreet().trim().equals("") || address.getCity().trim().equals("")
				|| address.getState().trim().equals("") || address.getZip().trim().equals(""))
			throw new LibrarySystemException("\"All fields should be filled!");

	}

	public static void bookCopyValidation(String isbn, String copyNum) throws LibrarySystemException {
		if (isbn.trim().equals("") || copyNum.trim().equals(""))
			throw new LibrarySystemException("All fields should be filled!");
		isNumberic(copyNum, "Total of copy should be numberic!");
	}

	public static void emptyValidation(String s, String msg) throws LibrarySystemException {
		if (s.trim().equals("")) {
			throw new LibrarySystemException(s);
		}
	}

	public static void memberValidation(LibraryMember member) throws LibrarySystemException {
		if (member.getMemberId().trim().equals("") || member.getFirstName().trim().equals("")
				|| member.getLastName().trim().equals("") || member.getTelephone().trim().equals(""))
			throw new LibrarySystemException("\"All fields should be filled!");
		zipRule(member.getAddress().getZip());
		isNumberic(member.getTelephone(), "Phone number should be numeric!");

	}

	public static void bookValidation(String isbn, String title, String maxCheckoutLength, String copyNum)
	
			throws LibrarySystemException {
	
		try {
			Long.parseLong(isbn);

		} catch (NumberFormatException e) {
			throw new LibrarySystemException("ISBN must be numeric");
		}
		if (!(isbn.length() == 10 || isbn.length() == 14))
			throw new LibrarySystemException("ISBN should be 10 or 13 digits long.");
		if (isbn.length() == 13)
			if (!(((isbn.charAt(0) == 9) && (isbn.charAt(1) == 7) && ((isbn.charAt(2) == 8) || (isbn.charAt(2) == 9)))))
				throw new LibrarySystemException("The first three digits should be 978 or 979");
if (isbn.length() == 10)
			if( !((isbn.charAt(0) == '0') || (isbn.charAt(0) == '1')))
				throw new LibrarySystemException("The first digit should be 0 or 1");
if (isbn.length() == 14)
	if( !((isbn.charAt(3) == '-') ))
		throw new LibrarySystemException("Please enter the right ISBN as:  978-1234567890");

		
		if (isbn.trim().equals("") || title.trim().equals("") || maxCheckoutLength.trim().equals("")
				|| copyNum.trim().equals(""))
			throw new LibrarySystemException("All fields should be filled!");
		isNumberic(maxCheckoutLength, "Max checkout length should be numberic");
		if (!(maxCheckoutLength.equals("21") || maxCheckoutLength.equals("7") || maxCheckoutLength.equals("-7"))) {
			throw new LibrarySystemException("Max checkout length should be 21 or 7!");
		}
		isNumberic(copyNum, "Number of copies must be numberic!");
	}

	public static void zipRule(String s) throws LibrarySystemException {
		isNumberic(s, "Zip code should be numeric!");
		if (s.length() != 5) {
			throw new LibrarySystemException("Zip code length should be 5!");
		}
	}

	public static void isNumberic(String s, String msg) throws LibrarySystemException {
		try {
			Integer.parseInt(s);
		} catch (IllegalArgumentException e) {
			throw new LibrarySystemException(msg);
		}
	}

}
