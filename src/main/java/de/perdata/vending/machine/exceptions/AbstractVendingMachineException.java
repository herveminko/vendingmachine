package de.perdata.vending.machine.exceptions;

/**
 * Standard abstract Vending Machine Exception.
 * @author minko.
 *
 */
public abstract class AbstractVendingMachineException extends RuntimeException {
	
	private static final long serialVersionUID = 1965318654490262861L;
	protected String message;
    
    protected AbstractVendingMachineException() {
		super();
	}

	protected AbstractVendingMachineException(String string) {
        this.message = string;
    }
   
    @Override
    public String getMessage(){
        return message;
    }
   
}