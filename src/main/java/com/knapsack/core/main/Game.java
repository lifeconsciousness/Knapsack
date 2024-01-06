package com.knapsack.core.main;

import com.knapsack.core.*;
import com.knapsack.core.entity.*;
import com.knapsack.core.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class Game implements ILogic {
    //TODO: visualize pentominoes using 3D matrices
    //TODO: add three dimentional matrix to store all pentominoes
    //TODO: comment everything !!!!

    private static final float CAMERA_MOVE_SPEED = 0.015f;

    private int direction = 0;
    private float color  = 0.0f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private static List<Entity> entities = new ArrayList<>();

    private Camera camera;
    Vector3f cameraInc;

    public static final int depth = 10;
    public static final int rows = 5;
    public static final int columns = 7;
    int searchDepth;
    private static int[][][] field = new int[depth][rows][columns];

    public Game(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        Model cubeModel = loader.loadModel(Cube.vertices, Cube.textureCoords, Cube.indices);

        //bottom
        entities.add(new Entity(cubeModel, new Vector3f(0,-100,0), new Vector3f(0,0,0), 100f, 0));

        // manipulating and displaying the field
        field = MatrixManipulation.emptyField(field);

        //render the matrix
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    // X AND Y ARE REVERTED TO HAVE A BETTER VISUAL REPRESENTATION OF ROWS AND COLUMNS (THE GO FROM TOP TO BOTTOM, LEFT TO RIGHT INSTEAD OF THE OTHER WAY AROUND)
                    float x = -k;
                    float y = -j;
                    float z = i;

                    entities.add(new Entity(cubeModel, new Vector3f(x,y,z), new Vector3f(0,0,0), 0.5f, field[i][j][k]));
                }
            }
        }

        OldAlgorithm oldAlgorithm = new OldAlgorithm();
        oldAlgorithm.init();
    }

    public static void addBlock(int[][][] block, int depth, int row, int column){
        MatrixManipulation.add(field, block, depth, row, column);
    }

    public void colorBlocks(){
        int counter = 1;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    entities.get(counter).setIndex(field[i][j][k]);
                    counter++;
                }
            }
        }
    }

    public static void emptyVisualization(){
        for(Entity entity : entities){
            entity.setIndex(-1);
        }
    }

    public static void emptyField(){
        MatrixManipulation.emptyField(field);
    }

    public static void emptyFieldAndVisualization(){
        emptyField();
        emptyVisualization();
    }

    public static int[][][] rotateX(int[][][] matrix){
        return MatrixManipulation.rotateX(matrix);
    }
    public static int[][][] rotateY(int[][][] matrix){
        return MatrixManipulation.rotateY(matrix);
    }
    public static int[][][] rotateZ(int[][][] matrix){
        return MatrixManipulation.rotateZ(matrix);
    }


    @Override
    public void input() {
        cameraInc.set(0,0,0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
            cameraInc.z = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
            cameraInc.z = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_A) || window.isKeyPressed(GLFW.GLFW_KEY_LEFT)){
            cameraInc.x = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_D) || window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)){
            cameraInc.x = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_Z) || window.isKeyPressed(GLFW.GLFW_KEY_DOWN)){
            cameraInc.y = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_X)  || window.isKeyPressed(GLFW.GLFW_KEY_UP)){
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);

        if(mouseInput.isLeftButtonPress()){
            Vector2f rotVector = mouseInput.getDisplayVector();
            camera.moveRotation(rotVector.x * Constants.MOUSE_SENSITIVITY, rotVector.y * Constants.MOUSE_SENSITIVITY, 0);
        }

        for(Entity entity : entities){
            renderer.processEntity(entity);
        }
    }

    @Override
    public void render() {
        emptyVisualization();

        if(window.isResize()){
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        colorBlocks();
//        colorRandomBlock();

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.render(camera);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }

    public void colorRandomBlock(){
        Random random = new Random();
        entities.get(random.nextInt(200)).setIndex(1);
        entities.get(random.nextInt(200)).setIndex(1);
    }
}
