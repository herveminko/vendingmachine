package de.perdata.vending.machine.util;

import java.util.List;

import de.perdata.vending.machine.entities.MoneyCoin;

/**
 * Utility class for handling MoneyCoins effectively.
 * @author minko
 *
 */
public class MoneyUtil {

	private MoneyUtil() {
	}

	public static Integer getTotalMoneyCount(List<MoneyCoin> payment) {
		return payment.stream().map(coin -> coin.getAmountInCent()).reduce(0, Integer::sum);
	}
}
