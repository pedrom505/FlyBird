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
    private int moviment = 0;
    private int widthDevice;
    private int heightDevice;
	
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
	}

	@Override
	public void render () {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        moviment = moviment + 1;

        batch.begin();
        batch.draw(backgroung,0,0, widthDevice, heightDevice);
        batch.draw(bird[0], moviment, 300);
        batch.end();
	}

}
