package de.perdata.vending.machine.exceptions;

/**
 * Exception thrown when a person want buy an item without paying the full
 * amount of money.
 * 
 * @author minko
 *
 */
public class NotFullyPaidException extends AbstractVendingMachineException {
	
	private static final long serialVersionUID = 5319443706266037422L;
	private Long remainingMoneyAmount;
       
    public NotFullyPaidException(String message, long remainingMoneyAmount) {
        this.message = message;
        this.remainingMoneyAmount = remainingMoneyAmount;
    }
   
    public Long getRemaining(){
        return remainingMoneyAmount;
    }
   
    @Override
    public String getMessage(){
        return message + remainingMoneyAmount;
    } 
   
}