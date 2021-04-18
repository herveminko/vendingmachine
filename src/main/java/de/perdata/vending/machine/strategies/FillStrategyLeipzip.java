package de.perdata.vending.machine.strategies;

import de.perdata.vending.machine.entities.MachineInventoryListing;
import de.perdata.vending.machine.entities.PurchaseItem;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;


/**
 * Vending machine filling strategy in Leipzig. Available items are "Coca Cola"
 * (15), "Sprite" (15) an "Water" (20). Prices: "Coca Cola" and "Sprite" --> 90
 * Cent , "Water" --> 45 Cent.
 */
public class FillStrategyLeipzip implements IPurchaseItemFillStrategy {

	public MachineInventoryListing<PurchaseItem> fillMachinePurchaseItems() {
		PurchaseItem cocaCola = new PurchaseItem();
		cocaCola.setId(1L);
		cocaCola.setItemName("Coca Cola");
		cocaCola.setItemPrice(90L);
		
		PurchaseItem sprite = new PurchaseItem();
		sprite.setId(2L);
		sprite.setItemName("Sprite");
		sprite.setItemPrice(90L);
		
		
		PurchaseItem water = new PurchaseItem();
		water.setId(3L);
		water.setItemName("Water");
		water.setItemPrice(45L);
		
		MachineInventoryListing<PurchaseItem> itemsInventory = new MachineInventoryListing<PurchaseItem>();
		itemsInventory.put(sprite, 15);
		itemsInventory.put(cocaCola, 15);
		itemsInventory.put(water, 20);
		
		return itemsInventory;
	}

}
