package com.decisionmind.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] bird;
    private Texture backgroung;

    //configuration attributes
    private int widthDevice;
    private int heightDevice;

    private float variation = 0;
    private float fallSpeed = 0;
    private float verticalInitialPosition;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");

        backgroung = new Texture("fundo.png");
        widthDevice = Gdx.graphics.getWidth();
        heightDevice = Gdx.graphics.getHeight();

        verticalInitialPosition = heightDevice / 2;
	}

	@Override
	public void render () {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        variation += Gdx.graphics.getDeltaTime()*10;
        fallSpeed++;

        if(variation > 2)
            variation = 0;

        if(verticalInitialPosition > 0)
            verticalInitialPosition -= fallSpeed;

        batch.begin();
        batch.draw(backgroung,0,0, widthDevice, heightDevice);
        batch.draw(bird[ (int)variation ], 30, verticalInitialPosition);
        batch.end();
	}

}
