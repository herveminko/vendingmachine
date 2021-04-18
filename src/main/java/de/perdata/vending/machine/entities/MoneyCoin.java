package de.perdata.vending.machine.entities;

/**
 * Enumerator containing the list of all accepted machine coins.
 * The values are expressed in cent.
 * 
 * @author minko
 *
 */
public enum MoneyCoin {
	TEN_CENT(10, "€ Cent"), TWENTY_CENT(20, "€ Cent"), FIFTY_CENT(50, "€ Cent"), ONE_EURO(100, "€ Cent"), TWO_EURO(200, "€ Cent");

	private Integer amountInCent;
	private String currency;

	private MoneyCoin(Integer amountInCent, String currency) {
		this.amountInCent = amountInCent;
		this.currency = currency;
	}

	public int getAmountInCent() {
		return amountInCent;
	}

	public String getCurrency() {
		return currency;
	}
	
	
}