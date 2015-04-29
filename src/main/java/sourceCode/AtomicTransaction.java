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

	public Transaction getTrn() {
		return trn;
	}

}
