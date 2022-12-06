package agh.ics.opp;

import java.util.Objects;


public class Vector2d {
    public final int x, y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return String.format("(%d,%d)", this.x, this.y);
    }
    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public boolean precedesX(Vector2d other){
        return this.x <= other.x;
    }
    public boolean precedesY(Vector2d other){
        return this.y <= other.y;
    }
    public boolean followsX(Vector2d other){
        return this.x >= other.x;
    }
    public boolean followsY(Vector2d other){
        return this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d addX(Vector2d other){ return new Vector2d(this.x + other.x, this.y); }
    public Vector2d addY(Vector2d other){ return new Vector2d(this.x, this.y + other.y); }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public Vector2d subtractX(Vector2d other){ return new Vector2d(this.x - other.x, this.y); }
    public Vector2d subtractY(Vector2d other){ return new Vector2d(this.x, this.y-other.y); }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Integer.max(this.x, other.x), Integer.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Integer.min(this.x, other.x), Integer.min(this.y, other.y));
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d that)) return false;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2d copy(){
        return new Vector2d(this.x, this.y);
    }

    public int getCoordinate(int i){
        return (i == 0 ? this.x : this.y);
    }
}