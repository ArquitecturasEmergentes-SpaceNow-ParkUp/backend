package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String email, List<String> roles) {
}
