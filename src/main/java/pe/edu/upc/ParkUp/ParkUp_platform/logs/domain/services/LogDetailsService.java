package pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;

public interface LogDetailsService {

    CreateLogCommand buildAuthLog(String action,
                                  Long userId,
                                  String username,
                                  String userEmail,
                                  String details,
                                  HttpServletRequest request,
                                  String status,
                                  String requestPayloadHash,
                                  Long resourceId);

    CreateLogCommand buildFromRequest(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String username,
                                      Long userId,
                                      Exception ex,
                                      String requestPayloadHash,
                                      Long startTimeMillis);
}
