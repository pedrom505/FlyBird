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
    private Texture higherWall;
    private Texture lowerWall;
    private Texture gameover;
    private Random rand;
    private BitmapFont font;
    private BitmapFont message;
    private Circle bird_shape;
    private Rectangle higherWall_shape;
    private Rectangle lowerWall_shape;
    //private ShapeRenderer shape;

    //configuration attributes
    private int widthDevice;
    private int heightDevice;
    private int gameState = 0; //0 -> game is stopped 1 -> start the game 2 -> game over
    private int score = 0;
    private boolean scoreFlag = true;

    //Bird Variables
    private float variation = 0;
    private float fallSpeed = 0;

    private float verticalPosition_Bird;
    private float horizontalPosition_Bird = 120;

    private float verticalPosition_Wall;
    private float horizontalPosition_Wall;
    private int spaceBetweenWalls;
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

        gameover = new Texture("game_over.png");

        higherWall = new Texture("cano_topo_maior.png");
        lowerWall = new Texture("cano_baixo_maior.png");
        higherWall_shape = new Rectangle();
        lowerWall_shape = new Rectangle();

        rand = new Random();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);

        message = new BitmapFont();
        message.setColor(Color.WHITE);
        message.getData().setScale(3);


        //configuration attributes
        backgroung = new Texture("fundo.png");
        widthDevice = Gdx.graphics.getWidth();
        heightDevice = Gdx.graphics.getHeight();

        spaceBetweenWalls = rand.nextInt(200) + 300;
        verticalPositionLimit = lowerWall.getHeight() + spaceBetweenWalls/2 - heightDevice/2;
        horizontalPosition_Wall = widthDevice;
        verticalPosition_Wall = 0;
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

            fallSpeed++;

            if(gameState == 1){
                horizontalPosition_Wall -= deltaTime * 500;



                //Restart the Wall position when it cross the screen
                if(horizontalPosition_Wall < -higherWall.getWidth()){
                    horizontalPosition_Wall = widthDevice;
                    spaceBetweenWalls = rand.nextInt(200) + 300;
                    verticalPositionLimit = lowerWall.getHeight() + spaceBetweenWalls/2 - heightDevice/2;
                    verticalPosition_Wall = rand.nextInt(verticalPositionLimit*2) - verticalPositionLimit;
                    scoreFlag = true;
                }

                //Detects when the bird passes through the Wall
                if(horizontalPosition_Wall < horizontalPosition_Bird){
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

            }else { // Game over screen
                if(Gdx.input.justTouched()){
                    gameState = 0;
                    score = 0;
                    scoreFlag = true;
                    horizontalPosition_Wall = widthDevice;
                    spaceBetweenWalls = rand.nextInt(200) + 300;
                    verticalPositionLimit = lowerWall.getHeight() + spaceBetweenWalls/2 - heightDevice/2;
                    verticalPosition_Wall = rand.nextInt(verticalPositionLimit*2) - verticalPositionLimit;
                    verticalPosition_Bird = heightDevice / 2;
                    fallSpeed = 0;
                }
            }

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
        batch.draw(higherWall, horizontalPosition_Wall, heightDevice / 2 + verticalPosition_Wall + spaceBetweenWalls/2);
        batch.draw(lowerWall, horizontalPosition_Wall, heightDevice / 2 - spaceBetweenWalls/2 + verticalPosition_Wall - lowerWall.getHeight());
        batch.draw(bird[ (int)variation ], horizontalPosition_Bird, verticalPosition_Bird);
        font.draw(batch, String.valueOf(score), widthDevice/2, heightDevice-heightDevice*0.02f );

        if(gameState == 2){
            batch.draw(gameover, widthDevice/2 - gameover.getWidth()/2, heightDevice/2);
            message.draw(batch, "Pressione para jogar novamente",widthDevice/2 - 200, heightDevice/2);
        }

        batch.end();

        bird_shape.set(
                horizontalPosition_Bird + bird[0].getWidth()/2,
                verticalPosition_Bird + bird[0].getHeight()/2,
                bird[0].getWidth() * 0.4f
        );
        lowerWall_shape.set(
                horizontalPosition_Wall,
                heightDevice / 2 - spaceBetweenWalls/2 + verticalPosition_Wall - lowerWall.getHeight(),
                lowerWall.getWidth(),
                lowerWall.getHeight()
        );

        higherWall_shape.set(
                horizontalPosition_Wall,
                heightDevice / 2 + verticalPosition_Wall + spaceBetweenWalls/2,
                higherWall.getWidth(),
                higherWall.getHeight()
        );

        if(Intersector.overlaps(bird_shape,lowerWall_shape) || Intersector.overlaps(bird_shape,higherWall_shape)){
            gameState = 2;
        }

    }
}
