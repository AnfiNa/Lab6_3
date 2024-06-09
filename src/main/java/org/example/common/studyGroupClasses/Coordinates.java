package org.example.common.studyGroupClasses;

import java.io.Serializable;

/**
 * Class for Coordinates main.common.objects
 */
public class Coordinates implements Serializable {
    private Float x;
    private float y;

    public Coordinates(){

    }
    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "[" + x +", " + y + "]";
    }
    @Override
    public boolean equals(Object a){
        if (a == this){
            return true;
        }
        if (a == null || a.getClass() == this.getClass()){
            return false;
        }
        Coordinates a1 = (Coordinates) a;
        boolean b1 = a1.x == this.x;
        boolean b2 = a1.y == this.y;
        return b1 && b2;
    }

    public void setX(Float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public Float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}
