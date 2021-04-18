package de.perdata.vending.machine.strategies;

import de.perdata.vending.machine.entities.MachineInventoryListing;
import de.perdata.vending.machine.entities.PurchaseItem;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;

/**
 * Default vending machine filling strategy. Available items are "Coca Cola"
 * (1), "Sprite" (1) an "Water" (2). Prices: "Coca Cola" and "Sprite" --> 100
 * Cent , "Water" --> 65 Cent.
 */
public class FillStrategyDefault implements IPurchaseItemFillStrategy {

	public MachineInventoryListing<PurchaseItem> fillMachinePurchaseItems() {
		PurchaseItem cocaCola = new PurchaseItem();
		cocaCola.setId(1L);
		cocaCola.setItemName("Coca Cola");
		cocaCola.setItemPrice(100L);

		PurchaseItem sprite = new PurchaseItem();
		sprite.setId(2L);
		sprite.setItemName("Sprite");
		sprite.setItemPrice(100L);

		PurchaseItem water = new PurchaseItem();
		water.setId(3L);
		water.setItemName("Water");
		water.setItemPrice(65L);

		MachineInventoryListing<PurchaseItem> itemsInventory = new MachineInventoryListing<PurchaseItem>();
		itemsInventory.put(sprite, 1);
		itemsInventory.put(cocaCola, 1);
		itemsInventory.put(water, 2);

		return itemsInventory;
	}

}
