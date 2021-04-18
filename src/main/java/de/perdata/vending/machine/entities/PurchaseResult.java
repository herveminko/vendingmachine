package de.perdata.vending.machine.entities;

import java.util.List;

/**
 * Immutable structure holding the result of a vending machine transaction. It contains the
 * purchased item and may be the money change if necessary.
 * 
 * @author minko
 *
 */
public class PurchaseResult {
	
	PurchaseItem purchasedItem;
	List<MoneyCoin> moneyChange;
	
	public PurchaseResult(PurchaseItem purchasedItem, List<MoneyCoin> moneyChange) {
		this.purchasedItem = purchasedItem;
		this.moneyChange = moneyChange;
	}
	
	public PurchaseItem getPurchasedItem() {
		return purchasedItem;
	}
	public List<MoneyCoin> getMoneyChange() {
		return moneyChange;
	}

}
