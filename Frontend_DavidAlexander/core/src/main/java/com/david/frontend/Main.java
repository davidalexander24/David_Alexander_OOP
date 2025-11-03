package com.david.frontend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.david.frontend.factories.ObstacleFactory;
import com.david.frontend.obstacles.BaseObstacle;
import com.david.frontend.obstacles.HomingMissile;

public class Main extends Game {
    private ShapeRenderer shapeRenderer;

    private Player player;
    private Ground ground;
    private GameManager gameManager;

    // TODO: Deklarasikan ObstacleFactory, obstacleSpawnTimer, dan konstanta-konstanta untuk spawning
    // - obstacleFactory (ObstacleFactory)
    // - obstacleSpawnTimer (float)
    // - lastObstacleSpawnX (float) dengan nilai awal 0f
    // - OBSTACLE_SPAWN_INTERVAL (static final float) dengan nilai 2.5f
    // - OBSTACLE_DENSITY (static final int) dengan nilai 1
    // - SPAWN_AHEAD_DISTANCE (static final float) dengan nilai 300f
    // - MIN_OBSTACLE_GAP (static final float) dengan nilai 200f
    // - OBSTACLE_CLUSTER_SPACING (static final float) dengan nilai 250f
    private ObstacleFactory obstacleFactory;
    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;
    private static final float OBSTACLE_SPAWN_INTERVAL = 2.5f;
    private static final int OBSTACLE_DENSITY = 1;
    private static final float SPAWN_AHEAD_DISTANCE = 300f;
    private static final float MIN_OBSTACLE_GAP = 200f;
    private static final float OBSTACLE_CLUSTER_SPACING = 250f;

    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;

    private int screenWidth;
    private int screenHeight;
    private int lastLoggedScore = -1;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        this.gameManager = GameManager.getInstance();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        player = new Player(new Vector2(100, screenHeight / 2f));
        ground = new Ground();

        // TODO: Inisialisasi obstacleFactory dan obstacleSpawnTimer
        obstacleFactory = new ObstacleFactory();
        obstacleSpawnTimer = 0f;

