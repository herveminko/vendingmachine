package de.perdata.vending.machine.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import de.perdata.vending.machine.exceptions.NotEnoughChangeException;
import de.perdata.vending.machine.exceptions.NotFullyPaidException;
import de.perdata.vending.machine.exceptions.SoldOutException;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;
import de.perdata.vending.machine.interfaces.IVendingMachine;
import de.perdata.vending.machine.util.MoneyUtil;

/**
 * Concrete vending machine implementation.
 * 
 * @author minko
 *
 */
public class VendingMachineImpl implements IVendingMachine {

	// Store for all items to be purchased
	private MachineInventoryListing<MoneyCoin> moneyInventory = new MachineInventoryListing<>();
	// Store containing all vending machine money
	private MachineInventoryListing<PurchaseItem> itemsInventory = new MachineInventoryListing<>();
	// Mapping the selection to defined items
	private Map<Integer, PurchaseItem> itemSelection = new HashMap<>();
	// Strategy used to fill the machine, variying on his location
	private IPurchaseItemFillStrategy purchaseItemFillStrategy;
	//
	private Long totalSales = 0L;

	/**
	 * Empty all machine inventories and reinitialize all values.
	 */
	public void emptyMachine() {
		moneyInventory.clear();
		itemsInventory.clear();
		itemSelection.clear();
		totalSales = 0L;
	}

	public void fillMachine() {
		itemsInventory = purchaseItemFillStrategy.fillMachinePurchaseItems();
		fillMachineCoins();
		setVendorMachineSelectionSlots();
	}

	public PurchaseResult getItemAndMoneyChange(Integer selection, List<MoneyCoin> payment) {

		PurchaseItem selectedItem = itemSelection.get(selection);
		
		// Try to get the desired Item from the machine.
		PurchaseItem item = tryToGetItem(selectedItem, payment);

		long changeAmount = MoneyUtil.getTotalMoneyCount(payment) - selectedItem.getItemPrice();
		List<MoneyCoin> moneyChange = getMoneyChangeForAmount(changeAmount);

		// UPdate the available cash pool
		updateMachineMoneyInventoryAfterReceivingMoney(payment);
		
		// Deduct the change money from the available machine cash pool
		//updateMachineMoneyInventoryAfterReturningChange(moneyChange);

		totalSales = totalSales + selectedItem.getItemPrice();
		

		return new PurchaseResult(item, moneyChange);
	}

	public void setPurchaseItemFillStrategy(IPurchaseItemFillStrategy purchaseItemFillStrategy) {
		this.purchaseItemFillStrategy = purchaseItemFillStrategy;
	}

	public PurchaseItem getItemAtPosition(Integer itemPosition) {
		return getItemSelection().get(itemPosition);
	}

	public Integer getRemainingItemsAmount(PurchaseItem selectedItem) {
		return getItemsInventory().getInventoryItemCount().get(selectedItem);
	}
	
	
	// Getters...
	
	public MachineInventoryListing<MoneyCoin> getMoneyInventory() {
		return moneyInventory;
	}

	public MachineInventoryListing<PurchaseItem> getItemsInventory() {
		return itemsInventory;
	}

	public Long getTotalSales() {
		return totalSales;
	}

	public Map<Integer, PurchaseItem> getItemSelection() {
		return itemSelection;
	}
		
	// Privates...

	private PurchaseItem tryToGetItem(PurchaseItem purchasedItem, List<MoneyCoin> payment)
			throws NotEnoughChangeException, NotFullyPaidException {
		Integer currentBalance = MoneyUtil.getTotalMoneyCount(payment);

		if (!itemsInventory.hasItem(purchasedItem)) {
			throw new SoldOutException("Sold Out, Please buy another item");
		}

		if (isItemPaymentSufficient(payment, purchasedItem)) {
			if (hasSufficientChange(payment, purchasedItem)) {
				itemsInventory.removeSingleItem(purchasedItem);
				return purchasedItem;
			}
			throw new NotEnoughChangeException("There is currently not Sufficient change in Inventory");
		}
		long remainingBalance = purchasedItem.getItemPrice() - currentBalance;
		throw new NotFullyPaidException("Price not full paid, remaining : ", remainingBalance);
	}

	private boolean isItemPaymentSufficient(List<MoneyCoin> payment, PurchaseItem purchasedItem) {
		Integer currentBalance = MoneyUtil.getTotalMoneyCount(payment);
		return currentBalance >= purchasedItem.getItemPrice();
	}

