package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries;


public record GetAllCreditCardsByUserIdQuery(long userId) {

    /**
     * Constructor for GetAllCredirCardsByUserId.
     *
     * @param userId the ID of the user whose credit cards are to be retrieved
     */
    public GetAllCreditCardsByUserIdQuery {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
    }
}
