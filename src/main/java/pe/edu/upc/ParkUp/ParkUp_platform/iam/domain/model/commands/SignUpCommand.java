package pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands;

import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String email, String password, List<Role> roles) {
}
