package com.decisionmind.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by pedro on 19/03/18.
 */

public class Obstacle {

    private int widthDevice;
    private int heightDevice;

    public Wall lowerWall;
    public Wall higherWall;

    public Obstacle (int widthDevice, int heightDevice){
        Random rand = new Random();
        int spaceBetweenWalls;
        int centerOfWalls;

        this.widthDevice = widthDevice;
        this.heightDevice = heightDevice;

        higherWall = new Wall("cano_maior.png");
        lowerWall = new Wall("cano_maior.png");

        spaceBetweenWalls = rand.nextInt(150) + 200;
        centerOfWalls = rand.nextInt(heightDevice - 40 - spaceBetweenWalls) + spaceBetweenWalls/2 + 20;

        lowerWall.setHorizontalPosition(widthDevice);
        lowerWall.setVerticalPositivon(centerOfWalls-spaceBetweenWalls/2-lowerWall.getHeight());
        higherWall.setHorizontalPosition(widthDevice);
        higherWall.setVerticalPositivon(centerOfWalls+spaceBetweenWalls/2);
    }

    public boolean shiftObstable(float deltaTime){

        lowerWall.setHorizontalPosition((int) (lowerWall.getHorizontalPosition() - deltaTime * widthDevice*0.5));
        higherWall.setHorizontalPosition((int) (higherWall.getHorizontalPosition() - deltaTime * widthDevice*0.5));

        if(higherWall.getHorizontalPosition() < -higherWall.getWidth()){
            return false;
        }
        return true;
    }


}
