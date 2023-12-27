package com.knapsack.core.test;

import com.knapsack.core.*;
import com.knapsack.core.entity.Entity;
import com.knapsack.core.entity.Model;
import com.knapsack.core.entity.Texture;
import com.knapsack.core.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;

public class TestGame implements ILogic {

    private static final float CAMERA_MOVE_SPEED = 0.015f;

    private int direction = 0;
    private float color  = 0.0f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private List<Entity> entities;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        //TODO: visualize pentominoes using 3D matrices
        //TODO: add three dimentional matrix to store all pentominoes
        //TODO: comment everything !!!!

        float[] vertices = new float[] {
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };

        float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7,
        };



        //rectangle 1x1x2

//        float[] vertices = new float[] {
//                -0.5f,  0.5f,  1.0f,  // Vertex 0
//                -0.5f, -0.5f,  1.0f,  // Vertex 1
//                0.5f, -0.5f,  1.0f,  // Vertex 2
//                0.5f,  0.5f,  1.0f,  // Vertex 3
//
//                -0.5f,  0.5f, -1.0f,  // Vertex 4
//                0.5f,  0.5f, -1.0f,  // Vertex 5
//                -0.5f, -0.5f, -1.0f,  // Vertex 6
//                0.5f, -0.5f, -1.0f   // Vertex 7
//        };
//        float[] textureCoords = new float[]{
//                0.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                1.0f, 0.0f,
//
//                0.0f, 0.0f,
//                1.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 1.0f
//        };
//
//        int[] indices = new int[]{
//                0, 1, 3, 3, 1, 2, // Front face
//                4, 5, 7, 7, 5, 6, // Back face
//                0, 1, 4, 4, 1, 5, // Left face
//                2, 3, 6, 6, 3, 7, // Right face
//                0, 2, 4, 4, 2, 6, // Top face
//                1, 3, 5, 5, 3, 7  // Bottom face
//        };


        Model model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/green.png")));

        entities = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            float x = i * 2;
            float y = 0;
            float z = -4;
            entities.add(new Entity(model, new Vector3f(x,y,z), new Vector3f(0,0,0), 1));
        }
//        entity = new Entity(model, new Vector3f(0,0,-4), new Vector3f(0,0,0), 1);
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

        // TODO: instead of moving horizontally moves vertically, fix that
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
//        entity.incrementRotation(0.0f, 0.1f, 0.0f);
    }

    @Override
    public void render() {
        if(window.isResize()){
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE); // Set polygon mode to draw only edges
        renderer.render(camera);
//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL); // Restore polygon mode to fill faces
    }



    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
