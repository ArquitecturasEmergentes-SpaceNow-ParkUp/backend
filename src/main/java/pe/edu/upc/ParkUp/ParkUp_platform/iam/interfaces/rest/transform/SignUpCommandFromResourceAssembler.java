package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.entities.Role;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList()
        : new ArrayList<Role>();
    return new SignUpCommand(resource.email(), resource.password(), roles);
  }
}