        gameManager.startGame();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        update(delta);
        // TODO: Panggil method renderGame(shapeRenderer)
        renderGame(shapeRenderer);
    }

    private void update(float delta) {
        boolean isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        // TODO: Tambahkan logika untuk restart game jika player mati dan menekan tombol SPACE
        // Jika player mati, panggil resetGame() dan hentikan eksekusi method update lebih lanjut (return)
        if (player.isDead()) {
            if (isFlying) {
                resetGame();
            }
            return;
        }

        player.update(delta, isFlying);
        updateCamera(delta);

        ground.update(camera.position.x);
        player.checkBoundaries(ground, screenHeight);

        // TODO: Panggil method untuk update dan check collision pada obstacles
        updateObstacles(delta);
        checkCollisions();

        int currentScoreMeters = (int) player.getDistanceTraveled();
        int previousScoreMeters = gameManager.getScore();

        if (currentScoreMeters > previousScoreMeters) {
            if (currentScoreMeters != lastLoggedScore) {
                System.out.println("Distance: " + currentScoreMeters + "m");
                lastLoggedScore = currentScoreMeters;
            }
            gameManager.setScore(currentScoreMeters);
        }
    }

    // TODO: Buatlah method private void renderGame(ShapeRenderer shapeRenderer)
    /*
     * - Bersihkan layar.
     * - Atur projection matrix shapeRenderer ke camera.combined.
     * - Mulai sesi ShapeRenderer dengan tipe Filled.
     * - Render ground dan player.
     * - Atur warna shapeRenderer menjadi MERAH.
     * - Loop semua obstacle yang aktif dari obstacleFactory, lalu render setiap obstacle.
     * - Akhiri sesi ShapeRenderer.
     */
    private void renderGame(ShapeRenderer shapeRenderer) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        ground.renderShape(shapeRenderer);
        player.renderShape(shapeRenderer);

        shapeRenderer.setColor(Color.RED);
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            obstacle.render(shapeRenderer);
        }

        shapeRenderer.end();
    }

    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + screenWidth * cameraOffset;
        camera.position.x = cameraFocus;
        camera.update();
    }

    // TODO: Buatlah method private void updateObstacles(float delta)
    /*
     * - Tambahkan delta ke obstacleSpawnTimer.
     * - Jika obstacleSpawnTimer melebihi OBSTACLE_SPAWN_INTERVAL, panggil spawnObstacle() dan reset timer.
     * - Dapatkan posisi tepi kiri kamera.
     * - Loop semua obstacle yang aktif:
     * - Jika obstacle adalah HomingMissile, set target-nya ke player dan panggil update(delta).
     * - Jika obstacle sudah di luar layar, lepaskan (release) obstacle tersebut.
     */
    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;

        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_INTERVAL) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }

        float cameraLeftEdge = camera.position.x - screenWidth / 2f;

        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle instanceof HomingMissile) {
                HomingMissile missile = (HomingMissile) obstacle;
                missile.setTarget(player);
                missile.update(delta);
            }

            if (obstacle.isOffScreenCamera(cameraLeftEdge)) {
                obstacleFactory.releaseObstacle(obstacle);
            }
        }
    }

    // TODO: Buatlah method private void spawnObstacle(). Method ini memastikan rintangan baru selalu muncul di depan kamera dan menjaga jarak minimum dari rintangan sebelumnya (clustering).
    /*
     * * 1. Pengecekan Posisi Spawn Dasar:
     * - Hitung tepi kanan kamera. Tepi kanan kamera adalah posisi kamera pada sumbu-x ditambah dengan setengah dari lebar layar.
     * - Hitung posisi spawn di depan kamera (float spawnAheadOfCamera): Perhitungannya adalah tepi kanan kamera + SPAWN_AHEAD_DISTANCE.
     * - Hitung posisi spawn setelah rintangan terakhir (float spawnAfterLastObstacle): Perhitungannya adalah lastObstacleSpawnX ditambah MIN_OBSTACLE_GAP.
     * * - Hitung posisi spawn dasar (float baseSpawnX): Pilih yang nilainya paling besar antara spawnAheadOfCamera dan spawnAfterLastObstacle.
     * (Ini memastikan spawn terjadi paling jauh di depan kamera DAN setelah gap minimum dari rintangan terakhir).
     * * 2. Pembuatan Rintangan (Clustering):
     * - Loop sebanyak OBSTACLE_DENSITY:
     * - Hitung posisi spawnX untuk setiap rintangan dalam satu cluster.
     * - Panggil obstacleFactory.createRandomObstacle(...) untuk membuat rintangan baru. Berikan ground.getTopY(), spawnX, dan player.getHeight() sebagai parameter.
     * - Perbarui lastObstacleSpawnX dengan nilai spawnX saat ini.
     */
    private void spawnObstacle() {
        float cameraRightEdge = camera.position.x + screenWidth / 2f;
        float spawnAheadOfCamera = cameraRightEdge + SPAWN_AHEAD_DISTANCE;
        float spawnAfterLastObstacle = lastObstacleSpawnX + MIN_OBSTACLE_GAP;
        float baseSpawnX = Math.max(spawnAheadOfCamera, spawnAfterLastObstacle);

        for (int i = 0; i < OBSTACLE_DENSITY; i++) {
            float spawnX = baseSpawnX + (i * OBSTACLE_CLUSTER_SPACING);
            obstacleFactory.createRandomObstacle(ground.getTopY(), spawnX, player.getHeight());
            lastObstacleSpawnX = spawnX;
        }
    }

    // TODO: Buatlah method private void checkCollisions()
    /*
     * - Dapatkan collider player.
     * - Loop semua obstacle yang aktif:
     * - Jika obstacle bertabrakan dengan player:
     * - Tampilkan pesan "GAME OVER" dan instruksi untuk restart.
     * - Panggil method untuk mematikan player.
     * - Hentikan eksekusi method.
     */
    private void checkCollisions() {
        Rectangle playerCollider = player.getCollider();

        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle.isColliding(playerCollider)) {
                System.out.println("GAME OVER! Press SPACE to restart.");
                player.die();
                return;
            }
        }
    }

    // TODO: Buatlah method private void resetGame()
    /*
     * - Panggil method untuk reset player.
     * - Lepaskan (release) semua obstacle melalui obstacleFactory.
     * - Reset obstacleSpawnTimer dan lastObstacleSpawnX.
     * - Reset posisi kamera.
     * - Reset skor di gameManager dan reset lastLoggedScore.
     * - Cetak pesan "Game reset!".
     */
    private void resetGame() {
        player.reset();
        obstacleFactory.releaseAllObstacles();
        obstacleSpawnTimer = 0f;
        lastObstacleSpawnX = 0f;
        camera.position.x = screenWidth / 2f;
        gameManager.setScore(0);
        lastLoggedScore = -1;
        System.out.println("Game reset!");
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        // TODO: Lepaskan (release) semua obstacle saat game ditutup.
        obstacleFactory.releaseAllObstacles();
    }
}
