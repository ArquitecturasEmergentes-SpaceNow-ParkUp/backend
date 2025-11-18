package pe.edu.upc.ParkUp.ParkUp_platform.affiliate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.commandservices.ParkingLotCommandServiceImpl;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ParkingLotMapIntegrationTest {


    @Autowired
    private ParkingLotCommandServiceImpl parkingLotCommandService;

    @Test
    @Transactional
    void addMapWithLargeJsonShouldPersist() {
        var lot = parkingLotCommandService.handle(new CreateParkingLotCommand("Test Lot", "Address", null));
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < 300; i++) {
            sb.append("{\"row\":\"R").append(i).append("\",\"slots\":[]}");
        }
        sb.append("]");
        String big = sb.toString();

        var savedMap = parkingLotCommandService.handle(new AddParkingLotMapCommand(lot.getId(), big, 1, 0));

        assertThat(savedMap.getLayout()).isNotNull();
        assertThat(savedMap.getLayout().getLayoutJson()).isEqualTo(big);
    }
}
