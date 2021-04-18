package de.perdata.vending.machine.exceptions;

/**
 * Exception thrown when a person want buy an item but the machine does not have
 * enough cash to return the money change.
 * 
 * @author minko
 *
 */
public class NotEnoughChangeException extends AbstractVendingMachineException {

	public NotEnoughChangeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -3386660388054297568L;
}