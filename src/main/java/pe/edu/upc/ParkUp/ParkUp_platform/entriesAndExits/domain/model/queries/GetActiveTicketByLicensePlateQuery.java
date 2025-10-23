package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries;

import jakarta.validation.constraints.NotBlank;

/**
 * GetActiveTicketByLicensePlateQuery
 * <p>
 *     This query represents the request to get an active ticket by license plate.
 * </p>
 */
public record GetActiveTicketByLicensePlateQuery(
    @NotBlank String licensePlate
) {
    
    /**
     * Create a new GetActiveTicketByLicensePlateQuery
     * @param licensePlate the license plate of the vehicle
     * @return new GetActiveTicketByLicensePlateQuery instance
     */
    public static GetActiveTicketByLicensePlateQuery of(String licensePlate) {
        return new GetActiveTicketByLicensePlateQuery(licensePlate);
    }
}
