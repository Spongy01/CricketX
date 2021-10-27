package com.cricketx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.cricketx.Controller.KeyboardController;
import com.cricketx.CricketX;

import java.util.Random;

public class MainScreen implements Screen {
    private Sprite bat;
    private Sprite ball;
    private TextureAtlas textureAtlas;
    private CricketX parent;
    private Texture background;
    OrthographicCamera camera;
    PhysicsShapeCache physicsShapeCache;
    FitViewport viewport;
    World world;
    Box2DDebugRenderer debugRenderer;
    KeyboardController controller;
    static HUD hud;
    public Body Bat;
    public Body Ball;
    float accumulator = 0;
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 3;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.38f;
    static int batxcor = 0;
    static int batycor = 0;
    static float degrees =45;
    static float batvelx =0;
    static float batvely=0;
    static final float BATVEL = 120;




    static final float UpperBoundX =300;
    static final float UpperBoundY =100;
    static final float LowerBoundX =200;
    static final float LowerBoundY =47.5f;
    static boolean isVisible = false;
    static boolean isCollide = false;
    static boolean isCalculated = false;
    static float releasex =0;
    static float reachx =0;


    public MainScreen(CricketX cx){
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920f/8, 1080f/8, camera);
        parent = cx;

        textureAtlas = parent.loader.manager.get(parent.loader.atlas);
        bat = textureAtlas.createSprite("Bat");
        ball = textureAtlas.createSprite("Ball");
        Box2D.init();
        world = new World(new Vector2(0,0),true);
        physicsShapeCache = new PhysicsShapeCache("physics.xml");

        debugRenderer = new Box2DDebugRenderer();
        Bat = createBody("Bat",0,35,0) ;
        bat.setSize(bat.getWidth()*SCALE, bat.getHeight()*SCALE);//perfect size
        ball.setSize(ball.getWidth()*SCALE, ball.getHeight()*SCALE);//p
        background = new Texture("Base.png");
        controller = new KeyboardController();
        hud = new HUD(parent.batch,parent);
    }
    @Override
    public void show() {
        MenuScreen.didGameOvercall = true;
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                hud.Score++;
                isCollide = true;
            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("Contact ended");
                float x = Ball.getLinearVelocity().x * 10;
                float y = Ball.getLinearVelocity().y * 10;
                Ball.setLinearVelocity(x,y);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud.stage);
        multiplexer.addProcessor(controller);

        Gdx.input.setInputProcessor(multiplexer);
        Bat.setTransform(240,50,(float)Math.toRadians(degrees));
        Ball = createBody("Ball",240,240,0);
        Vector2 velocity = getBallxy();
        Ball.setTransform(releasex,270,0);
        Ball.setLinearVelocity(velocity.x,velocity.y);


    }
    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);
        LogicMove();
        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stepWorld();

        draw();

        //debugRenderer.render(world,camera.combined);
    }
    private void draw(){
        parent.batch.begin();
        parent.batch.draw(background,0,0,1920/4,1080/4);

        bat.setSize(7,35);//perfect size
        Bat.setTransform(Bat.getPosition().x,Bat.getPosition().y,(float)Math.toRadians(degrees));
        Bat.setLinearVelocity(batvelx,batvely);
        bat.setRotation(degrees);
        bat.setPosition(Bat.getPosition().x+batxcor,Bat.getPosition().y +batycor);
        bat.draw(parent.batch);
        ball.setPosition(Ball.getPosition().x,Ball.getPosition().y);
        Ball.setAngularVelocity(0);
        //ball.setRotation((float) Math.toDegrees(Ball.getAngle()));
        ball.draw(parent.batch);

        parent.batch.end();
        hud.update();
        hud.stage.draw();
    }

    private Body createBody(String name, float x, float y, float rotation) {
        Body body = physicsShapeCache.createBody(name, world, SCALE, SCALE);
        body.setTransform(x, y, rotation);
        return body;
    }
    private void LogicMove(){
        //isPauseCLicked();
        if(controller.shift){
            degrees = 315;
        }
        else {
            degrees = 45;
        }

        if(degrees==45){
            batxcor = -35;
            batycor = -7;
        }
        else {
            batxcor = 29;
            batycor = -20;
        }
        batvelx=0;
        batvely=0;
        if(controller.left && Bat.getPosition().x >LowerBoundX){
            batvelx =-BATVEL;
        }
        if (controller.up &&Bat.getPosition().y <UpperBoundY){
            batvely = BATVEL;
        }
        if(controller.down && Bat.getPosition().y >LowerBoundY){
            batvely = -BATVEL;
        }
        if (controller.right&& Bat.getPosition().x <UpperBoundX){
            batvelx = BATVEL;
        }
        if((Ball.getPosition().x<-100 || Ball.getPosition().x> (1920/4) +100 )||(Ball.getPosition().y<-100 || Ball.getPosition().y > (1080/4) +100 ) ){
           Vector2 velocity = getBallxy();
           Ball.setTransform(releasex,270,0);
           Ball.setLinearVelocity(velocity.x,velocity.y);
           isCollide = false;
           isCalculated = false;

        }

        if(Ball.getPosition().y<0){
            if(!isCollide && !isCalculated){
                hud.life--;
                isCalculated = true;
            }
        }
        if (hud.life==0){
            parent.changeScreen(CricketX.GAMEOVER);
        }

    }

    private Vector2 getBallxy(){
        Random numGen = new Random();
        releasex = numGen.nextInt(130);
        releasex+=175;
        reachx = numGen.nextInt(100);
        reachx+=190;
        float delx = reachx-releasex;
        float dely = 270;
        float time  = 2 ;
        float vx = delx/time;
        float vy = dely/time;
        Vector2 vel = new Vector2(vx,-vy);
        return vel;

    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        parent.batch.setProjectionMatrix(camera.combined);
        hud.resize(width,height);
    }
    public boolean isPauseCLicked(){
        boolean flag = false;
        Vector2 loc = controller.mouseLocation;
        float x = loc.x;
        float y =  loc.y;
        if(controller.isMouse1Down){
            if((x<45 && x>10)&& (y<45 && y>10)){
                // to do when pause is called;

                System.out.println("Klikd on button");
                System.out.println("Location x y "+x+ " "+y);
            }
        }
        //System.out.println("Location x y "+x+ " "+y);

        return flag;
    }
    public static HUD getHud(){
        return hud;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
