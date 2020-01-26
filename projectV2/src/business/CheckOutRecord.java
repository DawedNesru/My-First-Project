package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutRecord implements Serializable{
	private static final long serialVersionUID = -6456884726468548214L;
	private final String memberID;
	private final String isbn;
	private CheckOutEntry entry ;
	long paidDate;
	long fineAmount;
	public String status;

	public CheckOutRecord(String memberID, String isbn, LocalDate checkoutDate, LocalDate dueDate, long paidDate, long fineAmount, String status) {
		this.memberID = memberID;
		this.isbn = isbn;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.status = status;
		this.paidDate = paidDate;
		this.fineAmount = fineAmount;
	}

	public CheckOutEntry getCheckoutEntry() {
		return entry;
	}

	public String getMemberID() {
		return memberID;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPaidDate() {
		return paidDate;
	}

	public long getFineAmount() {
		return fineAmount;
	}

	public void setPaidDate(long paidDate) {
		this.paidDate = paidDate;
	}

	public void setFineAmount(long fineAmount) {
		this.fineAmount = fineAmount;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	private final LocalDate checkoutDate;
	private final LocalDate dueDate;

}