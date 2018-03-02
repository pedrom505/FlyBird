package com.decisionmind.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] bird;
    private Texture backgroung;
    private Texture higherPipe;
    private Texture lowerPipe;
    private Texture badge;
    private Random rand;

    //configuration attributes
    private int widthDevice;
    private int heightDevice;

    private float variation = 0;
    private float fallSpeed = 0;
    private float verticalInitialPosition_Bird;
    private float horizontalPosition_Pipe;
    private float verticalPosition_Pipe;
    private float spaceBetweenPipes = 400;
    private float deltaTime;
    private int verticalPositionLimit;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");
        higherPipe = new Texture("cano_topo_maior.png");
        lowerPipe = new Texture("cano_baixo_maior.png");
        badge = new Texture("logo.png");
        rand = new Random();

        //configuration attributes
        backgroung = new Texture("fundo.png");
        widthDevice = Gdx.graphics.getWidth();
        heightDevice = Gdx.graphics.getHeight();

        verticalPositionLimit = (int) (lowerPipe.getHeight() + spaceBetweenPipes/2 - heightDevice/2);
        horizontalPosition_Pipe = widthDevice;
        verticalPosition_Pipe = 0;
        verticalInitialPosition_Bird = heightDevice / 2;

	}

	@Override
	public void render () {

        deltaTime = Gdx.graphics.getDeltaTime();

        variation += deltaTime * 10;
        horizontalPosition_Pipe -= deltaTime * 300;
        fallSpeed++;

        if(variation > 2)
            variation = 0;

        if(horizontalPosition_Pipe < -higherPipe.getWidth()){
            horizontalPosition_Pipe = widthDevice;
            verticalPosition_Pipe = rand.nextInt(verticalPositionLimit*2) - verticalPositionLimit;
        }

        if(Gdx.input.justTouched()){
            Gdx.app.log("Toque", "Toque na tela");
            fallSpeed = -15;
        }

        if(verticalInitialPosition_Bird > 5 || fallSpeed < 0)
            verticalInitialPosition_Bird -= fallSpeed;


        batch.begin();

        batch.draw(backgroung, 0, 0, widthDevice, heightDevice);
        batch.draw(badge, 0, heightDevice - badge.getHeight());
        batch.draw(higherPipe, horizontalPosition_Pipe, heightDevice / 2 + verticalPosition_Pipe + spaceBetweenPipes/2);
        batch.draw(lowerPipe, horizontalPosition_Pipe, heightDevice / 2 - spaceBetweenPipes/2 + verticalPosition_Pipe - lowerPipe.getHeight());
        batch.draw(bird[ (int)variation ], 30, verticalInitialPosition_Bird);

        batch.end();
    }
}
