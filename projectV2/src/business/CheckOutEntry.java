package business;
import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutEntry implements Serializable {
	private static final long serialVersionUID = 4098665274551336512L;
	LocalDate checkoutDate;
	LocalDate dueDate;

	CheckOutEntry(LocalDate checkoutDate, LocalDate dueDate) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
}
