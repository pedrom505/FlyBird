package com.decisionmind.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by pedro on 20/03/18.
 */

public class Wall {

    private Texture wall_Texture;
    private Rectangle wall_Shape;

    private int horizontalPosition;
    private int verticalPosition;

    public Wall (String dir){
        wall_Texture = new Texture(dir);
        wall_Shape = new Rectangle();
    }

    public void setHorizontalPosition(int horizontalPosition){
        this.horizontalPosition = horizontalPosition;

        wall_Shape.set(
                horizontalPosition,
                verticalPosition,
                wall_Texture.getWidth(),
                wall_Texture.getHeight()
        );
    }

    public void setVerticalPositivon(int verticalPosition){
        this.verticalPosition = verticalPosition;

        wall_Shape.set(
                horizontalPosition,
                verticalPosition,
                wall_Texture.getWidth(),
                wall_Texture.getHeight()
        );
    }

    public int getHorizontalPosition(){
        return horizontalPosition;
    }

    public int getVerticalPositivon(){
        return verticalPosition;
    }

    public int getHeight(){
        return wall_Texture.getHeight();
    }

    public int getWidth(){
        return wall_Texture.getWidth();
    }

    public Texture getTexture(){
        return wall_Texture;
    }

    public Rectangle getShape(){
        return wall_Shape;
    }

}
