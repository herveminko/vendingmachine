package de.perdata.vending.machine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.AfterClass;
import org.junit.Test;

import de.perdata.vending.machine.exceptions.NotEnoughChangeException;
import de.perdata.vending.machine.exceptions.NotFullyPaidException;
import de.perdata.vending.machine.exceptions.SoldOutException;
import de.perdata.vending.machine.factories.PurchaseItemFillStrategyFactory;
import de.perdata.vending.machine.factories.VendingMachineFactory;
import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;
import de.perdata.vending.machine.interfaces.IVendingMachine;
import de.perdata.vending.machine.strategies.FillStrategyDefault;
import de.perdata.vending.machine.util.MoneyUtil;

/**
 * Test class for all VendingMachineImpl API methods.
 * @author minko
 *
 */

public class VendingMachineImplTest {
	
	private static IVendingMachine vendingMachineUnderTest;

	@AfterClass
	public static void stop() {
		vendingMachineUnderTest = null;
	}

	@Test(expected = SoldOutException.class)
	public void testSoldOut() throws ConfigurationException {

		// given
		vendingMachineUnderTest = VendingMachineFactory.createVendingMachine();
		vendingMachineUnderTest.fillMachine();
		vendingMachineUnderTest.emptyMachine();

		// then

		// A SoldOutException should occur, since the machine has been emptied!
		vendingMachineUnderTest.getItemAndMoneyChange(1, Arrays.asList(MoneyCoin.TWO_EURO, MoneyCoin.ONE_EURO));

	}
	
	@Test
	public void testBuyItemWithMoneyChange() throws ConfigurationException {
		// given

		vendingMachineUnderTest = VendingMachineFactory.createVendingMachine();
		vendingMachineUnderTest.fillMachine();
		

		// then
		// select item
		Integer selectedMachinePosition = 1;
		PurchaseItem item = vendingMachineUnderTest.getItemAtPosition(selectedMachinePosition);
		// Purchase the item
		PurchaseResult purchaseResult = vendingMachineUnderTest.getItemAndMoneyChange(selectedMachinePosition,
				Arrays.asList(MoneyCoin.TWO_EURO, MoneyCoin.TWENTY_CENT));

		PurchaseItem purchasedItem = purchaseResult.getPurchasedItem();
		List<MoneyCoin> change = purchaseResult.getMoneyChange();

		// selected item price should be Sprite's price (from default configuration)
		Long price = item.getItemPrice();
		assertEquals(Long.valueOf(100L), price);

		// Purchase Item should be Sprite
		//assertEquals("Sprite", purchasedItem.getItemName());
		assertEquals(item, purchasedItem);

		// Money change must be available
		assertTrue(!change.isEmpty());
		// The change value must be 1,20 €
		assertEquals(Integer.valueOf(120), MoneyUtil.getTotalMoneyCount(change));
	}

	@Test
	public void testBuyItemWithoutMoneyChange() throws ConfigurationException {

		// given

		vendingMachineUnderTest = VendingMachineFactory.createVendingMachine();
		vendingMachineUnderTest.fillMachine();

		// then
		// select item
		Integer selectedMachinePosition = 1;
		PurchaseItem item = vendingMachineUnderTest.getItemAtPosition(selectedMachinePosition);
		// Purchase the item
		PurchaseResult purchaseResult = vendingMachineUnderTest.getItemAndMoneyChange(selectedMachinePosition,
				Arrays.asList(MoneyCoin.FIFTY_CENT, MoneyCoin.FIFTY_CENT));

		PurchaseItem purchasedItem = purchaseResult.getPurchasedItem();
		List<MoneyCoin> change = purchaseResult.getMoneyChange();

		// selected item price should be Sprite's price (from default configuration)
		Long price = item.getItemPrice();
		assertEquals(Long.valueOf(100L), price);

		// Purchase Item should be Sprite
		//assertEquals("Sprite", purchasedItem.getItemName());
		assertEquals(item, purchasedItem);

		// there should not be any change
		assertTrue(change.isEmpty());
	}

	@Test(expected = NotFullyPaidException.class)
	public void testNotFullyPaidException() throws ConfigurationException {

		// given

		vendingMachineUnderTest = VendingMachineFactory.createVendingMachine();
		vendingMachineUnderTest.fillMachine();
		// select item
		Integer selectedMachinePosition = 1;

		// then

		// NotFullyPaidException should occur, since no item cost only ten cent.
		vendingMachineUnderTest.getItemAndMoneyChange(selectedMachinePosition, Arrays.asList(MoneyCoin.TEN_CENT));
	}

	@Test(expected = NotEnoughChangeException.class)
	public void testNotEnoughChangeException() throws ConfigurationException {
		// given

		vendingMachineUnderTest = VendingMachineFactory.createVendingMachine();
		vendingMachineUnderTest.fillMachine();
		// select item
		Integer selectedMachinePosition = 1;

		// then

		// NotEnoughChangeException should occur.
		vendingMachineUnderTest.getItemAndMoneyChange(selectedMachinePosition, Arrays.asList(MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO,
				MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO,
				MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO,
				MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO, MoneyCoin.TWO_EURO));
	}

	@Test
	public void testDefaultStrategyForTesting() throws ConfigurationException {
		// given

		/*
		 * Default vending machine filling strategy. Available items are "Coca Cola"
		 * (1), "Sprite" (1) an "Water" (2). Prices: "Coca Cola" and "Sprite" --> 100
		 * Cent , "Water" --> 65 Cent.
		 */
		IPurchaseItemFillStrategy strategy = PurchaseItemFillStrategyFactory.createPurchaseItemFillStrategy();

		// then
		assertTrue(strategy instanceof FillStrategyDefault);
	}

}