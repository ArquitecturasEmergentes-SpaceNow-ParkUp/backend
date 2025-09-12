package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String email, String token) {
}
