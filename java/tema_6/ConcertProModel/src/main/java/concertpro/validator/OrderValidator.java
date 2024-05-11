package concertpro.validator;

import concertpro.exception.ValidationException;
import concertpro.model.Order;

public class OrderValidator implements Validator<Order> {
    @Override
    public void validate(Order entity) throws ValidationException {
        String errorMessage = "";
        if(entity.getBuyerName().isBlank() || entity.getBuyerName() == null) {
            errorMessage += "Buyer Name should not be null!\n";
        }
        if(entity.getSpectacle() == null) {
            errorMessage += "Spectacle should not be null!\n";
        }
        if(entity.getNumberOfSeats() < 0 || entity.getNumberOfSeats() == null) {
            errorMessage += "Number of seats should be positive!";
        }

        if(!errorMessage.isBlank()) {
            throw new ValidationException(errorMessage);
        }
    }
}
