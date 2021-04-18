package de.perdata.vending.machine.interfaces;

import java.util.List;

import de.perdata.vending.machine.entities.MoneyCoin;
import de.perdata.vending.machine.entities.PurchaseItem;
import de.perdata.vending.machine.entities.PurchaseResult;

/**
 * Interface representing a vending machine and all its methods.
 * 
 * @author minko
 *
 */
public interface IVendingMachine {

	/**
	 * Buy a given machine's item by selecting a button and entering some money
	 * coins.
	 * 
	 * @param selection is the selected item position.
	 * @param payment   is the set of coins used for payment
	 * @return a {@link PurchaseResult} containing a {@link PurchaseItem} and the
	 *         money change if available.
	 */
	public PurchaseResult getItemAndMoneyChange(Integer selection, List<MoneyCoin> payment);

	/**
	 * Reset the machine completely by emptying all inventories (coins and items).
	 */
	public void emptyMachine();

	/**
	 * Initialize the machine by filling money coins for change as well as items to
	 * sell and placing them at a specific position.
	 */
	public void fillMachine();

	/**
	 * Set a specific filling strategy for the machine.
	 */
	public void setPurchaseItemFillStrategy(IPurchaseItemFillStrategy purchaseItemFillStrategy);
	
	public PurchaseItem getItemAtPosition(Integer itemPosition);
	
	public Integer getRemainingItemsAmount(PurchaseItem selectedItem);
	
}
