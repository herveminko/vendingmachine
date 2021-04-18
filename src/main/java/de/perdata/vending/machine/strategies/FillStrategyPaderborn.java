package de.perdata.vending.machine.strategies;

import de.perdata.vending.machine.entities.MachineInventoryListing;
import de.perdata.vending.machine.entities.PurchaseItem;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;

/**
 * Vending machine filling strategy in Paderborn. Available items are "Coca Cola"
 * (15), "Sprite" (15), "Fanta" (30) and "Water" (20). Prices: "Coca Cola", "Fanta" and "Sprite" --> 150
 * Cent , "Water" --> 110 Cent.
 * 
 * @author minko
 */
public class FillStrategyPaderborn implements IPurchaseItemFillStrategy {

	public MachineInventoryListing<PurchaseItem> fillMachinePurchaseItems() {
		PurchaseItem cocaCola = new PurchaseItem();
		cocaCola.setId(1L);
		cocaCola.setItemName("Coca Cola");
		cocaCola.setItemPrice(150L);
		
		PurchaseItem sprite = new PurchaseItem();
		sprite.setId(2L);
		sprite.setItemName("Sprite");
		sprite.setItemPrice(150L);
		
		PurchaseItem fanta = new PurchaseItem();

		sprite.setId(3L);
		fanta.setItemName("Fanta");
		fanta.setItemPrice(150L);
		
		
		PurchaseItem water = new PurchaseItem();
		sprite.setId(4L);
		water.setItemName("Water");
		water.setItemPrice(110L);
		
		MachineInventoryListing<PurchaseItem> itemsInventory = new MachineInventoryListing<PurchaseItem>();
		itemsInventory.put(sprite, 30);
		itemsInventory.put(cocaCola, 30);
		itemsInventory.put(fanta, 30);
		itemsInventory.put(water, 200);
		
		return itemsInventory;
	}

}
