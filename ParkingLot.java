class ParkingLot {
    List<ParkingFloor> parkingFloors;
    List<Entrance> entrances;
    List<Exit> exits;
    
    Address address;
    
    String parkingLotName;
    
    public boolean isParkingSpaceAvailableForVehicle (Vehicle vehicle);
    public boolean updateParkingAttendant (ParkingAttendant parkingAttendant, int gateId);
}

class ParkingFloor {
    int levelId;
    bool isFull;
    List<ParkingSpace> parkingSpaces;
    
    ParkingDisplayBoard displayBoard;
}

class Gate {
    int gateId;
    ParkingAttendant parkingAttendant;
}

class Entarnce extends Gate {
    public ParkingTicket getParkingTicket(Vehicle vehicle);
}

class Exit extends Gate {
    public ParkingTicket payForParking(ParkingTicket parkingTicket, Vehicle vehicle);
}

class Address {
    String city;
    String state;
    String country;
    String zipCode;
    String street;
}

class ParkingSpace {
    int parkingSpaceId;
    boolean isFree;
    Vehicle vehicle;
    double costPerHour;
    ParkingSpaceType parkingSpaceType;
}

class ParkingDisplayBoard {
    Map<Integer, ParkingSpaceType> freeSlotsAvailable;
    public void updateFreeSlotAvailable(ParkingSpaceType parkingSpaceType, int parkingSpaceId);
}


class Account {
    String name;
    String email;
    String password;
    String empId;
    Address address;
}


class Admin extends Account {
    public boolean addParkingFloor(ParkingLot parkinglot, ParkingFloor parkingfloor);
    public boolean addParkingSpace(ParkingFloor parkingfloor,ParkingSpace parkingspace);
    public boolean addParkingDisplayBoard(ParkingFloor parkingfloor, ParkingDisplayBoard displayboard);
}

class ParkingAttendant extends Account {
    Payment paymentService;
    public boolean processVehicleEntry(Vehicle vehicle);
    public boolean processPayment(ParkingTicket ticket, PaymentType paymentType);
}

class Vehicle {
    String licenseNumber;
    VehicleType vehicleType;
    ParkingTicket parkingTicket;
    PaymentInfo paymentInfo;
}

class ParkingTicket {
    double cost;
    int ticketId;
    int levelId;
    int spaceId;
    Date vehicleEntryDate;
    Date vehicleExitDate;
    ParkingSpaceType parkingSpaceType;
    ParkingTicketStatus ticketStatus;
    
    public void updateTicketCost();
    public void updateTicketExitTime(Date vehicleExitDate);
}

public enum PaymentType {
    CREDIT_CARD, DEBIT_CARD, CASH, UPI;
}

public enum ParkingSpaceType {
    BIKE_PARKING, CAR_PARKING, TRUCK_PARKING;
}

class Payment {
    public PaymentInfo makePayment(ParkingTicket ticket, PaymentType paymentType);
}

public class PaymentInfo {
    double amount;
    Date paymentDate;
    int transactionId;
    ParkingTicket parkingTicket;
    PaymentStatus paymentStatus;
}

public enum VehicleType {
    BIKE, CAR, TRUCK;
}

public enum ParkingTicketStatus {
    PAID, ACTIVE;
}

public enum PaymentStatus {
    UNPAID, PENDING, COMPLETED, DECLINED, CANCELLED, REFUNDED;
}

