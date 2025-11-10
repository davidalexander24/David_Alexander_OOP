package com.david.frontend; // Ganti dengan nama package kalian
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.david.frontend.commands.Command;
import com.david.frontend.commands.JetpackCommand;
import com.david.frontend.commands.RestartCommand;
import com.david.frontend.factories.ObstacleFactory;
import com.david.frontend.observers.ScoreUIObserver;
import com.david.frontend.obstacles.BaseObstacle;
import com.david.frontend.obstacles.HomingMissile;

public class Main extends Game {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    // Game objects
    private Player player;
    private Ground ground;
    private GameManager gameManager;

    // Background
    // TODO: Deklarasikan objek Background di sini
    private Background background;
    // TODO: Deklarasikan objek Command untuk jetpack dan restart
    private Command jetpackCommand;
    private Command restartCommand;
    // TODO: Deklarasikan objek ScoreUIObserver
    private ScoreUIObserver scoreUIObserver;
    // Obstacle spawning
    private ObstacleFactory obstacleFactory;
    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;
    private static final float OBSTACLE_SPAWN_INTERVAL = 2.5f;
    private static final int OBSTACLE_DENSITY = 1; // Number of obstacles per spawn
    // Nilai ini diubah agar konsisten dengan file utama
    private static final float SPAWN_AHEAD_DISTANCE = 300f; // Distance ahead of camera to spawn
    private static final float MIN_OBSTACLE_GAP = 200f; // Minimum gap between obstacle clusters
    private static final float OBSTACLE_CLUSTER_SPACING = 250f; // Spacing within a cluster
    // Camera system
    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;
    // Cached values for performance
    private int screenWidth;
    private int screenHeight;
    private int lastLoggedScore = -1;
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        this.gameManager = GameManager.getInstance();
        // Cache screen dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        // Initialize camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        player = new Player(new Vector2(100, screenHeight / 2f));
        ground = new Ground();
        // TODO: Inisialisasi objek jetpackCommand dan restartCommand
        jetpackCommand = new JetpackCommand(player);
        restartCommand = new RestartCommand(player, gameManager);
        // TODO: Inisialisasi objek scoreUIObserver
        scoreUIObserver = new ScoreUIObserver();
        // TODO: Daftarkan scoreUIObserver ke gameManager sebagai observer
        gameManager.addObserver(scoreUIObserver);
        // TODO: Inisialisasi objek background
        background = new Background();
        // Initialize obstacle factory
        obstacleFactory = new ObstacleFactory();
        obstacleSpawnTimer = 0f;
        gameManager.startGame();
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        update(delta);
        renderGame(shapeRenderer);
    }
    private void update(float delta) {
        // TODO: Panggil execute() pada jetpackCommand jika tombol SPACE ditekan
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jetpackCommand.execute();
        }
        // Check if player is dead and wants to restart
        if (player.isDead()) {
            // TODO: Panggil execute() pada restartCommand jika tombol SPACE baru saja ditekan
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                restartCommand.execute();
            }
            return;
        }
        player.update(delta, false); // We handle flying through commands now
        updateCamera(delta);
        // TODO: Update posisi background berdasarkan posisi kamera x
        background.update(camera.position.x);

        // Update ground position based on camera BEFORE checking boundaries
        ground.update(camera.position.x);
        player.checkBoundaries(ground, screenHeight);
        // Update obstacles
        updateObstacles(delta);
        // Check collisions
        checkCollisions();
        // Calculate distance-based score and log changes (print every meter)
        int currentScoreMeters = (int)player.getDistanceTraveled();
        int previousScoreMeters = gameManager.getScore();
        if (currentScoreMeters > previousScoreMeters) {
            // Only print if this is a new meter milestone
            if (currentScoreMeters != lastLoggedScore) {
                System.out.println("Distance: " + currentScoreMeters + "m");
                lastLoggedScore = currentScoreMeters;
            }
            gameManager.setScore(currentScoreMeters);
        }
    }
    private void renderGame(ShapeRenderer shapeRenderer) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        // TODO: Inisialisasi spriteBatch jika masih null
        if (spriteBatch == null) {
            spriteBatch = new SpriteBatch();
        }
        // TODO: Atur projection matrix untuk spriteBatch agar sesuai dengan kamera
        spriteBatch.setProjectionMatrix(camera.combined);
        // TODO: Gambar background menggunakan spriteBatch (di antara begin() dan end())
        spriteBatch.begin();
        background.render(spriteBatch);
        spriteBatch.end();
        // Render game objects using ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Render ground
        ground.renderShape(shapeRenderer);
        // Render player
        player.renderShape(shapeRenderer);
        // Render active obstacles (batched for performance)
        shapeRenderer.setColor(Color.RED);
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            obstacle.render(shapeRenderer);
        }
        shapeRenderer.end();
        // TODO: Render score UI menggunakan scoreUIObserver, gunakan skor dari gameManager
        scoreUIObserver.render(gameManager.getScore());
    }
    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + screenWidth * cameraOffset;
        camera.position.x = cameraFocus;
        camera.update();
    }
    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;
        // Spawn new obstacles at interval
        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_INTERVAL) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }
        // Cache camera edge for reuse
        float cameraLeftEdge = camera.position.x - screenWidth / 2f;
        // Update existing obstacles - use enhanced for-loop for better performance
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            // Update homing missiles to track player
            if (obstacle instanceof HomingMissile) {
                ((HomingMissile) obstacle).setTarget(player);
                ((HomingMissile) obstacle).update(delta);
            }
            // Release obstacles that are off-screen (behind camera)
            if (obstacle.isOffScreenCamera(cameraLeftEdge)) {
                obstacleFactory.releaseObstacle(obstacle);
            }
        }
    }
    private void spawnObstacle() {
        float cameraRightEdge = camera.position.x + screenWidth / 2f;
        float spawnAheadOfCamera = cameraRightEdge + SPAWN_AHEAD_DISTANCE;
        float spawnAfterLastObstacle = lastObstacleSpawnX + MIN_OBSTACLE_GAP;
        // Spawn far enough ahead AND maintain minimum gap from last obstacle
        float baseSpawnX = Math.max(spawnAheadOfCamera, spawnAfterLastObstacle);
        // Spawn multiple obstacles based on density
        for (int i = 0; i < OBSTACLE_DENSITY; i++) {
            float spawnX = baseSpawnX + (i * OBSTACLE_CLUSTER_SPACING);
            obstacleFactory.createRandomObstacle(ground.getTopY(), spawnX, player.getHeight());
            lastObstacleSpawnX = spawnX;
        }
    }
    private void checkCollisions() {
        Rectangle playerCollider = player.getCollider();
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle.isColliding(playerCollider)) {
                System.out.println("==============================================");
                System.out.println("GAME OVER!");
                System.out.println("Press SPACE to restart");
                System.out.println("==============================================");
                player.die();
                return;
            }
        }
    }
    // resetGame() tidak dipanggil dari dalam RestartCommand, jadi method ini
    // mungkin tidak digunakan jika logika reset sudah ada di dalam command.
    private void resetGame() {
        // Reset player
        player.reset();
        // Clear all obstacles
        obstacleFactory.releaseAllObstacles();
        // Reset timers
        obstacleSpawnTimer = 0f;
        lastObstacleSpawnX = 0f;
        // Reset camera
        camera.position.x = player.getPosition().x - screenWidth * cameraOffset;
        camera.update();
        // Reset score
        gameManager.setScore(0);
        lastLoggedScore = -1; // Reset logging tracker
        System.out.println("Game reset!");
    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        obstacleFactory.releaseAllObstacles();

        // TODO: Panggil dispose() pada scoreUIObserver
        scoreUIObserver.dispose();
        // TODO: Panggil dispose() pada background
        background.dispose();
    }
}
