package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;


import org.springframework.http.HttpMethod;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.MakeEdgeNodeRequestCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;

public class OpenDoorCommandFromResourceAssembler {

    public static MakeEdgeNodeRequestCommand toCommand(RecognitionUnit unit) {
        return MakeEdgeNodeRequestCommand.of(
                unit.getId(),
                "/open-door",
                HttpMethod.POST,
                "{\"action\": \"open\"}"
        );
    }
}