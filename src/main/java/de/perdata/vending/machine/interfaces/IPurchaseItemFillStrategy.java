package de.perdata.vending.machine.interfaces;

import de.perdata.vending.machine.entities.MachineInventoryListing;
import de.perdata.vending.machine.entities.PurchaseItem;

/**
 * Define a strategy for properly filling the vending machine.
 * @author minko
 *
 */
public interface IPurchaseItemFillStrategy {
	
	/**
	 * Fill the vending machine with appropriate items to be purchased.
	 */
	public MachineInventoryListing<PurchaseItem> fillMachinePurchaseItems();
}
