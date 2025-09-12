package pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.email(), signInResource.password());
  }
}
