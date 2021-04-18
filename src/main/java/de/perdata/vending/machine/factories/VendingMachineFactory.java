package de.perdata.vending.machine.factories;

import org.apache.commons.configuration2.ex.ConfigurationException;

import de.perdata.vending.machine.entities.VendingMachineImpl;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;
import de.perdata.vending.machine.interfaces.IVendingMachine;

/**
 * Factory class for instantiating vending machines.
 * 
 * @author minko
 *
 */
public class VendingMachineFactory {
	
	private VendingMachineFactory() {		
	}
	
	public static IVendingMachine createVendingMachine() throws ConfigurationException { 			
	 	IVendingMachine	vendingMachine = new VendingMachineImpl();
	 	IPurchaseItemFillStrategy fillStrategy = PurchaseItemFillStrategyFactory.createPurchaseItemFillStrategy();	
	 	vendingMachine.setPurchaseItemFillStrategy(fillStrategy);
		return vendingMachine;
	}
}