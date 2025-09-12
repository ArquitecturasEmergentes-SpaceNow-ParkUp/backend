package pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
