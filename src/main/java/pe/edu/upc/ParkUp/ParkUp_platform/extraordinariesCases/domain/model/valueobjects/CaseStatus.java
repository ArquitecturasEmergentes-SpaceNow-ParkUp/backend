package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.valueobjects;

public enum CaseStatus {
    ENTERED,    // "Extraordinary cases entered"
    OPENED,     // "Recognition-Unit opened"
    SURPASSED,  // "Barrier surpassed"
    FAILED      // "No vehicle at barrier", "Car plate... unregistered"
}