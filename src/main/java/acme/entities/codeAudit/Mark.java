
package acme.entities.codeAudit;

public enum Mark {

	A_PLUS("A+"), A("A"), B("B"), C("C"), F("F"), F_MINUS("F-");


	private String str;


	Mark(final String str) {
		this.str = str;
	}
	@Override
	public String toString() {
		return this.str;
	}
}
