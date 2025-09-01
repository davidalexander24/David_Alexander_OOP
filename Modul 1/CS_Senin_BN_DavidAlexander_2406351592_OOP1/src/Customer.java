public class Customer {
    private String nama;
    private Kendaraan kendaraan;

    public Customer(String nama, Kendaraan kendaraan) {
        this.nama = nama;
        this.kendaraan = kendaraan;
    }

    public double getTotalPrice(){
        return kendaraan.getHarga();
    }

    public void showDetail() {
        System.out.println("--------------------------------");
        System.out.println("Customer Name: " + nama);
        kendaraan.showDetail();
        System.out.println("--------------------------------");
    }
}
