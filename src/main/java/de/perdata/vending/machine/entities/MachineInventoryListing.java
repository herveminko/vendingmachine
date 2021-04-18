package de.perdata.vending.machine.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Map to define an inventory listing to hold all available vending machine cash
 * and items.
 * 
 * @author minko
 */
public class MachineInventoryListing<T> {

	// Map containing the number of available items of type T in the vending machine
	private Map<T, Integer> inventoryItemCount = new HashMap<T, Integer>();

	/**
	 * Returns the number of available items of the given type.
	 * 
	 * @param machineItem is the item whose amount must be verified.
	 * @return
	 */
	public Integer getAvailableAmount(T machineItem) {
		Integer value = inventoryItemCount.get(machineItem);
		return value == null ? 0 : value;
	}

	/**
	 * Add a new item to the inventory.
	 */
	public void addSingleItem(T item) {
		int count = inventoryItemCount.get(item);
		inventoryItemCount.put(item, count + 1);
	}

	/**
	 * Remove an item from the inventory.
	 */
	public void removeSingleItem(T item) {
		if (hasItem(item)) {
			int count = inventoryItemCount.get(item);
			inventoryItemCount.put(item, count - 1);
		}
	}

	public void clear() {
		inventoryItemCount.clear();
	}

	public void put(T item, Integer quantity) {
		inventoryItemCount.put(item, quantity);
	}

	public boolean hasItem(T item) {
		return getAvailableAmount(item) > 0;
	}

	public Map<T, Integer> getInventoryItemCount() {
		return inventoryItemCount;
	}

}