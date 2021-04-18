package de.perdata.vending.machine.exceptions;

/**
 * Exception thrown when a person want buy an item which is no longer available.
 * 
 * @author minko
 *
 */
public class SoldOutException extends AbstractVendingMachineException {
	
	public SoldOutException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 5020412359359073918L;
}