package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.MakeEdgeNodeRequestCommand;

/**
 * EdgeNodeCommunicationService
 * <p>
 *     This service defines the communication operations with Edge Nodes.
 * </p>
 */
public interface EdgeNodeCommunicationService {
    
    /**
     * Make HTTP request to Edge Node
     * @param command the make edge node request command
     * @return response from Edge Node
     */
    String makeRequest(MakeEdgeNodeRequestCommand command);
}
