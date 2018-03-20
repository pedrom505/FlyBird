package com.decisionmind.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.decisionmind.objects.Bird;
import com.decisionmind.objects.Obstacle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture backgroung;
    private Texture gameover;
    private BitmapFont font;
    private BitmapFont message;

    private Obstacle obstable;
    private Bird bird;

    //configuration attributes
    private int widthDevice;
    private int heightDevice;
    private int gameState = 0; //0 -> game is stopped 1 -> start the game 2 -> game over
    private int score = 0;
    private boolean scoreFlag = true;
    private float deltaTime;
	
	@Override
	public void create () {
        batch = new SpriteBatch();

        widthDevice = Gdx.graphics.getWidth();
        heightDevice = Gdx.graphics.getHeight();

        gameover = new Texture("game_over.png");

        obstable = new Obstacle( widthDevice, heightDevice);
        bird = new Bird( widthDevice, heightDevice);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);

        message = new BitmapFont();
        message.setColor(Color.WHITE);
        message.getData().setScale(3);


        //configuration attributes
        backgroung = new Texture("fundo.png");

	}

	@Override
	public void render () {

        deltaTime = Gdx.graphics.getDeltaTime();

        bird.flapWings(deltaTime);

        if(gameState == 0) {
            //Wait for the first touch of the user to start the game
            if(Gdx.input.justTouched()){
                gameState = 1;
            }
        }else{

            bird.fall();

            if(gameState == 1){

                //Restart the Wall position when it cross the screen
                if(!obstable.shiftObstable(deltaTime)){
                    obstable = new Obstacle( widthDevice, heightDevice);
                    scoreFlag = true;
                }

                //Detects when the bird passes through the Wall
                if(obstable.higherWall.getHorizontalPosition() < bird.getHorizontalPosition()){
                    if (scoreFlag){
                        scoreFlag = false;
                        score++;
                    }
                }

                //Detect the touch event
                if(Gdx.input.justTouched()){
                    gameState = 1;
                    bird.goUp();
                }

            }else { // Game over screen
                if(Gdx.input.justTouched()){
                    gameState = 0;
                    score = 0;
                    scoreFlag = true;
                    obstable = new Obstacle( widthDevice, heightDevice);
                    bird = new Bird( widthDevice, heightDevice);
                }
            }

        }

        // Draw all elements of the game
        batch.begin();

        batch.draw(backgroung, 0, 0, widthDevice, heightDevice);

        //batch.draw(higherWall, horizontalPosition_Wall, heightDevice / 2 + verticalPosition_Wall + spaceBetweenWalls/2);

        batch.draw(obstable.lowerWall.getTexture(), obstable.lowerWall.getHorizontalPosition(), obstable.lowerWall.getVerticalPositivon());
        batch.draw(obstable.higherWall.getTexture(), obstable.higherWall.getHorizontalPosition(), obstable.higherWall.getVerticalPositivon());
        batch.draw(bird.getTexture(), bird.getHorizontalPosition(), bird.getVerticalPosition());
        font.draw(batch, String.valueOf(score), widthDevice/2, heightDevice-heightDevice*0.02f );

        if(gameState == 2){
            batch.draw(gameover, widthDevice/2 - gameover.getWidth()/2, heightDevice/2);
            message.draw(batch, "Pressione para jogar",widthDevice/2 - 200, heightDevice/2);
        }

        batch.end();

        if(Intersector.overlaps(bird.getShape(),obstable.lowerWall.getShape())
                || Intersector.overlaps(bird.getShape(),obstable.higherWall.getShape())){
            gameState = 2;
        }

    }




}
