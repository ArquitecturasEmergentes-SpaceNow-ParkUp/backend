package pe.edu.upc.ParkUp.ParkUp_platform.affiliate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingSpaceRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
// imports adjusted

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ParkingLotMapAutoImportTest {

    @Autowired
    private pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotCommandService parkingLotCommandService;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    // parkingLotRepository not needed here

    @Test
    public void testAutoImportFromLayout() {
        var lot = parkingLotCommandService.handle(new CreateParkingLotCommand("AutoLot", "Foo", null));
        assertNotNull(lot);

        // Layout sample with a few spaces
        String layoutJson = "[\n" +
                "  { \"row\": \"A\", \"slots\": [ {\"ids\": [\"A1\",\"A2\"], \"gap\": false }, {\"ids\": [], \"gap\": true } ] },\n" +
                "  { \"row\": \"B\", \"slots\": [ {\"ids\": [\"B1\"], \"gap\": false } ] }\n" +
                "]";

        var saved = parkingLotCommandService.handle(new AddParkingLotMapCommand(lot.getId(), layoutJson, 3, 0));
        assertNotNull(saved);

        var spaces = parkingSpaceRepository.findByMapId(saved.getId());
        assertEquals(3, spaces.size());
        assertTrue(spaces.stream().anyMatch(s -> s.getCode().equals("A1")));
        assertTrue(spaces.stream().anyMatch(s -> s.getCode().equals("B1")));
    }
}
