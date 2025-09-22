import Model.Player;
import Model.Score;
import Repository.PlayerRepository;
import Repository.ScoreRepository;
import Service.PlayerService;
import Service.ScoreService;
public class Main {
    public static void main(String[] args) {
        PlayerRepository playerRepository = new
                PlayerRepository();
        ScoreRepository scoreRepository = new ScoreRepository();
        PlayerService playerService = new
                PlayerService(playerRepository);
        ScoreService scoreService = new
                ScoreService(scoreRepository, playerRepository, playerService);
        System.out.println("=== CS 4 ===\n");
        Player player1 = new Player("NanaBanana");
        Player player2 = new Player("Yingko");
        Player player3 = new Player("LegdontWork");
        // TODO: Gunakan playerService untuk menyimpan ketiga
        pemain di atas

        System.out.println("Players created\n");
        // TODO: Tampilkan semua pemain untuk melihat status awal
        mereka

        // TODO: Buat 5 objek Score baru dengan ketentuan berikut
        dan simpan menggunakan scoreService
        // Score 1: Untuk player1, score 1500, coins 50, distance
        3000
        // Score 2: Untuk player1, score 2000, coins 75, distance
        5
        4500
        // Score 3: Untuk player2, score 1800, coins 60, distance
        3500
        // Score 4: Untuk player3, score 1200, coins 40, distance
        2500
        // Score 5: Untuk player3, score 2500, coins 90, distance
        5000

        System.out.println("Scores created!\n");
        System.out.println("Player Score:");
        // TODO: Tampilkan detail semua pemain untuk membuktikan
        statistiknya ter-update

        System.out.println("Top 2 players by high score");
        // TODO: Tampilkan leaderboard top 2 pemain berdasarkan
        highscore melalui service

        System.out.println("All scores for " +
                player1.getUsername() + ":");
        // TODO: Tampilkan semua skor milik player1 melalui
        service

        System.out.println("Top 3 scores overall:");
        // TODO: Tampilkan leaderboard untuk top 3 skor
        keseluruhan melalui service
        System.out.println("earching for player 'NanaBanana':");
        // TODO: Cari pemain dengan username "NanaBanana" melalui
        service.
                // Jika ditemukan, tampilkan detailnya. Jika tidak, cetak
                "Player not found!".


                System.out.println("Totals for " + player3.getUsername() +
                        ":");
        // TODO: Dapatkan dan cetak total koin dan total jarak
        milik player3 melalui service.

        System.out.println("Recent scores (ordered by creation
                time):");
        // TODO: Dapatkan semua skor terurut berdasarkan waktu
        dibuatnya melalui service, lalu tampilkan detail setiap skor.
        6
    }
}