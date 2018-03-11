package com.decisionmind.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] bird;
    private Texture backgroung;
    private Texture higherPipe;
    private Texture lowerPipe;
    private Random rand;
    private BitmapFont font;
    private Circle bird_shape;
    private Rectangle higherPipe_shape;
    private Rectangle lowerPipe_shape;
    //private ShapeRenderer shape;

    //configuration attributes
    private int widthDevice;
    private int heightDevice;
    private int gameState = 0; //0 -> game is stopped 1 -> start the game
    private int score = 0;
    private boolean scoreFlag = true;

    //Bird Variables
    private float variation = 0;
    private float fallSpeed = 0;

    private float verticalPosition_Bird;
    private float horizontalPosition_Bird = 120;

    private float verticalPosition_Pipe;
    private float horizontalPosition_Pipe;
    private float spaceBetweenPipes = 300;
    private int verticalPositionLimit;

    private float deltaTime;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        //shape = new ShapeRenderer();

        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");
        bird_shape = new Circle();

        higherPipe = new Texture("cano_topo_maior.png");
        lowerPipe = new Texture("cano_baixo_maior.png");
        higherPipe_shape = new Rectangle();
        lowerPipe_shape = new Rectangle();

        rand = new Random();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);


        //configuration attributes
        backgroung = new Texture("fundo.png");
        widthDevice = Gdx.graphics.getWidth();
        heightDevice = Gdx.graphics.getHeight();

        verticalPositionLimit = (int) (lowerPipe.getHeight() + spaceBetweenPipes/2 - heightDevice/2);
        horizontalPosition_Pipe = widthDevice;
        verticalPosition_Pipe = 0;
        verticalPosition_Bird = heightDevice / 2;

	}

	@Override
	public void render () {

        deltaTime = Gdx.graphics.getDeltaTime();

        variation += deltaTime * 10;

        if(variation > 2)
            variation = 0;

        if(gameState == 0) {
            //Wait for the first touch of the user to start the game
            if(Gdx.input.justTouched()){
                gameState = 1;
            }
        }else{

            //
            horizontalPosition_Pipe -= deltaTime * 500;
            fallSpeed++;

            //Restart the pipe position when it cross the screen
            if(horizontalPosition_Pipe < -higherPipe.getWidth()){
                horizontalPosition_Pipe = widthDevice;
                verticalPosition_Pipe = rand.nextInt(verticalPositionLimit*2) - verticalPositionLimit;
                scoreFlag = true;
            }

            //Detects when the bird passes through the pipe
            if(horizontalPosition_Pipe < horizontalPosition_Bird){
                if (scoreFlag){
                    scoreFlag = false;
                    score++;
                }
            }

            //Detect the touch event
            if(Gdx.input.justTouched()){
                gameState = 1;
                fallSpeed = -20;
            }


            //if(verticalPosition_Bird > 5 || fallSpeed < 0)
            verticalPosition_Bird -= fallSpeed;

            if(verticalPosition_Bird < 0){
                verticalPosition_Bird = 0;
            }
            if(verticalPosition_Bird > heightDevice - bird[0].getHeight()){
                verticalPosition_Bird = heightDevice - bird[0].getHeight();
                fallSpeed = 0;
            }

        }

        // Draw all elements of the game
        batch.begin();

        batch.draw(backgroung, 0, 0, widthDevice, heightDevice);
        batch.draw(higherPipe, horizontalPosition_Pipe, heightDevice / 2 + verticalPosition_Pipe + spaceBetweenPipes/2);
        batch.draw(lowerPipe, horizontalPosition_Pipe, heightDevice / 2 - spaceBetweenPipes/2 + verticalPosition_Pipe - lowerPipe.getHeight());
        batch.draw(bird[ (int)variation ], horizontalPosition_Bird, verticalPosition_Bird);
        font.draw(batch, String.valueOf(score), widthDevice/2, heightDevice-heightDevice*0.02f );

        batch.end();

        bird_shape.set(
                horizontalPosition_Bird + bird[0].getWidth()/2,
                verticalPosition_Bird + bird[0].getHeight()/2,
                bird[0].getWidth() * 0.4f
        );
        lowerPipe_shape.set(
                horizontalPosition_Pipe,
                heightDevice / 2 - spaceBetweenPipes/2 + verticalPosition_Pipe - lowerPipe.getHeight(),
                lowerPipe.getWidth(),
                lowerPipe.getHeight()
        );

        higherPipe_shape.set(
                horizontalPosition_Pipe,
                heightDevice / 2 + verticalPosition_Pipe + spaceBetweenPipes/2,
                higherPipe.getWidth(),
                higherPipe.getHeight()
        );

        //Drawing the shapes
        /*shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.circle(bird_shape.x, bird_shape.y, bird_shape.radius);
        shape.rect(lowerPipe_shape.x, lowerPipe_shape.y, lowerPipe_shape.width, lowerPipe_shape.height);
        shape.rect(higherPipe_shape.x, higherPipe_shape.y, higherPipe_shape.width, higherPipe_shape.height);

        shape.end();*/

        if(Intersector.overlaps(bird_shape,lowerPipe_shape) || Intersector.overlaps(bird_shape,higherPipe_shape)){
            gameState = 0;
            Gdx.app.log("Colisão", "Houve uma colisão");
        }

    }
}
