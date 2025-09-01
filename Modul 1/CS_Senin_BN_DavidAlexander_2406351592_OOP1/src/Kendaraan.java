public class Kendaraan {
    private String brand;
    private int year;
    private VehicleType type;
    private float harga;

    public Kendaraan(String brand, int year, VehicleType type, int harga) {
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.harga = harga;
    }

    public float getHarga() {
        return harga;
    }

    public void showDetail() {
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Type: " + type);
        System.out.println("Harga: " + harga);
    }
}
