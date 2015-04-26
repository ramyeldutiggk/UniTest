package sourceCode;

public class AtomicTransaction {
	private String name;
	private Transaction trn;
	
	public AtomicTransaction(String name, Transaction trn) {
		this.name = name;
		this.trn = trn;
	}

	public String getName() {
		return name;
	}

	/*public void setName(String name) {
		this.name = name;
	}
*/
	public Transaction getTrn() {
		return trn;
	}

	/*public void setTrn(Transaction trn) {
		this.trn = trn;
	}*/	
}
