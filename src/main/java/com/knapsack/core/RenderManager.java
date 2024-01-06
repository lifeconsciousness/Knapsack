package com.knapsack.core;

import com.knapsack.core.entity.Entity;
import com.knapsack.core.entity.Model;
import com.knapsack.core.main.Launcher;
import com.knapsack.core.utils.Transformations;
import com.knapsack.core.utils.Utils;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.*;

public class RenderManager {
    private final WindowManager window;
    private ShaderManager shader;
    Random random = new Random();


    private Map<Model, List<Entity>> entities = new HashMap<>();

    public RenderManager(){
        window = Launcher.getWindow();
    }

    public void init() throws Exception{
        shader = new ShaderManager();
        //if color mode load these, if texture mode load vertexTexture and fragmentTexture
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.link();

        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");

//        shader.createUniform("textureSampler");
        shader.createUniform("colorValue");
//        shader.createUniform("isOutlined");
    }

    public void bind(Model model){
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
    }

    public void unbind(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    int counter = 0;

    public void prepare(Entity entity, Camera camera){
//        shader.setUniform("textureSampler", 0);

        shader.setUniform("transformationMatrix", Transformations.createTransformationMatrix(entity));
        shader.setUniform("viewMatrix", Transformations.getViewMatrix(camera));

        //set color of a cube based on its index
        float index = entity.getIndex();
        if(index == -1){
            //white
            shader.setUniform("colorValue", new Vector4f(1.0f, 1.0f, 1.0f, 0.5f));
        } else{
            float fractionalPart = ((index % 1.0f) * 12);

            float brightnessFactor = 1.0f;

            Vector4f primaryColor;

            if (index >= 1 && index < 2) {
                // Interpolate between blue (0, 0, 1) and purple (0.5, 0, 1)
                primaryColor = new Vector4f(0, 0, 1.0f, 1.0f).lerp(new Vector4f(0.5f, 0, 1.0f, 1.0f), fractionalPart);
                // Apply brightness factor to primary color
                primaryColor.mul(brightnessFactor);
            } else if (index >= 2 && index < 3) {
                // Interpolate between green (0, 1, 0) and yellow (1, 1, 0)
                primaryColor = new Vector4f(0, 1.0f, 0, 1.0f).lerp(new Vector4f(1, 1, 0, 1.0f), fractionalPart);
                // Apply brightness factor to primary color
                primaryColor.mul(brightnessFactor);
            } else if (index >= 3 && index < 4) {
                // Interpolate between pink (1, 0, 1) and purple (1, 0, 0)
                primaryColor = new Vector4f(1, 0, 1, 1.0f).lerp(new Vector4f(1, 0, 0, 1.0f), fractionalPart);
                // Apply brightness factor to primary color
                primaryColor.mul(brightnessFactor);
            } else {
                // Handle other cases if needed
                primaryColor = new Vector4f(1, 1, 1, 1.0f); // Default color (white) for unknown ranges
                // Apply brightness factor to primary color
                primaryColor.mul(brightnessFactor);
            }

// You can use 'primaryColor' in your shader as needed
            shader.setUniform("colorValue", primaryColor);
        }
    }


    public void render(Camera camera){
        clear();

        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());

        for(Model model : entities.keySet()){
            bind(model);
            List<Entity> entityList = entities.get(model);

            for(Entity entity : entityList){
                prepare(entity, camera);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbind();
        }

        entities.clear();
        shader.unbind();
    }

    public void processEntity(Entity entity){
        List<Entity> entityList = entities.get(entity.getModel());

        if(entityList != null){
            entityList.add(entity);
        } else{
            List<Entity> newEntitityList = new ArrayList<>();
            newEntitityList.add(entity);
            entities.put(entity.getModel(), newEntitityList);
        }
    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup(){
        shader.cleanup();
    }
}
