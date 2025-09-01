//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Kendaraan kendaraanSupraBapak = new Kendaraan("Honda Supra", 1998, VehicleType.Motor, 3000);
        Kendaraan kendaraanKalcer = new Kendaraan("VW Beetle", 1998, VehicleType.Mobil, 200000);
        Kendaraan kendaraanGuede = new Kendaraan("Izuzu Giga", 2011, VehicleType.Truck, 300000);

        Customer customer1 = new Customer("Tommy", kendaraanSupraBapak);
        Customer customer2 = new Customer("David", kendaraanKalcer);
        Customer customer3 = new Customer("Bradley", kendaraanGuede);

        customer1.showDetail();
        customer2.showDetail();
        customer3.showDetail();

    }
}