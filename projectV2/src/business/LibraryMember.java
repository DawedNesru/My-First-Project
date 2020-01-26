package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckOutEntry> checkout = new ArrayList<>();

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
	}

	private List<CheckOutRecord> record = new ArrayList<CheckOutRecord>();

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add,
			List<CheckOutRecord> record) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
		this.record = record;
	}

	public String getMemberId() {
		return memberId;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;

	public List<CheckOutRecord> getRecord() {
		if (record == null) {
			record = new ArrayList<CheckOutRecord>();
		}
		return record;
	}

	public List<CheckOutEntry> getCheckout() {
		return checkout;
	}

//	
	public void setRecord(List<CheckOutRecord> record) {
		this.record = record;
	}

}
