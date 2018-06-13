package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import static com.jme3.shader.VarType.Vector3;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import java.util.Random;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private Geometry last = null;
    private Geometry last1 = null;
    private int[][] m = new int[13][13];
    
    private Node playerNode;
    private AnimControl animControl;
    private AnimChannel animChannel;
    boolean walkLeft, walkRight, walkUp, walkDown, dropBomb, restart, specialK;
    
    private Node playerNode1;
    private AnimControl animControl1;
    private AnimChannel animChannel1;
    boolean walkLeft1, walkRight1, walkUp1, walkDown1, dropBomb1, specialK1;
    
    Vector3f pos;
    Vector3f pos1;
    float count = 0;
    int otto_pos_i = 0;
    int otto_pos_j = 1;
    int count_eff = 0;
    boolean special = false;
    boolean special_on = false;
    int counta=0;
    
    
    Node aux_rootNode = new Node("auxiliar");
    
    float count1 = 0;
    int otto_pos_i1 = 12;
    int otto_pos_j1 = 11;
    boolean special1 = false;
    boolean special_on1 = false;
    
    
    //time variables
    long totalTime;
    long totalTime1;
    long totalTime2;
    long totalTime3;
    long totalTime4;
    long totalTime5;
    long totalTime6;
    long totalTime7;
    long currentTime;
    long currentTime1;
    long currentTime2;
    long currentTime3;
    long currentTime4;
    long currentTime5;
    long currentTime6;
    long currentTime7;
    long TWO_SEC = 2000;
    
    int count_box = 0;
    int au = 0;
    int winner=0;
    int winner1=0;
    int win=0;
    
    
    //bomb variables
    int bomb_on_field = 0; 
    int effects_on_field = 0;
    int bomb_i;
    int bomb_j;
    
    //bomb variables
    int bomb_on_field1 = 0; 
    int effects_on_field1 = 0;
    int bomb_i1;
    int bomb_j1;

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        //AppSettings a = new AppSettings(true);
        //a.setFullscreen(true);
        //app.setSettings(a);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        flyCam.setEnabled(paused);
           
        playerOne();
        playerTwo();
        timeSetup();
        
        /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.1f, -0.1f, -0.1f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);  
        
       
        //Filling matrix.
         for(int i = 0; i <= 12; i++)
            for(int j =0; j <= 12; j++){
                
                if(i % 2 == 0 && j % 2 == 0)
                {
                    m[i][j] = 1;
                }
                
            }
            
       
         //Create the field
        Box boxMesh = new Box(40f,40f,3f); 
        Geometry boxGeo = new Geometry("A Textured Box", boxMesh); 
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
        Texture monkeyTex = assetManager.loadTexture("Interface/grass.jpg"); 
        boxMat.setTexture("ColorMap", monkeyTex); 
        boxGeo.setMaterial(boxMat); 

        rootNode.attachChild(boxGeo); 
        rootNode.setLocalTranslation(0, 0, -100);
        rootNode.rotate(-FastMath.PI/16, 0, 0);
        
        
        //Create the fixed blocks...
        for(int i = 0; i <= 12; i++){
            for(int j = 0; j <= 12; j++){
                
                if(m[i][j] == 1){
                
                    Box boxMesh1 = new Box(3f,3f,4f);
                    Geometry boxGeo1 = new Geometry("box" + i + j, boxMesh1); 
                    Material boxMat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
                    Texture monkeyTex1 = assetManager.loadTexture("Interface/parede.jpg"); 
                    boxMat1.setTexture("ColorMap", monkeyTex1); 
                    boxGeo1.setMaterial(boxMat1); 
                    
                    if(i % 2 == 0 && j == 0){
                        
                        boxGeo1.setLocalTranslation(-37, 37 - count, 5);
                        last = boxGeo1;
                        last1 = boxGeo1;
                        rootNode.attachChild(boxGeo1);
                        
                    }
                    
                    else{
                        
                        pos = last.getLocalTranslation();
                        boxGeo1.setLocalTranslation(pos.x + 6.17f, pos.y, pos.z);
                        rootNode.attachChild(boxGeo1); 
                       
                        last = boxGeo1;
                        last1 = boxGeo1;
                    }
                }
                
                else{
                    
                    Box boxMesh1 = new Box(3f,3f,4f);
                    Geometry boxGeo1 = new Geometry("box" + i + j, boxMesh1); 
                    Material boxMat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
                    Texture monkeyTex1 = assetManager.loadTexture("Interface/stone.jpg"); 
                    boxMat1.setTexture("ColorMap", monkeyTex1); 
                    boxGeo1.setMaterial(boxMat1);
                    
                    Box boxMesh2 = new Box(1.6f,1.6f,1.6f);
                    Geometry boxGeo2 = new Geometry("bonus" + i + j, boxMesh2); 
                    Material boxMat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
                    Texture monkeyTex2 = assetManager.loadTexture("Textures/estrela.jpg"); 
                    boxMat2.setTexture("ColorMap", monkeyTex2); 
                    boxGeo2.setMaterial(boxMat2);
                    
                     if(i % 2 != 0 && j == 0){
                        
                        boxGeo1.setLocalTranslation(-37, 37 - count, 5);
                        boxGeo2.setLocalTranslation(-37, 37 - count, 5);
                        last = boxGeo1;
                        last1 = boxGeo2;
                        //rootNode.attachChild(boxGeo1);
                        aux_rootNode.attachChild(boxGeo1);
                        aux_rootNode.attachChild(boxGeo2);
                    }
                    
                     else{
                        pos = last.getLocalTranslation();
                        pos1 = last1.getLocalTranslation();
                        boxGeo1.setLocalTranslation(pos.x + 6.17f, pos.y, pos.z);
                        boxGeo2.setLocalTranslation(pos1.x + 6.17f, pos1.y, pos1.z);
                        //rootNode.attachChild(boxGeo1); 
                        aux_rootNode.attachChild(boxGeo1);
                        aux_rootNode.attachChild(boxGeo2);

                        last = boxGeo1; 
                        last1 = boxGeo2;
                    }
                }
            }
            
            count += 6.17f;
        }
        
        
         initKeys();
    }
    private void initKeys() {
        
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("DropBomb", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Special", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Restart", new KeyTrigger(KeyInput.KEY_R));

        String[] walkKeys = new String[]{"Up", "Down", "Left", "Right", "DropBomb", "Restart", "Special"};
        inputManager.addListener(waklListenerAnalog, walkKeys);
        inputManager.addListener(walkListenerAction, walkKeys);
        
        inputManager.addMapping("Up1", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Down1", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Left1", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right1", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("DropBomb1", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Special1", new KeyTrigger(KeyInput.KEY_L));

        String[] walkKeys1 = new String[]{"Up1", "Down1", "Left1", "Right1", "DropBomb1", "Special1"};
        inputManager.addListener(waklListenerAnalog1, walkKeys1);
        inputManager.addListener(walkListenerAction1, walkKeys1);

    }
    
    private AnalogListener waklListenerAnalog = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            
            Quaternion q1 = new Quaternion();
            Vector3f pos;

            if (walkUp || walkDown || walkLeft || walkRight || dropBomb || restart || specialK) {

                if (walkUp) {
                    pos = playerNode.getLocalTranslation();
                    
                    if(otto_pos_i - 1 >= 0 && otto_pos_i - 1 <= 12)
                    {
                        if(m[otto_pos_i - 1][otto_pos_j] == 0 || m[otto_pos_i - 1][otto_pos_j] == 5){
                           pos.y = pos.y + 6.0f;
                           playerNode.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkUp = false;
                           q1.fromAngles(20,0,60);
                           playerNode.setLocalRotation(q1);
                           
                           if(m[otto_pos_i - 1][otto_pos_j] == 5){
                               special = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i - 1) + otto_pos_j);
                               
                           }
                           
                           otto_pos_i--;
                        }
                    }
                }
                
                if (walkDown) {
                   pos = playerNode.getLocalTranslation();
                   
                   if(otto_pos_i + 1 >= 0 && otto_pos_i + 1 <= 12)
                    {
                        if(m[otto_pos_i + 1][otto_pos_j] == 0 || m[otto_pos_i + 1][otto_pos_j] == 5){
                           pos.y = pos.y - 6.0f;
                           playerNode.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkDown = false;
                           q1.fromAngles(20,0,0);
                           playerNode.setLocalRotation(q1);
                           
                           if(m[otto_pos_i + 1][otto_pos_j] == 5){
                               special = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i + 1) + otto_pos_j);
                               
                           }
                           
                           otto_pos_i++;
                        }
                    }
                }
                
                if (walkLeft) {
                    pos = playerNode.getLocalTranslation();
                    
                    if(otto_pos_j - 1 >= 0 && otto_pos_j - 1 <= 12)
                    {
                        if(m[otto_pos_i][otto_pos_j - 1] == 0  || m[otto_pos_i][otto_pos_j - 1] == 5){
                           pos.x = pos.x - 6.0f;
                           playerNode.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkLeft = false;
                           q1.fromAngles(20,0,300);
                           playerNode.setLocalRotation(q1);
                           
                           if(m[otto_pos_i][otto_pos_j - 1] == 5){
                               special = true;
                               rootNode.detachChildNamed("bonus"+ otto_pos_i + (otto_pos_j - 1));
                           }
                           
                           otto_pos_j--;
                        }
                    }
                }
                
                if (walkRight) {
                    pos = playerNode.getLocalTranslation();
                    
                    if(otto_pos_j + 1 >= 0 && otto_pos_j + 1 <= 12)
                    {
                        if(m[otto_pos_i][otto_pos_j + 1] == 0 || m[otto_pos_i][otto_pos_j + 1] == 5){
                           pos.x = pos.x + 6.0f;
                           playerNode.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkRight = false;
                           q1.fromAngles(20,0,360);
                           playerNode.setLocalRotation(q1);
                           
                           if(m[otto_pos_i][otto_pos_j + 1] == 5){
                               special = true;
                               rootNode.detachChildNamed("bonus"+ otto_pos_i + (otto_pos_j + 1));
                           }
                           
                           otto_pos_j++;
                        }
                    }
                }
                
                if(dropBomb && bomb_on_field == 0){
                    createBomb();
                    bomb_i = otto_pos_i;
                    bomb_j = otto_pos_j;
                    dropBomb = false;
                }
                
                if(restart)
                    restartGame();
                
                if(specialK && special){
                    createBomb();
                    bomb_i = otto_pos_i;
                    bomb_j = otto_pos_j;
                    dropBomb = false;
                    special_on = true;
                }
            } 
            
            walkUp = false;
            walkDown = false;
            walkLeft = false;
            walkRight = false;
            dropBomb = false;
            restart = false;
            specialK = false;
        }
    };
    private ActionListener walkListenerAction = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            switch (name) {
                case "Up":
                    walkUp = keyPressed;
                    break;
                case "Down":
                    walkDown = keyPressed;
                    break;
                case "Left":
                    walkLeft = keyPressed;
                    break;
                case "Right":
                    walkRight = keyPressed;
                    break;
                case "DropBomb":
                    dropBomb = keyPressed;
                    break;
                case "Restart":
                    restart = keyPressed;
                    break;
                case "Special":
                    specialK = keyPressed;
                    break;
            }
        }
    };
    
    
    private AnalogListener waklListenerAnalog1 = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            
            Quaternion q1 = new Quaternion();
            Vector3f pos;

            if (walkUp1 || walkDown1 || walkLeft1 || walkRight1 || dropBomb1 || specialK1) {

                if (walkUp1) {
                    pos = playerNode1.getLocalTranslation();
                    
                    if(otto_pos_i1 - 1 >= 0 && otto_pos_i1 - 1 <= 12)
                    {
                        if(m[otto_pos_i1 - 1][otto_pos_j1] == 0 || m[otto_pos_i1 - 1][otto_pos_j1] == 5){
                           pos.y = pos.y + 6.0f;
                           playerNode1.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkUp1 = false;
                           q1.fromAngles(20,0,60);
                           playerNode1.setLocalRotation(q1);
                           
                           if(m[otto_pos_i1 - 1][otto_pos_j1] == 5){
                               special1 = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i1 - 1) + otto_pos_j1);
                           }
                           
                           
                           otto_pos_i1--;
                        }
                    }
                }
                
                if (walkDown1) {
                   pos = playerNode1.getLocalTranslation();
                   
                   if(otto_pos_i1 + 1 >= 0 && otto_pos_i1 + 1 <= 12)
                    {
                        if(m[otto_pos_i1 + 1][otto_pos_j1] == 0  || m[otto_pos_i1 + 1][otto_pos_j1] == 5){
                           pos.y = pos.y - 6.0f;
                           playerNode1.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkDown1 = false;
                           q1.fromAngles(20,0,0);
                           playerNode1.setLocalRotation(q1);
                           
                           if(m[otto_pos_i1 + 1][otto_pos_j1] == 5){
                               special1 = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i1 + 1) + otto_pos_j1);
                           }
                           
                           
                           otto_pos_i1++;
                        }
                    }
                }
                
                if (walkLeft1) {
                    pos = playerNode1.getLocalTranslation();
                    
                    if(otto_pos_j1 - 1 >= 0 && otto_pos_j1 - 1 <= 12)
                    {
                        if(m[otto_pos_i1][otto_pos_j1 - 1] == 0  || m[otto_pos_i1][otto_pos_j1 - 1] == 5){
                           pos.x = pos.x - 6.0f;
                           playerNode1.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkLeft1 = false;
                           q1.fromAngles(20,0,300);
                           playerNode1.setLocalRotation(q1);
                           
                           if(m[otto_pos_i1][otto_pos_j1 - 1] == 5){
                               special1 = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i1) + (otto_pos_j1 - 1));
                           }
                           
                           
                           otto_pos_j1--;
                        }
                    }
                }
                
                if (walkRight1) {
                    pos = playerNode1.getLocalTranslation();
                    
                    if(otto_pos_j1 + 1 >= 0 && otto_pos_j1 + 1 <= 12)
                    {
                        if(m[otto_pos_i1][otto_pos_j1 + 1] == 0 || m[otto_pos_i1][otto_pos_j1 + 1] == 5){
                           pos.x = pos.x + 6.0f;
                           playerNode1.setLocalTranslation(pos.x, pos.y, pos.z);
                           walkRight1 = false;
                           q1.fromAngles(20,0,360);
                           playerNode1.setLocalRotation(q1);
                           
                           if(m[otto_pos_i1][otto_pos_j1 + 1] == 5){
                               special1 = true;
                               rootNode.detachChildNamed("bonus"+ (otto_pos_i1) + (otto_pos_j1 + 1));
                           }
                           
                           otto_pos_j1++;
                        }
                    }
                }
                
                if(dropBomb1 && bomb_on_field1 == 0){
                    createBomb1();
                    bomb_i1 = otto_pos_i1;
                    bomb_j1 = otto_pos_j1;
                    dropBomb1 = false;
                }
                
                if(specialK1 && special1){
                    
                    createBomb1();
                    bomb_i1 = otto_pos_i1;
                    bomb_j1 = otto_pos_j1;
                    dropBomb1 = false;
                    special_on1 = true;
                    
                }
            } 
            
            walkUp1 = false;
            walkDown1 = false;
            walkLeft1 = false;
            walkRight1 = false;
            dropBomb1 = false;
            specialK1 = false;
        }
    };
    private ActionListener walkListenerAction1 = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            switch (name) {
                case "Up1":
                    walkUp1 = keyPressed;
                    break;
                case "Down1":
                    walkDown1 = keyPressed;
                    break;
                case "Left1":
                    walkLeft1 = keyPressed;
                    break;
                case "Right1":
                    walkRight1 = keyPressed;
                    break;
                case "DropBomb1":
                    dropBomb1 = keyPressed;
                    break;
                case "Special1":
                    specialK1 = keyPressed;
                    break;
            }
        }
    };
    
    
    public void createBomb(){
        
        Vector3f pos;
        
        if(bomb_on_field == 0 && effects_on_field == 0){
            
            bomb_on_field = 1;
            
            totalTime1 = System.currentTimeMillis();
            totalTime4 = System.currentTimeMillis();
            pos = playerNode.getLocalTranslation();
            m[otto_pos_i][otto_pos_j] = 3;
            
            
            Sphere sphereMesh = new Sphere(32,32, 2f);
            Geometry shinyGeo = new Geometry("bomb_p1", sphereMesh);
            Material shinyMat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
            shinyMat.setTexture("NormalMap",  assetManager.loadTexture("Textures/madeira.jpg"));
            //shinyMat.setTexture("GlowMap", assetManager.loadTexture("Textures/glowmap.png")); // requires glow filter!
            shinyMat.setBoolean("UseMaterialColors",true);  // needed for shininess
            shinyMat.setColor("Specular", ColorRGBA.White); // needed for shininess
            shinyMat.setColor("Diffuse",  ColorRGBA.White); // needed for shininess
            shinyMat.setFloat("Shininess", 5f); // shininess from 1-128
            shinyGeo.setMaterial(shinyMat);

            shinyGeo.setLocalTranslation(pos);
            rootNode.attachChild(shinyGeo);
        }
     
    }
    public void explodeBomb(){
        
        currentTime1 = System.currentTimeMillis();
        
        if (currentTime1 - totalTime1 >= 800) {
            
            Vector3f pos = (rootNode.getChild("bomb_p1").getLocalTranslation());
            rootNode.detachChild(rootNode.getChild("bomb_p1"));
            m[bomb_i][bomb_j] = 0;
            bomb_on_field = 0;
            effects_on_field = 1;
            
            effectsBomb(pos.x, pos.y, pos.z, 0);
            effectsBomb(pos.x + 6, pos.y, pos.z, 0);
            effectsBomb(pos.x - 6, pos.y, pos.z, 0);
            effectsBomb(pos.x, pos.y + 6, pos.z, 0);
            effectsBomb(pos.x, pos.y - 6, pos.z, 0);
            
            if(special_on == true)
            {
                effectsBomb(pos.x + 12, pos.y, pos.z, 0);
                effectsBomb(pos.x - 12, pos.y, pos.z, 0);
                effectsBomb(pos.x, pos.y + 12, pos.z, 0);
                effectsBomb(pos.x, pos.y - 12, pos.z, 0);
                effectsBomb(pos.x + 18, pos.y, pos.z, 0);
                effectsBomb(pos.x - 18, pos.y, pos.z, 0);
                effectsBomb(pos.x, pos.y + 18, pos.z, 0);
                effectsBomb(pos.x, pos.y - 18, pos.z, 0);
                effectsBomb(pos.x + 24, pos.y, pos.z, 0);
                effectsBomb(pos.x - 24, pos.y, pos.z, 0);
                effectsBomb(pos.x, pos.y + 24, pos.z, 0);
                effectsBomb(pos.x, pos.y - 24, pos.z, 0);
            }
            
            totalTime1 = currentTime1;
        }  
        
        
    }
    public void createBomb1(){
        
        Vector3f pos;
        
        if(bomb_on_field1 == 0 && effects_on_field1 == 0){
            
            bomb_on_field1 = 1;
            
            totalTime3 = System.currentTimeMillis();
            totalTime5 = System.currentTimeMillis();
            pos = playerNode1.getLocalTranslation();
            m[otto_pos_i1][otto_pos_j1] = 3;
            
            
            Sphere sphereMesh = new Sphere(32,32, 2f);
            Geometry shinyGeo = new Geometry("bomb_p2", sphereMesh);
            Material shinyMat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
            shinyMat.setTexture("NormalMap",  assetManager.loadTexture("Textures/madeira.jpg"));
            //shinyMat.setTexture("GlowMap", assetManager.loadTexture("Textures/glowmap.png")); // requires glow filter!
            shinyMat.setBoolean("UseMaterialColors",true);  // needed for shininess
            shinyMat.setColor("Specular", ColorRGBA.White); // needed for shininess
            shinyMat.setColor("Diffuse",  ColorRGBA.White); // needed for shininess
            shinyMat.setFloat("Shininess", 5f); // shininess from 1-128
            shinyGeo.setMaterial(shinyMat);

            shinyGeo.setLocalTranslation(pos);
            rootNode.attachChild(shinyGeo);
        }
        
    }
    public void explodeBomb1(){
        
        currentTime3 = System.currentTimeMillis();
        
        if (currentTime3 - totalTime3 >= 800) {
            
            Vector3f pos = (rootNode.getChild("bomb_p2").getLocalTranslation());
            rootNode.detachChild(rootNode.getChild("bomb_p2"));
            m[bomb_i1][bomb_j1] = 0;
            bomb_on_field1 = 0;
            effects_on_field1 = 1;
           
            effectsBomb(pos.x, pos.y, pos.z, 1);
            effectsBomb(pos.x + 6, pos.y, pos.z, 1);
            effectsBomb(pos.x - 6, pos.y, pos.z, 1);
            effectsBomb(pos.x, pos.y + 6, pos.z, 1);
            effectsBomb(pos.x, pos.y - 6, pos.z, 1);
                    
            if(special_on1 == true)
            {
                effectsBomb(pos.x + 12, pos.y, pos.z, 1);
                effectsBomb(pos.x - 12, pos.y, pos.z, 1);
                effectsBomb(pos.x, pos.y + 12, pos.z, 1);
                effectsBomb(pos.x, pos.y - 12, pos.z, 1);
                effectsBomb(pos.x + 18, pos.y, pos.z, 1);
                effectsBomb(pos.x - 18, pos.y, pos.z, 1);
                effectsBomb(pos.x, pos.y + 18, pos.z, 1);
                effectsBomb(pos.x, pos.y - 18, pos.z, 1);
                effectsBomb(pos.x + 24, pos.y, pos.z, 1);
                effectsBomb(pos.x - 24, pos.y, pos.z, 1);
                effectsBomb(pos.x, pos.y + 24, pos.z, 1);
                effectsBomb(pos.x, pos.y - 24, pos.z, 1);
            }
            
           totalTime3 = currentTime3; 
        }

        
          
    }
    
    
    public void createBox(){
        
        Random r = new Random();
        int xpos = r.nextInt(13);
        int ypos = r.nextInt(13);
        
        if(!(xpos < 3 && ypos < 3)) 
            if(!(xpos > 9 && ypos > 9)){
                
                if(m[xpos][ypos] == 0){

                    if(aux_rootNode.getChild("box" + xpos + ypos) != null){
                        rootNode.attachChild(aux_rootNode.getChild("box" + xpos + ypos));
                        m[xpos][ypos] = 2;
                    }
                }
        }
    }
    public void destroyBox(int bomb_i, int bomb_j){
        
        if(special_on || special_on1){
            
            for(int i = 1; i < 5; i++)
            {
                if((bomb_i + i) >= 0 && (bomb_i + i) <= 12)
                if(m[bomb_i + i][bomb_j] == 2){
                    rootNode.detachChildNamed("box" + (bomb_i + i) + bomb_j);
                    m[bomb_i + i][bomb_j] = 0;
                    counta++;
                }
            
            if((bomb_i - i) >= 0 && (bomb_i - i) <= 12)
                if(m[bomb_i - i][bomb_j] == 2){
                    rootNode.detachChildNamed("box" + (bomb_i - i) + bomb_j);
                    m[bomb_i - i][bomb_j] = 0;
                    counta++;
                }
            
            
            if((bomb_j + i) >= 0 && (bomb_j + i) <= 12)
                if(m[bomb_i][bomb_j + i] == 2){
                    rootNode.detachChildNamed("box" + bomb_i + (bomb_j + i));
                    m[bomb_i][bomb_j + i] = 0;
                    counta++;
                }
            
            if((bomb_j - i) >= 0 && (bomb_j - i) <= 12)
                if(m[bomb_i][bomb_j - i ] == 2){
                    rootNode.detachChildNamed("box" + bomb_i + (bomb_j - i));
                    m[bomb_i][bomb_j - i] = 0;
                    counta++;
                } 
            }
        }
        
        
        else{
        if((bomb_i + 1) >= 0 && (bomb_i + 1) <= 12)
                if(m[bomb_i + 1][bomb_j] == 2){
                    rootNode.detachChildNamed("box" + (bomb_i + 1) + bomb_j);
                    m[bomb_i + 1][bomb_j] = 0;
                    counta++;
                }
            
            if((bomb_i - 1) >= 0 && (bomb_i - 1) <= 12)
                if(m[bomb_i - 1][bomb_j] == 2){
                    rootNode.detachChildNamed("box" + (bomb_i - 1) + bomb_j);
                    m[bomb_i - 1][bomb_j] = 0;
                    counta++;
                }
            
            
            if((bomb_j + 1) >= 0 && (bomb_j + 1) <= 12)
                if(m[bomb_i][bomb_j + 1] == 2){
                    rootNode.detachChildNamed("box" + bomb_i + (bomb_j + 1));
                    m[bomb_i][bomb_j + 1] = 0;
                    counta++;
                }
            
            if((bomb_j - 1) >= 0 && (bomb_j - 1) <= 12)
                if(m[bomb_i][bomb_j - 1 ] == 2){
                    rootNode.detachChildNamed("box" + bomb_i + (bomb_j - 1));
                    m[bomb_i][bomb_j - 1] = 0;
                    counta++;
                } 
            }
        
    }
    public void killPlayer(int bomb_i, int bomb_j){
        
        if(special_on || special_on1){
            for(int i = 1; i < 5; i++){
            if((bomb_i == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                    (bomb_i + i == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                    (bomb_i - i == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                    (bomb_i == otto_pos_i1 && bomb_j + i  == otto_pos_j1) ||
                    (bomb_i == otto_pos_i1 && bomb_j - i == otto_pos_j1)){               
                    rootNode.detachChild(playerNode1);
                    winner = 1;
                    win = 1;
                    textInput(0);
                }
            }

            for(int i = 1; i < 5; i++){
            if((bomb_i == otto_pos_i && bomb_j == otto_pos_j) ||
                    (bomb_i + i == otto_pos_i && bomb_j == otto_pos_j) ||
                    (bomb_i - i == otto_pos_i && bomb_j == otto_pos_j) ||
                    (bomb_i == otto_pos_i && bomb_j + i  == otto_pos_j) ||
                    (bomb_i == otto_pos_i && bomb_j - i == otto_pos_j)){               
                    rootNode.detachChild(playerNode);  
                    winner1 = 1;
                    win = 2;
                    textInput(0);
                }
            }
        }
        
        
         if((bomb_i == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                (bomb_i + 1 == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                (bomb_i - 1 == otto_pos_i1 && bomb_j == otto_pos_j1) ||
                (bomb_i == otto_pos_i1 && bomb_j + 1  == otto_pos_j1) ||
                (bomb_i == otto_pos_i1 && bomb_j - 1 == otto_pos_j1)){               
                rootNode.detachChild(playerNode1);
                winner = 1;
                win = 1;
                textInput(0);
            }
         
          if((bomb_i == otto_pos_i && bomb_j == otto_pos_j) ||
                (bomb_i + 1 == otto_pos_i && bomb_j == otto_pos_j) ||
                (bomb_i - 1 == otto_pos_i && bomb_j == otto_pos_j) ||
                (bomb_i == otto_pos_i && bomb_j + 1  == otto_pos_j) ||
                (bomb_i == otto_pos_i && bomb_j - 1 == otto_pos_j)){               
                rootNode.detachChild(playerNode);  
                winner1 = 1;
                win = 2;
                textInput(0);
            }
          
            
          if(winner == 1 && winner1 == 1){
             
              textInput(1);
          }
          
    }  
    public void effectsBomb(float x, float y, float z, int aux){
        
        /** Explosion effect. Uses Texture from jme3-test-data library! */ 
        ParticleEmitter debrisEffect = new ParticleEmitter("Debris"+aux, ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Debris.png"));
        debrisEffect.setMaterial(debrisMat);
        debrisEffect.setImagesX(3); debrisEffect.setImagesY(3); // 3x3 texture animation
        debrisEffect.setRotateSpeed(4);
        debrisEffect.setSelectRandomImage(true);
        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        debrisEffect.setGravity(0f,6f,0f);
        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
        debrisEffect.setLocalTranslation(x,y,z);
        rootNode.attachChild(debrisEffect);
        debrisEffect.emitAllParticles();

        /** Uses Texture from jme3-test-data library! */
        ParticleEmitter fireEffect = new ParticleEmitter("Emitter"+aux, ParticleMesh.Type.Triangle, 30);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/flame.png"));
        fireEffect.setMaterial(fireMat);
        fireEffect.setImagesX(2); fireEffect.setImagesY(2); // 2x2 texture animation
        fireEffect.setEndColor( new ColorRGBA(1f, 0f, 0f, 1f) );   // red
        fireEffect.setStartColor( new ColorRGBA(1f, 1f, 0f, 0.5f) ); // yellow
        fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 1, 0));
        fireEffect.setStartSize(2.2f);
        fireEffect.setEndSize(3.0f);
        fireEffect.setGravity(0f,0f,0f);
        fireEffect.setLowLife(2.0f);
        fireEffect.setHighLife(3.0f);
        fireEffect.getParticleInfluencer().setVelocityVariation(1f);
        fireEffect.setLocalTranslation(x,y,z);
        rootNode.attachChild(fireEffect);
        
        
        
    }
    public void createBonus(){
        
        Random r = new Random();
        int xpos = r.nextInt(13);
        int ypos = r.nextInt(13);
        
        if(!(xpos < 3 && ypos < 3)) 
            if(!(xpos > 9 && ypos > 9)){
                
                if(m[xpos][ypos] == 0){

                    if(aux_rootNode.getChild("bonus" + xpos + ypos) != null){
                        rootNode.attachChild(aux_rootNode.getChild("bonus" + xpos + ypos));
                        m[xpos][ypos] = 5;
                    }
                }
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        
        //Create 80 box randomly.
       if(count_box < 80){
            
            //Timer to create box random.
            currentTime = System.currentTimeMillis();

            if (currentTime - totalTime >= 1) {

                createBox();
                totalTime = currentTime;
                count_box++;
            }
       }
       
       //still creating boxes randomly every 2 seconds
       else{
            currentTime2 = System.currentTimeMillis();

            if (currentTime2 - totalTime2 >= 2000) {

                 createBox();
                 totalTime2 = currentTime2;
             }
        }
       
       
       //create bonus
       currentTime7 = System.currentTimeMillis();

        if (currentTime7 - totalTime7 >= 10000) {

            createBonus();
            totalTime7 = currentTime7;
        }
       
       
        //Time to remove bomb after planted it.
        if(bomb_on_field == 1)
            explodeBomb();
        
        if(bomb_on_field1 == 1)
            explodeBomb1();
        
        
        //efeito bomba
        if(effects_on_field == 1){
            
            currentTime4 = System.currentTimeMillis();
           
            if (currentTime4 - totalTime4 >= 1200) {

                for(int i = 0; i < 17; i++){

                    rootNode.detachChildNamed("Debris0");
                    rootNode.detachChildNamed("Emitter0");
                }
                
                effects_on_field = 0;
                special_on = false;
                special = false;
                totalTime4 = currentTime4; 
            }
            
            killPlayer(bomb_i, bomb_j);
            destroyBox(bomb_i, bomb_j);
        }
        
        //efeito bomba
        if(effects_on_field1 == 1){
            
            currentTime5 = System.currentTimeMillis();
        
            if(currentTime5 - totalTime5 >= 1200){
                
                for(int i = 0; i < 17; i++){

                    rootNode.detachChildNamed("Debris1");
                    rootNode.detachChildNamed("Emitter1");
                }
                
                effects_on_field1 = 0;
                special_on1 = false;
                special1 = false;
                totalTime5 = currentTime5;
            }
        
            killPlayer(bomb_i1, bomb_j1);
            destroyBox(bomb_i1, bomb_j1);
        }
    }
    public void restartGame(){
        
    
        rootNode.detachChild(playerNode1);
        rootNode.detachChild(playerNode);
        winner = 0;
        winner1 = 0;
        
        playerOne();
        playerTwo();
        guiNode.detachAllChildren();
        count_box -= counta;
     
    }
    public void playerOne(){
        
        otto_pos_i = 0;
        otto_pos_j = 1;
        
        // init player node
        playerNode = (Node) assetManager.loadModel("Models/Oto.mesh.xml");
        playerNode.setLocalScale(0.75f);
        playerNode.setLocalTranslation(-30, 36, 5);
        playerNode.rotate(1, 0, 0);
        playerNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        // init the animation of the player'
        animControl = playerNode.getControl(AnimControl.class);
        animChannel = animControl.createChannel();
        animChannel.setAnim("stand");
        
        //Player player = new Player(rootNode,0.5f,assetManager,inputManager);
        rootNode.attachChild(playerNode);
        
    }
    public void playerTwo(){
        
        otto_pos_i1 = 12;
        otto_pos_j1 = 11;
        
        // init player node1
        Quaternion q1 = new Quaternion();
        playerNode1 = (Node) assetManager.loadModel("Models/Oto.mesh.xml");
        playerNode1.setLocalScale(0.75f);
        playerNode1.setLocalTranslation(30, -36, 5);
        q1.fromAngles(20,0,60);
        playerNode1.setLocalRotation(q1);
        
        playerNode1.rotate(1, 0, 0);
        playerNode1.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        // init the animation of the player
        animControl = playerNode1.getControl(AnimControl.class);
        animChannel = animControl.createChannel();
        animChannel.setAnim("stand");
        
        //Player player = new Player(rootNode,0.5f,assetManager,inputManager);
        rootNode.attachChild(playerNode1);
    }
    public void timeSetup(){
        
        //Time to create random blocks.
        totalTime = System.currentTimeMillis();
        totalTime1 = System.currentTimeMillis();
        totalTime2 = System.currentTimeMillis();
        totalTime3 = System.currentTimeMillis();
        totalTime4 = System.currentTimeMillis();
        totalTime5 = System.currentTimeMillis();
        totalTime6 = System.currentTimeMillis();
    }
    public void textInput(int a){
        
        if(a == 1){
            
            guiNode.detachAllChildren();
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            BitmapText helloText = new BitmapText(guiFont, false);
            helloText.setSize(guiFont.getCharSet().getRenderedSize());
            helloText.setText("TIEEEEE!!!  ------ Press R to restart");
            helloText.setLocalTranslation(150, helloText.getLineHeight(), 0);
            helloText.setLocalTranslation(150, 280, 0);
            guiNode.attachChild(helloText);
        }
            
            
        else{
            guiNode.detachAllChildren();
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            BitmapText helloText = new BitmapText(guiFont, false);
            helloText.setSize(guiFont.getCharSet().getRenderedSize());
            helloText.setText("PLAYER " + win + " IS THE WINNER!!! ----- Press R to restart");
            helloText.setLocalTranslation(150, helloText.getLineHeight(), 0);
            helloText.setLocalTranslation(150, 280, 0);
            guiNode.attachChild(helloText);
        }
    }
    
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
