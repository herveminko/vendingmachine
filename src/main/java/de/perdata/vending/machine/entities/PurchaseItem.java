package de.perdata.vending.machine.entities;

/**
 * An Item to sell in the vending machine.
 * @author minko
 *
 */
public class PurchaseItem {
	
	Long id;
	
	String itemName;
	Long itemPrice;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
