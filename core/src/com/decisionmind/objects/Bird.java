package com.decisionmind.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import javax.xml.soap.Text;

/**
 * Created by pedro on 19/03/18.
 */

public class Bird {

    private int heightDevice;
    private int widthDevice;

    private Texture[] bird;
    private Circle bird_shape;

    private float variation = 0;
    private float fallSpeed = 0;

    private int verticalPosition;
    private int horizontalPosition;

    public Bird(int widthDevice, int heightDevice) {

        this.heightDevice = heightDevice;
        this.widthDevice = widthDevice;

        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");
        bird_shape = new Circle();

        verticalPosition = heightDevice / 2;
        horizontalPosition = 50;
        fallSpeed = 0;

    }


    public int getHorizontalPosition(){
        return horizontalPosition;
    }

    public int getVerticalPosition(){
        return verticalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition){
        this.horizontalPosition = horizontalPosition;
    }

    public void setVerticalPosition(int verticalPosition){
        this.verticalPosition = verticalPosition;
    }

    public Circle getShape (){
        return bird_shape;
    }

    public void flapWings(float deltaTime) {
        variation +=deltaTime *10;
        if(variation >2)
            variation =0;
    }

    public void fall (){
        fallSpeed++;

        verticalPosition -= fallSpeed;
        if(verticalPosition < 0){
            verticalPosition = 0;
        }
        if(verticalPosition > heightDevice - bird[0].getHeight()){
            verticalPosition = heightDevice - bird[0].getHeight();
            fallSpeed = 0;
        }

        bird_shape.set(
                horizontalPosition + bird[0].getWidth()/2,
                verticalPosition + bird[0].getHeight()/2,
                bird[0].getWidth() * 0.4f
        );

    }

    public void goUp (){
        fallSpeed = -15;
    }

    public Texture getTexture(){
        return bird[ (int)variation ];
    }

}
