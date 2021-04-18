package de.perdata.vending.machine.factories;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import de.perdata.vending.machine.interfaces.IPurchaseItemFillStrategy;
import de.perdata.vending.machine.strategies.FillStrategyDefault;
import de.perdata.vending.machine.strategies.FillStrategyLeipzip;
import de.perdata.vending.machine.strategies.FillStrategyPaderborn;

/**
 * Create an appropriate strategy to fill the vending machine appropriately,
 * depending on his location.
 * 
 * @author minko.
 *
 */
public class PurchaseItemFillStrategyFactory {

	private static final String PADERBORN_LOCATION = "Paderborn";
	private static final String LEIPZIG_LOCATION = "Leipzig";

	private static Configuration applicationConfiguration;
	
	private PurchaseItemFillStrategyFactory() {		
	}

	/**
	 * Create an appropriate vendor machine filling strategy depending on the
	 * configured location.
	 * 
	 * @throws ConfigurationException
	 */
	public static IPurchaseItemFillStrategy createPurchaseItemFillStrategy() throws ConfigurationException {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.properties().setFileName("config.properties"));
		applicationConfiguration = builder.getConfiguration();

		if (applicationConfiguration.getString("machine.location").equalsIgnoreCase(PADERBORN_LOCATION)) {
			return new FillStrategyPaderborn();
		} else if (applicationConfiguration.getString("machine.location").equalsIgnoreCase(LEIPZIG_LOCATION)) {
			return new FillStrategyLeipzip();
		}

		return new FillStrategyDefault();
	}
}
