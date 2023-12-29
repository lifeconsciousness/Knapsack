package com.knapsack.core;

import com.knapsack.core.entity.Entity;
import com.knapsack.core.entity.Model;
import com.knapsack.core.test.Launcher;
import com.knapsack.core.utils.Transformations;
import com.knapsack.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {
    private final WindowManager window;
    private ShaderManager shader;

    private Map<Model, List<Entity>> entities = new HashMap<>();

    public RenderManager(){
        window = Launcher.getWindow();
    }

    public void init() throws Exception{
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.link();
//        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
//        shader.createUniform("colorValue");
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

    public void prepare(Entity entity, Camera camera){
//        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformations.createTransformationMatrix(entity));
        shader.setUniform("viewMatrix", Transformations.getViewMatrix(camera));
//        shader.setUniform("colorValue", 2);
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
