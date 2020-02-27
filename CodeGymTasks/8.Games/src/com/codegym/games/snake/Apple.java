package com.codegym.games.snake;
import com.codegym.engine.cell.*;

public class Apple extends GameObject {

    public int x,y;
    public boolean isAlive = true;

    public Apple(int x, int y){
        super(x, y);
        this.x = x;
        this.y = y;
    }

    private static final String APPLE_SIGN = "\uD83C\uDF4E"; //UTF-16 symbol for an apple: "\uD83C\uDF4E".

    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.RED, 75);
    }
}