	private boolean hasSufficientChange(List<MoneyCoin> payment, PurchaseItem purchasedItem) {
		Integer currentBalance = MoneyUtil.getTotalMoneyCount(payment);
		return hasSufficientChangeForAmount(currentBalance - purchasedItem.getItemPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getMoneyChangeForAmount(amount);
		} catch (NotEnoughChangeException nece) {
			return hasChange = false;
		}

		return hasChange;
	}

	/*
	 * Get the money change after an item is successfully purchased by somebody.
	 */
	private List<MoneyCoin> getMoneyChangeForAmount(Long amount) throws NotEnoughChangeException {
		List<MoneyCoin> moneyChange = Collections.emptyList();

		if (amount > 0) {
			moneyChange = new ArrayList<MoneyCoin>();
			Long balance = amount;
			while (balance > 0) {
				if (balance >= MoneyCoin.TWO_EURO.getAmountInCent() && moneyInventory.hasItem(MoneyCoin.TWO_EURO)) {
					moneyChange.add(MoneyCoin.TWO_EURO);
					balance = balance - MoneyCoin.TWO_EURO.getAmountInCent();
					moneyInventory.removeSingleItem(MoneyCoin.TWO_EURO);
				} else if (balance >= MoneyCoin.ONE_EURO.getAmountInCent()
						&& moneyInventory.hasItem(MoneyCoin.ONE_EURO)) {
					moneyChange.add(MoneyCoin.ONE_EURO);
					balance = balance - MoneyCoin.ONE_EURO.getAmountInCent();
					moneyInventory.removeSingleItem(MoneyCoin.ONE_EURO);
				} else if (balance >= MoneyCoin.FIFTY_CENT.getAmountInCent()
						&& moneyInventory.hasItem(MoneyCoin.FIFTY_CENT)) {
					moneyChange.add(MoneyCoin.FIFTY_CENT);
					balance = balance - MoneyCoin.FIFTY_CENT.getAmountInCent();
					moneyInventory.removeSingleItem(MoneyCoin.FIFTY_CENT);
				} else if (balance >= MoneyCoin.TWENTY_CENT.getAmountInCent()
						&& moneyInventory.hasItem(MoneyCoin.TWENTY_CENT)) {
					moneyChange.add(MoneyCoin.TWENTY_CENT);
					balance = balance - MoneyCoin.TWENTY_CENT.getAmountInCent();
					moneyInventory.removeSingleItem(MoneyCoin.TWENTY_CENT);
				} else if (balance >= MoneyCoin.TEN_CENT.getAmountInCent()
						&& moneyInventory.hasItem(MoneyCoin.TEN_CENT)) {
					moneyChange.add(MoneyCoin.TEN_CENT);
					balance = balance - MoneyCoin.TEN_CENT.getAmountInCent();
					moneyInventory.removeSingleItem(MoneyCoin.TEN_CENT);
				} else {
					throw new NotEnoughChangeException("Not enough money change, Please try another product");
				}
			}
		}

		return moneyChange;
	}

	/*
	 * Update the amount of available money for change, after collecting money from a purchase.
	 */
	private void updateMachineMoneyInventoryAfterReceivingMoney(List<MoneyCoin> moneyChange) {
		for (MoneyCoin coin : moneyChange) {
			moneyInventory.addSingleItem(coin);
		}
	}
	
	/*
	 * Update the amount of available money for change after returning money change.
	 */
	private void updateMachineMoneyInventoryAfterReturningChange(List<MoneyCoin> moneyChange) {
		for (MoneyCoin coin : moneyChange) {
			moneyInventory.removeSingleItem(coin);
		}
	}

	/*
	 * Fill the machine with coins to use for money change.
	 * 
	 * @TODO: Configure the way of filling money and implement it as strategy to be
	 * more flexible, if new money types are allowed in the future.
	 */
	private void fillMachineCoins() {
		// initialize machine money inventory with 5 coins of each type
		for (MoneyCoin c : MoneyCoin.values()) {
			moneyInventory.put(c, 5);
		}
	}

	/*
	 * Set each machine item at a specific position to react when a customer select
	 * a specific button.
	 * 
	 * @TODO: read the selection from the configuration and implement the whole as
	 * strategy as well.
	 */
	private void setVendorMachineSelectionSlots() {
		int slotPosition = 0;
		Comparator<PurchaseItem> purchaseItemComparator = (PurchaseItem p1, PurchaseItem p2) -> {
	        return Long.compare(p1.getId(), p2.getId());
	    };
	    
	    List<PurchaseItem> sortedPurchaseList = new ArrayList<PurchaseItem>(itemsInventory.getInventoryItemCount().keySet());
	    Collections.sort(sortedPurchaseList, purchaseItemComparator);

		for (PurchaseItem item : sortedPurchaseList) {
			itemSelection.put(slotPosition, item);
			slotPosition++;
		}
	}

}
