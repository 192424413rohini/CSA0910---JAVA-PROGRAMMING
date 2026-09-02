import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartWasteSystem {

    // =========================================================
    // CUSTOM EXCEPTIONS
    // =========================================================

    static class InvalidResidentException extends Exception {
        public InvalidResidentException(String message) {
            super(message);
        }
    }

    static class DuplicateRequestException extends Exception {
        public DuplicateRequestException(String message) {
            super(message);
        }
    }

    static class VehicleUnavailableException extends Exception {
        public VehicleUnavailableException(String message) {
            super(message);
        }
    }

    static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    // =========================================================
    // RESIDENT CLASS
    // =========================================================

    static class Resident {
        private int residentId;
        private String name;
        private String area;
        private String phone;

        public Resident(int residentId, String name, String area, String phone) {
            this.residentId = residentId;
            this.name = name;
            this.area = area;
            this.phone = phone;
        }

        public int getResidentId() {
            return residentId;
        }

        public String getName() {
            return name;
        }

        public String getArea() {
            return area;
        }

        public String getPhone() {
            return phone;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void display() {
            System.out.println(
                "ID: " + residentId +
                " | Name: " + name +
                " | Area: " + area +
                " | Phone: " + phone
            );
        }
    }

    // =========================================================
    // BIN BASE CLASS
    // =========================================================

    static class WasteBin {
        private int binId;
        private String location;
        private int fillLevel;

        public WasteBin(int binId, String location, int fillLevel) {
            this.binId = binId;
            this.location = location;
            this.fillLevel = fillLevel;
        }

        public int getBinId() {
            return binId;
        }

        public String getLocation() {
            return location;
        }

        public synchronized int getFillLevel() {
            return fillLevel;
        }

        public synchronized void setFillLevel(int fillLevel) {
            if (fillLevel < 0) {
                this.fillLevel = 0;
            } else if (fillLevel > 100) {
                this.fillLevel = 100;
            } else {
                this.fillLevel = fillLevel;
            }
        }

        public String getBinCategory() {
            return "General Waste";
        }

        public int getCollectionPriority() {
            return fillLevel >= 80 ? 1 : 2;
        }

        public void display() {
            System.out.println(
                "Bin ID: " + binId +
                " | Location: " + location +
                " | Fill: " + fillLevel + "%" +
                " | Category: " + getBinCategory() +
                " | Priority: " + getCollectionPriority()
            );
        }
    }

    // =========================================================
    // INHERITANCE - RECYCLABLE BIN
    // =========================================================

    static class RecyclableBin extends WasteBin {

        public RecyclableBin(int binId, String location, int fillLevel) {
            super(binId, location, fillLevel);
        }

        @Override
        public String getBinCategory() {
            return "Recyclable";
        }

        @Override
        public int getCollectionPriority() {
            return getFillLevel() >= 70 ? 1 : 3;
        }
    }

    // =========================================================
    // INHERITANCE - ORGANIC BIN
    // =========================================================

    static class OrganicBin extends WasteBin {

        public OrganicBin(int binId, String location, int fillLevel) {
            super(binId, location, fillLevel);
        }

        @Override
        public String getBinCategory() {
            return "Organic";
        }

        @Override
        public int getCollectionPriority() {
            return getFillLevel() >= 60 ? 1 : 2;
        }
    }

    // =========================================================
    // VEHICLE BASE CLASS
    // =========================================================

    static class Vehicle {
        private int vehicleId;
        private String driver;
        private int capacity;
        private boolean available;

        public Vehicle(int vehicleId, String driver, int capacity) {
            this.vehicleId = vehicleId;
            this.driver = driver;
            this.capacity = capacity;
            this.available = true;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public String getDriver() {
            return driver;
        }

        public int getCapacity() {
            return capacity;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getVehicleType() {
            return "General Collection Vehicle";
        }

        public void display() {
            System.out.println(
                "Vehicle ID: " + vehicleId +
                " | Driver: " + driver +
                " | Capacity: " + capacity + " kg" +
                " | Type: " + getVehicleType() +
                " | Status: " +
                (available ? "Available" : "Assigned")
            );
        }
    }

    // =========================================================
    // INHERITANCE - RECYCLING VEHICLE
    // =========================================================

    static class RecyclingVehicle extends Vehicle {

        public RecyclingVehicle(int vehicleId, String driver, int capacity) {
            super(vehicleId, driver, capacity);
        }

        @Override
        public String getVehicleType() {
            return "Recycling Vehicle";
        }
    }

    // =========================================================
    // INHERITANCE - ORGANIC VEHICLE
    // =========================================================

    static class OrganicVehicle extends Vehicle {

        public OrganicVehicle(int vehicleId, String driver, int capacity) {
            super(vehicleId, driver, capacity);
        }

        @Override
        public String getVehicleType() {
            return "Organic Waste Vehicle";
        }
    }

    // =========================================================
    // ROUTE CLASS
    // =========================================================

    static class Route {
        private int routeId;
        private String routeName;
        private String area;
        private int vehicleId;
        private boolean active;

        public Route(int routeId, String routeName, String area, int vehicleId) {
            this.routeId = routeId;
            this.routeName = routeName;
            this.area = area;
            this.vehicleId = vehicleId;
            this.active = true;
        }

        public int getRouteId() {
            return routeId;
        }

        public String getRouteName() {
            return routeName;
        }

        public String getArea() {
            return area;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void display() {
            System.out.println(
                "Route ID: " + routeId +
                " | Route: " + routeName +
                " | Area: " + area +
                " | Vehicle: " + vehicleId +
                " | Status: " +
                (active ? "Active" : "Inactive")
            );
        }
    }

    // =========================================================
    // ALERT CLASS
    // =========================================================

    static class Alert {
        private int alertId;
        private String message;
        private String severity;
        private LocalDateTime createdAt;

        public Alert(int alertId, String message, String severity) {
            this.alertId = alertId;
            this.message = message;
            this.severity = severity;
            this.createdAt = LocalDateTime.now();
        }

        public int getAlertId() {
            return alertId;
        }

        public String getMessage() {
            return message;
        }

        public String getSeverity() {
            return severity;
        }

        public void display() {
            DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            System.out.println(
                "[" + severity + "] " +
                message +
                " | Time: " +
                createdAt.format(formatter)
            );
        }
    }

    // =========================================================
    // COLLECTION REQUEST
    // =========================================================

    static class CollectionRequest {
        private int requestId;
        private int residentId;
        private int binId;
        private String requestType;
        private String status;

        public CollectionRequest(
            int requestId,
            int residentId,
            int binId,
            String requestType
        ) {
            this.requestId = requestId;
            this.residentId = residentId;
            this.binId = binId;
            this.requestType = requestType;
            this.status = "PENDING";
        }

        public int getRequestId() {
            return requestId;
        }

        public int getResidentId() {
            return residentId;
        }

        public int getBinId() {
            return binId;
        }

        public String getRequestType() {
            return requestType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void display() {
            System.out.println(
                "Request ID: " + requestId +
                " | Resident: " + residentId +
                " | Bin: " + binId +
                " | Type: " + requestType +
                " | Status: " + status
            );
        }
    }

    // =========================================================
    // GLOBAL COLLECTIONS
    // =========================================================

    static ArrayList<Resident> residents = new ArrayList<>();

    static ArrayList<WasteBin> bins = new ArrayList<>();

    static ArrayList<Vehicle> vehicles = new ArrayList<>();

    static ArrayList<Route> routes = new ArrayList<>();

    static ArrayList<CollectionRequest> requests = new ArrayList<>();

    static ArrayList<Alert> alerts = new ArrayList<>();

    static Set<Integer> registeredResidentIds = new HashSet<>();

    static Set<Integer> registeredBinIds = new HashSet<>();

    static HashMap<Integer, Resident> residentMap = new HashMap<>();

    static Hashtable<Integer, Vehicle> vehicleTable =
        new Hashtable<>();

    static Queue<CollectionRequest> waitlist =
        new LinkedList<>();

    static Scanner scanner = new Scanner(System.in);

    static int nextRequestId = 1001;

    static int nextAlertId = 5001;

    static volatile boolean monitoringRunning = true;

    // Shared data lock for synchronization
    static final Object systemLock = new Object();

    // =========================================================
    // RESIDENT REGISTRATION
    // =========================================================

    static void addResident() {

        try {

            System.out.print("Enter Resident ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id <= 0) {
                throw new InvalidInputException(
                    "Resident ID must be positive."
                );
            }

            if (registeredResidentIds.contains(id)) {
                throw new InvalidInputException(
                    "Resident ID already exists."
                );
            }

            System.out.print("Enter Resident Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Area: ");
            String area = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();

            if (name.isEmpty() || area.isEmpty() || phone.isEmpty()) {
                throw new InvalidInputException(
                    "Fields cannot be empty."
                );
            }

            Resident resident =
                new Resident(id, name, area, phone);

            residents.add(resident);

            registeredResidentIds.add(id);

            residentMap.put(id, resident);

            System.out.println(
                "\nResident registered successfully."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Error: Enter a valid numeric ID."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                "Validation Error: " + e.getMessage()
            );
        }
    }

    // =========================================================
    // DISPLAY RESIDENTS USING ITERATOR
    // =========================================================

    static void displayResidents() {

        if (residents.isEmpty()) {
            System.out.println("No resident records found.");
            return;
        }

        System.out.println("\n--------- RESIDENT RECORDS ---------");

        Iterator<Resident> iterator =
            residents.iterator();

        while (iterator.hasNext()) {
            iterator.next().display();
        }
    }

    // =========================================================
    // SEARCH RESIDENT USING HASHMAP
    // =========================================================

    static void searchResident() {

        try {

            System.out.print("Enter Resident ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Resident resident = residentMap.get(id);

            if (resident == null) {
                throw new InvalidResidentException(
                    "Resident ID " + id + " not found."
                );
            }

            System.out.println("\nResident found:");
            resident.display();

        } catch (NumberFormatException e) {

            System.out.println("Enter a valid resident ID.");

        } catch (InvalidResidentException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }

    // =========================================================
    // UPDATE RESIDENT USING LISTITERATOR
    // =========================================================

    static void updateResident() {

        try {

            System.out.print("Enter Resident ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            ListIterator<Resident> iterator =
                residents.listIterator();

            boolean found = false;

            while (iterator.hasNext()) {

                Resident resident = iterator.next();

                if (resident.getResidentId() == id) {

                    found = true;

                    System.out.print(
                        "Enter new name: "
                    );

                    String name = scanner.nextLine();

                    System.out.print(
                        "Enter new area: "
                    );

                    String area = scanner.nextLine();

                    System.out.print(
                        "Enter new phone: "
                    );

                    String phone = scanner.nextLine();

                    resident.setName(name);
                    resident.setArea(area);
                    resident.setPhone(phone);

                    iterator.set(resident);

                    residentMap.put(id, resident);

                    System.out.println(
                        "Resident updated successfully."
                    );

                    break;
                }
            }

            if (!found) {
                throw new InvalidResidentException(
                    "Resident not found."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid ID.");

        } catch (InvalidResidentException e) {

            System.out.println(e.getMessage());
        }
    }

    // =========================================================
    // ADD BIN
    // =========================================================

    static void addBin() {

        try {

            System.out.print("Enter Bin ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (registeredBinIds.contains(id)) {
                throw new InvalidInputException(
                    "Bin ID already exists."
                );
            }

            System.out.print("Enter location: ");
            String location = scanner.nextLine();

            System.out.print(
                "Enter initial fill level (0-100): "
            );

            int fill = Integer.parseInt(scanner.nextLine());

            if (fill < 0 || fill > 100) {
                throw new InvalidInputException(
                    "Fill level must be between 0 and 100."
                );
            }

            System.out.println(
                "\n1. General"
            );

            System.out.println(
                "2. Recyclable"
            );

            System.out.println(
                "3. Organic"
            );

            System.out.print("Select bin type: ");

            int type = Integer.parseInt(scanner.nextLine());

            WasteBin bin;

            if (type == 1) {

                bin = new WasteBin(
                    id,
                    location,
                    fill
                );

            } else if (type == 2) {

                bin = new RecyclableBin(
                    id,
                    location,
                    fill
                );

            } else if (type == 3) {

                bin = new OrganicBin(
                    id,
                    location,
                    fill
                );

            } else {

                throw new InvalidInputException(
                    "Invalid bin type."
                );
            }

            bins.add(bin);

            registeredBinIds.add(id);

            System.out.println(
                "Bin added successfully."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Please enter valid numbers."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }

    // =========================================================
    // DISPLAY BINS
    // =========================================================

    static void displayBins() {

        if (bins.isEmpty()) {

            System.out.println(
                "No bin records available."
            );

            return;
        }

        System.out.println(
            "\n--------- BIN STATUS ---------"
        );

        for (WasteBin bin : bins) {
            bin.display();
        }
    }

    // =========================================================
    // UPDATE BIN FILL LEVEL
    // =========================================================

    static void updateBinFillLevel() {

        try {

            System.out.print("Enter Bin ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            WasteBin selectedBin = null;

            for (WasteBin bin : bins) {

                if (bin.getBinId() == id) {

                    selectedBin = bin;
                    break;
                }
            }

            if (selectedBin == null) {

                throw new InvalidInputException(
                    "Bin not found."
                );
            }

            System.out.print(
                "Enter new fill percentage: "
            );

            int fill =
                Integer.parseInt(scanner.nextLine());

            if (fill < 0 || fill > 100) {

                throw new InvalidInputException(
                    "Fill must be between 0 and 100."
                );
            }

            synchronized (systemLock) {

                selectedBin.setFillLevel(fill);

                systemLock.notifyAll();
            }

            System.out.println(
                "Bin status updated."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid numeric input."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }

    // =========================================================
    // ADD VEHICLE
    // =========================================================

    static void addVehicle() {

        try {

            System.out.print("Enter Vehicle ID: ");

            int id =
                Integer.parseInt(scanner.nextLine());

            if (vehicleTable.containsKey(id)) {

                throw new InvalidInputException(
                    "Vehicle ID already exists."
                );
            }

            System.out.print("Enter Driver Name: ");

            String driver =
                scanner.nextLine();

            System.out.print(
                "Enter Capacity in kg: "
            );

            int capacity =
                Integer.parseInt(scanner.nextLine());

            if (capacity <= 0) {

                throw new InvalidInputException(
                    "Capacity must be positive."
                );
            }

            System.out.println(
                "\n1. General Vehicle"
            );

            System.out.println(
                "2. Recycling Vehicle"
            );

            System.out.println(
                "3. Organic Waste Vehicle"
            );

            System.out.print(
                "Select vehicle type: "
            );

            int type =
                Integer.parseInt(scanner.nextLine());

            Vehicle vehicle;

            if (type == 1) {

                vehicle =
                    new Vehicle(id, driver, capacity);

            } else if (type == 2) {

                vehicle =
                    new RecyclingVehicle(
                        id,
                        driver,
                        capacity
                    );

            } else if (type == 3) {

                vehicle =
                    new OrganicVehicle(
                        id,
                        driver,
                        capacity
                    );

            } else {

                throw new InvalidInputException(
                    "Invalid vehicle type."
                );
            }

            vehicles.add(vehicle);

            vehicleTable.put(id, vehicle);

            System.out.println(
                "Vehicle registered successfully."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Enter valid numeric values."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }

    // =========================================================
    // DISPLAY VEHICLES
    // =========================================================

    static void displayVehicles() {

        if (vehicles.isEmpty()) {

            System.out.println(
                "No vehicle records available."
            );

            return;
        }

        System.out.println(
            "\n--------- VEHICLE RECORDS ---------"
        );

        Iterator<Vehicle> iterator =
            vehicles.iterator();

        while (iterator.hasNext()) {

            iterator.next().display();
        }
    }

    // =========================================================
    // ADD ROUTE
    // =========================================================

    static void addRoute() {

        try {

            System.out.print("Enter Route ID: ");

            int routeId =
                Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Route Name: ");

            String routeName =
                scanner.nextLine();

            System.out.print("Enter Area: ");

            String area =
                scanner.nextLine();

            System.out.print(
                "Enter Vehicle ID: "
            );

            int vehicleId =
                Integer.parseInt(scanner.nextLine());

            if (!vehicleTable.containsKey(vehicleId)) {

                throw new VehicleUnavailableException(
                    "Vehicle does not exist."
                );
            }

            Vehicle vehicle =
                vehicleTable.get(vehicleId);

            if (!vehicle.isAvailable()) {

                throw new VehicleUnavailableException(
                    "Vehicle is already assigned."
                );
            }

            Route route =
                new Route(
                    routeId,
                    routeName,
                    area,
                    vehicleId
                );

            routes.add(route);

            vehicle.setAvailable(false);

            System.out.println(
                "Route created and vehicle assigned."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid numeric input."
            );

        } catch (VehicleUnavailableException e) {

            System.out.println(
                "Vehicle Error: " +
                e.getMessage()
            );
        }
    }

    // =========================================================
    // DISPLAY ROUTES
    // =========================================================

    static void displayRoutes() {

        if (routes.isEmpty()) {

            System.out.println(
                "No route schedules available."
            );

            return;
        }

        System.out.println(
            "\n--------- ROUTE SCHEDULE ---------"
        );

        for (Route route : routes) {

            route.display();
        }
    }

    // =========================================================
    // CREATE COLLECTION REQUEST
    // =========================================================

    static void createCollectionRequest() {

        try {

            System.out.print(
                "Enter Resident ID: "
            );

            int residentId =
                Integer.parseInt(scanner.nextLine());

            if (!residentMap.containsKey(residentId)) {

                throw new InvalidResidentException(
                    "Resident does not exist."
                );
            }

            System.out.print(
                "Enter Bin ID: "
            );

            int binId =
                Integer.parseInt(scanner.nextLine());

            if (!registeredBinIds.contains(binId)) {

                throw new InvalidInputException(
                    "Bin does not exist."
                );
            }

            System.out.print(
                "Enter request type: "
            );

            String type =
                scanner.nextLine();

            for (CollectionRequest request : requests) {

                if (
                    request.getResidentId() == residentId
                    &&
                    request.getBinId() == binId
                    &&
                    request.getStatus().equals("PENDING")
                ) {

                    throw new DuplicateRequestException(
                        "A pending request already exists."
                    );
                }
            }

            CollectionRequest request =
                new CollectionRequest(
                    nextRequestId++,
                    residentId,
                    binId,
                    type
                );

            requests.add(request);

            System.out.println(
                "Collection request created."
            );

            request.display();

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid numeric input."
            );

        } catch (InvalidResidentException |
                 InvalidInputException |
                 DuplicateRequestException e) {

            System.out.println(
                "Request Error: " +
                e.getMessage()
            );
        }
    }

    // =========================================================
    // ASSIGN REQUEST TO VEHICLE
    // =========================================================

    static void assignRequest() {

        try {

            System.out.print(
                "Enter Request ID: "
            );

            int requestId =
                Integer.parseInt(scanner.nextLine());

            CollectionRequest selected = null;

            for (CollectionRequest request : requests) {

                if (request.getRequestId() == requestId) {

                    selected = request;
                    break;
                }
            }

            if (selected == null) {

                throw new InvalidInputException(
                    "Request not found."
                );
            }

            if (
                selected.getStatus().equals("CANCELLED")
                ||
                selected.getStatus().equals("COMPLETED")
            ) {

                throw new InvalidInputException(
                    "Request cannot be assigned."
                );
            }

            System.out.print(
                "Enter Vehicle ID: "
            );

            int vehicleId =
                Integer.parseInt(scanner.nextLine());

            Vehicle vehicle =
                vehicleTable.get(vehicleId);

            if (vehicle == null) {

                throw new VehicleUnavailableException(
                    "Vehicle not found."
                );
            }

            if (!vehicle.isAvailable()) {

                throw new VehicleUnavailableException(
                    "Vehicle is currently unavailable."
                );
            }

            vehicle.setAvailable(false);

            selected.setStatus("ASSIGNED");

            System.out.println(
                "Request assigned successfully."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid numeric input."
            );

        } catch (InvalidInputException |
                 VehicleUnavailableException e) {

            System.out.println(
                "Assignment Error: " +
                e.getMessage()
            );
        }
    }

    // =========================================================
    // CANCEL REQUEST
    // =========================================================

    static void cancelRequest() {

        try {

            System.out.print(
                "Enter Request ID: "
            );

            int requestId =
                Integer.parseInt(scanner.nextLine());

            boolean found = false;

            for (CollectionRequest request : requests) {

                if (request.getRequestId() == requestId) {

                    found = true;

                    if (
                        request.getStatus()
                            .equals("CANCELLED")
                    ) {

                        throw new InvalidInputException(
                            "Request is already cancelled."
                        );
                    }

                    request.setStatus("CANCELLED");

                    System.out.println(
                        "Request cancelled."
                    );

                    break;
                }
            }

            if (!found) {

                throw new InvalidInputException(
                    "Request not found."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid request ID."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }

    // =========================================================
    // ADD REQUEST TO WAITLIST
    // =========================================================

    static void addToWaitlist() {

        try {

            System.out.print(
                "Enter Request ID: "
            );

            int requestId =
                Integer.parseInt(scanner.nextLine());

            CollectionRequest selected = null;

            for (CollectionRequest request : requests) {

                if (request.getRequestId() == requestId) {

                    selected = request;
                    break;
                }
            }

            if (selected == null) {

                throw new InvalidInputException(
                    "Request not found."
                );
            }

            synchronized (systemLock) {

                waitlist.offer(selected);

                selected.setStatus("WAITLISTED");

                systemLock.notifyAll();
            }

            System.out.println(
                "Request added to waitlist."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid request ID."
            );

        } catch (InvalidInputException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }

    // =========================================================
    // DISPLAY REQUESTS
    // =========================================================

    static void displayRequests() {

        if (requests.isEmpty()) {

            System.out.println(
                "No collection requests."
            );

            return;
        }

        System.out.println(
            "\n--------- COLLECTION REQUESTS ---------"
        );

        for (CollectionRequest request : requests) {

            request.display();
        }
    }

    // =========================================================
    // DISPLAY WAITLIST
    // =========================================================

    static void displayWaitlist() {

        synchronized (systemLock) {

            if (waitlist.isEmpty()) {

                System.out.println(
                    "Waitlist is empty."
                );

                return;
            }

            System.out.println(
                "\n--------- WAITLIST ---------"
            );

            for (CollectionRequest request : waitlist) {

                request.display();
            }
        }
    }

    // =========================================================
    // GENERATE ALERT
    // =========================================================

    static synchronized void createAlert(
        String message,
        String severity
    ) {

        Alert alert =
            new Alert(
                nextAlertId++,
                message,
                severity
            );

        alerts.add(alert);

        System.out.println(
            "\n*** NEW ALERT ***"
        );

        alert.display();
    }

    // =========================================================
    // DISPLAY ALERTS
    // =========================================================

    static void displayAlerts() {

        if (alerts.isEmpty()) {

            System.out.println(
                "No alerts generated."
            );

            return;
        }

        System.out.println(
            "\n--------- ALERT HISTORY ---------"
        );

        for (Alert alert : alerts) {

            alert.display();
        }
    }

    // =========================================================
    // BIN MONITORING THREAD
    // =========================================================

    static class BinMonitoringTask
        implements Runnable {

        @Override
        public void run() {

            Thread.currentThread()
                .setPriority(Thread.MAX_PRIORITY);

            System.out.println(
                "\n[Monitoring Thread] Started."
            );

            for (int cycle = 1; cycle <= 3; cycle++) {

                synchronized (systemLock) {

                    for (WasteBin bin : bins) {

                        if (bin.getFillLevel() >= 90) {

                            createAlert(
                                "Bin " +
                                bin.getBinId() +
                                " at " +
                                bin.getLocation() +
                                " is critically full (" +
                                bin.getFillLevel() +
                                "%).",
                                "CRITICAL"
                            );

                        } else if (
                            bin.getFillLevel() >= 80
                        ) {

                            createAlert(
                                "Bin " +
                                bin.getBinId() +
                                " requires collection soon.",
                                "WARNING"
                            );
                        }
                    }

                    systemLock.notifyAll();
                }

                try {

                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    System.out.println(
                        "Monitoring thread interrupted."
                    );

                    break;
                }
            }

            System.out.println(
                "[Monitoring Thread] Completed."
            );
        }
    }

    // =========================================================
    // ROUTE ALERT THREAD
    // =========================================================

    static class RouteAlertTask
        implements Runnable {

        @Override
        public void run() {

            Thread.currentThread()
                .setPriority(Thread.NORM_PRIORITY);

            System.out.println(
                "[Route Alert Thread] Started."
            );

            for (Route route : routes) {

                if (!route.isActive()) {

                    createAlert(
                        "Route " +
                        route.getRouteId() +
                        " is inactive.",
                        "ROUTE"
                    );
                }
            }

            synchronized (systemLock) {

                try {

                    if (waitlist.isEmpty()) {

                        System.out.println(
                            "[Route Alert Thread] " +
                            "No pending waitlist items."
                        );

                    } else {

                        System.out.println(
                            "[Route Alert Thread] " +
                            "Waitlist requires attention."
                        );
                    }

                    systemLock.notifyAll();

                } catch (Exception e) {

                    System.out.println(
                        "Route processing error."
                    );
                }
            }

            System.out.println(
                "[Route Alert Thread] Completed."
            );
        }
    }

    // =========================================================
    // START CONCURRENT MONITORING
    // =========================================================

    static void startMonitoring() {

        if (bins.isEmpty() && routes.isEmpty()) {

            System.out.println(
                "Add bins or routes before starting monitoring."
            );

            return;
        }

        monitoringRunning = true;

        Thread binThread =
            new Thread(
                new BinMonitoringTask(),
                "Bin-Monitor"
            );

        Thread routeThread =
            new Thread(
                new RouteAlertTask(),
                "Route-Alert"
            );

        System.out.println(
            "\nStarting concurrent monitoring..."
        );

        binThread.start();

        routeThread.start();

        try {

            binThread.join();

            routeThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                "Main thread interrupted."
            );
        }

        monitoringRunning = false;

        System.out.println(
            "Monitoring cycle completed."
        );
    }

    // =========================================================
    // PROCESS WAITLIST
    // =========================================================

    static void processWaitlist() {

        synchronized (systemLock) {

            if (waitlist.isEmpty()) {

                System.out.println(
                    "No requests in waitlist."
                );

                return;
            }

            CollectionRequest request =
                waitlist.poll();

            if (request != null) {

                request.setStatus("PROCESSING");

                System.out.println(
                    "Processing waitlisted request:"
                );

                request.display();

                request.setStatus("COMPLETED");

                System.out.println(
                    "Waitlisted request completed."
                );

                systemLock.notifyAll();
            }
        }
    }

    // =========================================================
    // COLLECTION REPORT
    // =========================================================

    static void generateReport() {

        int pending = 0;
        int assigned = 0;
        int completed = 0;
        int cancelled = 0;
        int waitlisted = 0;

        for (CollectionRequest request : requests) {

            switch (request.getStatus()) {

                case "PENDING":
                    pending++;
                    break;

                case "ASSIGNED":
                    assigned++;
                    break;

                case "COMPLETED":
                    completed++;
                    break;

                case "CANCELLED":
                    cancelled++;
                    break;

                case "WAITLISTED":
                    waitlisted++;
                    break;

                default:
                    break;
            }
        }

        int criticalBins = 0;

        int warningBins = 0;

        for (WasteBin bin : bins) {

            if (bin.getFillLevel() >= 90) {

                criticalBins++;

            } else if (bin.getFillLevel() >= 80) {

                warningBins++;
            }
        }

        int availableVehicles = 0;

        for (Vehicle vehicle : vehicles) {

            if (vehicle.isAvailable()) {

                availableVehicles++;
            }
        }

        System.out.println(
            "\n=============================================="
        );

        System.out.println(
            "       COLLECTION & ROUTE EFFICIENCY REPORT"
        );

        System.out.println(
            "=============================================="
        );

        System.out.println(
            "Total Residents       : " +
            residents.size()
        );

        System.out.println(
            "Total Bins             : " +
            bins.size()
        );

        System.out.println(
            "Total Vehicles         : " +
            vehicles.size()
        );

        System.out.println(
            "Available Vehicles     : " +
            availableVehicles
        );

        System.out.println(
            "Total Routes           : " +
            routes.size()
        );

        System.out.println(
            "Total Requests         : " +
            requests.size()
        );

        System.out.println(
            "Pending Requests       : " +
            pending
        );

        System.out.println(
            "Assigned Requests      : " +
            assigned
        );

        System.out.println(
            "Completed Requests     : " +
            completed
        );

        System.out.println(
            "Cancelled Requests     : " +
            cancelled
        );

        System.out.println(
            "Waitlisted Requests    : " +
            waitlisted
        );

        System.out.println(
            "Critical Bins          : " +
            criticalBins
        );

        System.out.println(
            "Warning Bins            : " +
            warningBins
        );

        System.out.println(
            "Active Alerts          : " +
            alerts.size()
        );

        System.out.println(
            "=============================================="
        );
    }

    // =========================================================
    // DEMO DATA
    // =========================================================

    static void loadSampleData() {

        if (!residents.isEmpty()) {

            System.out.println(
                "Sample data is already loaded."
            );

            return;
        }

        Resident r1 =
            new Resident(
                101,
                "Arun Kumar",
                "Anna Nagar",
                "9876543210"
            );

        Resident r2 =
            new Resident(
                102,
                "Meena Devi",
                "Velachery",
                "9123456780"
            );

        residents.add(r1);
        residents.add(r2);

        registeredResidentIds.add(101);
        registeredResidentIds.add(102);

        residentMap.put(101, r1);
        residentMap.put(102, r2);

        WasteBin b1 =
            new WasteBin(
                1,
                "Anna Nagar Zone A",
                55
            );

        WasteBin b2 =
            new RecyclableBin(
                2,
                "Velachery Zone B",
                82
            );

        WasteBin b3 =
            new OrganicBin(
                3,
                "Anna Nagar Zone C",
                92
            );

        bins.add(b1);
        bins.add(b2);
        bins.add(b3);

        registeredBinIds.add(1);
        registeredBinIds.add(2);
        registeredBinIds.add(3);

        Vehicle v1 =
            new Vehicle(
                201,
                "Suresh",
                1000
            );

        Vehicle v2 =
            new RecyclingVehicle(
                202,
                "Karthik",
                800
            );

        Vehicle v3 =
            new OrganicVehicle(
                203,
                "Ramesh",
                900
            );

        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);

        vehicleTable.put(201, v1);
        vehicleTable.put(202, v2);
        vehicleTable.put(203, v3);

        Route route1 =
            new Route(
                301,
                "Route-A",
                "Anna Nagar",
                201
            );

        Route route2 =
            new Route(
                302,
                "Route-B",
                "Velachery",
                202
            );

        routes.add(route1);
        routes.add(route2);

        v1.setAvailable(false);
        v2.setAvailable(false);

        System.out.println(
            "\nSample data loaded successfully."
        );
    }

    // =========================================================
    // MENU
    // =========================================================

    static void showMenu() {

        System.out.println(
            "\n=============================================="
        );

        System.out.println(
            "       SMART WASTE MANAGEMENT SYSTEM"
        );

        System.out.println(
            "=============================================="
        );

        System.out.println(
            "1.  Register Resident"
        );

        System.out.println(
            "2.  Display Residents"
        );

        System.out.println(
            "3.  Search Resident"
        );

        System.out.println(
            "4.  Update Resident"
        );

        System.out.println(
            "5.  Add Waste Bin"
        );

        System.out.println(
            "6.  Display Bin Status"
        );

        System.out.println(
            "7.  Update Bin Fill Level"
        );

        System.out.println(
            "8.  Register Vehicle"
        );

        System.out.println(
            "9.  Display Vehicles"
        );

        System.out.println(
            "10. Add Route"
        );

        System.out.println(
            "11. Display Routes"
        );

        System.out.println(
            "12. Create Collection Request"
        );

        System.out.println(
            "13. Display Collection Requests"
        );

        System.out.println(
            "14. Assign Request to Vehicle"
        );

        System.out.println(
            "15. Cancel Collection Request"
        );

        System.out.println(
            "16. Add Request to Waitlist"
        );

        System.out.println(
            "17. Display Waitlist"
        );

        System.out.println(
            "18. Process Waitlist"
        );

        System.out.println(
            "19. Start Bin & Route Monitoring"
        );

        System.out.println(
            "20. Display Alerts"
        );

        System.out.println(
            "21. Generate Collection Report"
        );

        System.out.println(
            "22. Load Sample Data"
        );

        System.out.println(
            "0.  Exit"
        );

        System.out.println(
            "=============================================="
        );
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        System.out.println(
            "=============================================="
        );

        System.out.println(
            " SMART MUNICIPAL WASTE COLLECTION SYSTEM"
        );

        System.out.println(
            "=============================================="
        );

        boolean running = true;

        while (running) {

            showMenu();

            try {

                System.out.print(
                    "Enter your choice: "
                );

                int choice =
                    Integer.parseInt(
                        scanner.nextLine()
                    );

                switch (choice) {

                    case 1:
                        addResident();
                        break;

                    case 2:
                        displayResidents();
                        break;

                    case 3:
                        searchResident();
                        break;

                    case 4:
                        updateResident();
                        break;

                    case 5:
                        addBin();
                        break;

                    case 6:
                        displayBins();
                        break;

                    case 7:
                        updateBinFillLevel();
                        break;

                    case 8:
                        addVehicle();
                        break;

                    case 9:
                        displayVehicles();
                        break;

                    case 10:
                        addRoute();
                        break;

                    case 11:
                        displayRoutes();
                        break;

                    case 12:
                        createCollectionRequest();
                        break;

                    case 13:
                        displayRequests();
                        break;

                    case 14:
                        assignRequest();
                        break;

                    case 15:
                        cancelRequest();
                        break;

                    case 16:
                        addToWaitlist();
                        break;

                    case 17:
                        displayWaitlist();
                        break;

                    case 18:
                        processWaitlist();
                        break;

                    case 19:
                        startMonitoring();
                        break;

                    case 20:
                        displayAlerts();
                        break;

                    case 21:
                        generateReport();
                        break;

                    case 22:
                        loadSampleData();
                        break;

                    case 0:

                        running = false;

                        System.out.println(
                            "\nThank you for using Smart Waste Management System."
                        );

                        break;

                    default:

                        System.out.println(
                            "Invalid choice. Select 0-22."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                    "Invalid input. Please enter a number."
                );

            } catch (Exception e) {

                System.out.println(
                    "Unexpected error: " +
                    e.getMessage()
                );
            }
        }

        scanner.close();
    }
}