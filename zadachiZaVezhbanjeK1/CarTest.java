package NP.zadachiZaVezhbanjeK1;

import java.util.*;
import java.util.stream.Collectors;

class Car{
    private String manufacturer;
    private String model;
    private int cena;
    private float power;

    public Car(String manufacturer, String model, int cena, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.cena = cena;
        this.power = power;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%dKW) %d",
                manufacturer, model, (int)power, cena);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getCena() {
        return cena;
    }

    public float getPower() {
        return power;
    }
}

class CarCollection{
    private Collection<Car> cars;

    public CarCollection() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void sortByPrice(boolean ascending){
        if(ascending){
            cars = cars.stream().sorted(Comparator.comparing(Car::getCena).thenComparing(Car::getPower)).collect(Collectors.toList());
        }
        else{
            cars = cars.stream().sorted(Comparator.comparing(Car::getCena).reversed().thenComparing(Comparator.comparing(Car::getPower).reversed())).collect(Collectors.toList());

        }
    }

    public List<Car> filterByManufacturer(String manufacturer){
        cars = cars.stream().filter(t->t.getManufacturer().toLowerCase().equals(manufacturer.toLowerCase())).collect(Collectors.toList());
        cars = cars.stream().sorted(Comparator.comparing(Car::getModel)).collect(Collectors.toList());
        return cars.stream().collect(Collectors.toList());
    }

    public List<Car> getList(){
        return cars.stream().collect(Collectors.toList());
    }
}

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if(parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}


// vashiot kod ovde