package com.david.frontend; // Ganti dengan nama package kalian
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    // TODO: Deklarasikan Attribute yang hanya dapat diakses di dalam class yang sama:
    // shapeRenderer (ShapeRenderer), player (Player), ground (Ground), gameManager (GameManager)
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Ground ground;
    private GameManager gameManager;

    // TODO: Deklarasikan Attribute Camera System yang hanya dapat diakses di dalam class yang sama:
    // camera (OrthographicCamera), cameraOffset (float) dengan nilai awal 0.2f
    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;

    @Override
    public void create() {
        // TODO: Inisialisasi shapeRenderer
        shapeRenderer = new ShapeRenderer();

        // TODO: Inisialisasi gameManager dengan mengambil instance tunggal
        gameManager = GameManager.getInstance();

        // TODO: Inisialisasi camera ortografis baru (OrthoGraphicCamera) dengan ukuran layar.
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.setToOrtho(false);

        // TODO: Inisialisasi player baru pada posisi awal (100,Gdx.graphics.getHeight() / 2f)
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f));

        // TODO: Inisialisasi ground baru
        ground = new Ground();
        ground.collider();

        // TODO: Panggil sebuah method pada gameManager untuk mulai game
        gameManager.startGame();
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Update game logic

        update(delta);

        // TODO: Render: Bersihkan layar dengan warna latar belakang background bebas
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.setProjectionMatrix(camera.combined);

        // TODO: Mulai sesi ShapeRenderer dengan tipe Filled
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // TODO: Panggil method pada ground dan player untuk memulai gambar
        ground.renderShape(shapeRenderer);
        player.renderShape(shapeRenderer);

        // TODO: Akhiri sesi ShapeRenderer
        shapeRenderer.end();

    }

    // TODO: Buatlah Method update(float delta) yang hanya dapat diakses di dalam class yang sama:
     /*
     * - Membuat variabel isFlying yang bernilai true saat tombol SPACE
    ditekan pada frame tersebut dan digunakan untuk memberitahu Player agar
    mengaktifkan mekanik terbang.
     * - Meng-update player, camera, dan ground
     * - Memastikan player selalu mengecek batas
     * - Hitung dan perbarui skor/jarak jika ada perubahan jarak
     */
    private void update(float delta) {
        boolean isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        player.update(delta, isFlying);
        updateCamera(delta);
        ground.update(camera.position.x);

        player.checkBoundaries(ground, Gdx.graphics.getHeight());

        int currentScore = (int) player.getDistanceTraveled();
        gameManager.setScore(currentScore);
    }

    // TODO: Buatlah Method updateCamera(float delta) yang hanya dapat diakses di dalam class yang sama:
    /*
    * - Hitung cameraFocus berdasarkan posisi player dan cameraOffset.
   cameraFocus berfungsi sebagai titik fokus sumbu x pada camera.
    * - Atur posisi sumbu-x camera agar sama dengan cameraFocus
    * - Meng-update camera
    */
    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + Gdx.graphics.getWidth() * cameraOffset;
        camera.position.x = cameraFocus;
        camera.update();
    }

    @Override
    public void dispose() {
        // TODO: Membersihkan resource pada shapeRenderer
        shapeRenderer.dispose();
    }
}
