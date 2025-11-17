package pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries;

public record GetAllLogsQuery(
    Integer page,
    Integer size,
    String action,
    String username,
    String status,
    String startDate,
    String endDate,
    String search
) {}