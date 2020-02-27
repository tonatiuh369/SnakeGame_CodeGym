package com.codegym.games.snake;
import com.codegym.engine.cell.*;
import java.util.*;

public class Snake {

    public boolean isAlive = true;
   /* public enum Direction {   // This is not necessary here. There is already a Direction enum file I created. [7]
        UP, RIGHT, DOWN, LEFT
    }*/

    private Direction direction  = Direction.LEFT;

    //Setter direction
    public void setDirection(Direction direc) {
       // this.direction = direc;
        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) return;
        if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) return;
        
        switch (direc){
            
            case LEFT: // if we press LEFT key:
               {
                    if (direc == Direction.LEFT && this.direction == Direction.RIGHT) break; // Don't do nothing if its direction is Opposite
                    else {
                        this.direction = Direction.LEFT; // else change to LEFT
                        break; 
                        }
                }
            
            case RIGHT:
                {
                    if (direc == Direction.RIGHT && this.direction == Direction.LEFT) break; // Don't do nothing if its direction is Opposite
                    else {
                        this.direction = Direction.RIGHT; // else change to RIGHT
                        break; 
                        }
                }
                    
            case DOWN:
                {
                    if (direc == Direction.DOWN && this.direction == Direction.UP) break; // Don't do nothing if its direction is Opposite
                    else {
                        this.direction = Direction.DOWN; // else change to DOWN
                        break; 
                        }
                }
                
            case UP:
                {
                    if (direc == Direction.UP && this.direction == Direction.DOWN) break; // Don't do nothing if its direction is Opposite
                    else {
                        this.direction = Direction.UP; // else change to UP
                        break; 
                        }
                }
        }        
    }
    
    //Getter direction
    public Direction getDirection(){
        return direction;
    }

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake (int x, int y){
        GameObject first = new GameObject(x, y);
        GameObject second  = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);

        Collections.addAll(snakeParts, first, second, third); // Add GameObject first, second and third to snakeParts list
    }

    private static final String HEAD_SIGN = "\uD83D\uDC7E"; // UTF-16 symbol
    private static final String BODY_SIGN = "\u26AB";  // UTF-16 symbol
    
    public int getLength(){
        return snakeParts.size();
    }

    public void draw(Game game){ /* The draw(Game) method must call the Game class's setCellValue(int, int, String) method on each GameObject in the snakeParts list. [6] */
        for (int i = 0; i < snakeParts.size(); i++ ){
            if (i == 0) {
                if (isAlive) {
                    /*The setCellValue method must be called with arguments (x, y, HEAD_SIGN) on the snake's head (the element with index 0 in the snakeParts list). [6] */
                    //  snakeParts.get(0).setCellValue(x, y, HEAD_SIGN);
                    //  game.setCellValue(snakeParts.get(0).x, snakeParts.get(0).y, HEAD_SIGN);

                /* setCellValueEx(int, int, Color, String, Color, int)
                setCellValueEx(x, y, Color.NONE, HEAD_SIGN (or BODY_SIGN), <color of snake>, 75)    [7]
                setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN (or BODY_SIGN), <color of snake>, 75)   [7]*/
                    game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
                else
                    game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);

            }
            else if (i != 0) {
                if (isAlive) {
                /*The setCellValue method must be called with arguments (x, y, BODY_SIGN) on each segment of the snake's body (elements with non-zero indices in the snakeParts list). [6] */
                // snakeParts.get(i).setCellValue(x, y, BODY_SIGN);
              //  game.setCellValue(snakeParts.get(i).x, snakeParts.get(i).y, BODY_SIGN);

                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
                else
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    
 ///
    public void move(Apple apple){
        // GameObject newHead = new GameObject(1,1); X wrong
        // GameObject head = snakeParts.get(0); X wrong
        GameObject newHead = createNewHead();

        // this.isAlive = true; // Optional ?
        // boolean checkCol = checkCollision(createNewHead());
        // if(checkCol == true) {
        //    isAlive = false; 
            // not change the snakeParts list. [14]
        //}

        if (checkCollision(newHead) == false && newHead.x >= 0 && newHead.y >= 0 && newHead.x < SnakeGame.WIDTH && createNewHead().y < SnakeGame.HEIGHT )
            {
              if(newHead.x == apple.x && newHead.y == apple.y ) { // if the new snake head's coordinates match the apple's coordinates, you need to set isAlive=false on the apple and not remove the snake's tail. [12]
                apple.isAlive = false;
                snakeParts.add(0, newHead ); //add createNewHead() to snakeParts at position 0.
                // return;
              }
             // { // else { //else if (newHead.x != apple.x && newHead.y != apple.y ) {
                snakeParts.add(0, newHead ); //add createNewHead() to snakeParts at position 0.
                removeTail();
                // this.isAlive = true; /// optional ?
                // return;
            //  }
        }

        else // if (checkCol == true || newHead.x < 0 || newHead.y < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y >= SnakeGame.HEIGHT )
        {
            this.isAlive = false;
            // return;
        }
    } 
  
    public boolean checkCollision(GameObject obj){
        boolean eatSelf = false; // boolean eatSelf initial value
        for(GameObject segment : snakeParts) { //for (int i = 0; i < snakeParts.size(); i++ ){ 
            if(segment.x == obj.x && segment.y == obj.y ){ // if ( obj.x == snakeParts.get(i).x && obj.y == snakeParts.get(i).y )
                //return this.eatSelf = true; //return true;
                // break;
                eatSelf = true;
                return eatSelf;
            }
           // else return this.eatSelf= false; //return false;
        }
        return eatSelf;
    }


    public GameObject createNewHead(){

        GameObject obj = null;
        // headX and headY are the coordinates of the snake's head.
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;

        /* Create and return a new GameObject. The new GameObject must be created next to
     the element that currently contains the snake head (element with index 0 in snakeParts).  [9]*/
        // If the snake is moving to the left, new GameObject(headX-1, headY)...
        
        switch (direction) {
        case LEFT:  
            obj = new GameObject(headX-1, headY);
            break;
        case RIGHT: 
            obj = new GameObject(headX+1, headY);
            break;
        case UP:
            obj = new GameObject(headX, headY-1);
            break;
        case DOWN:
            obj = new GameObject(headX, headY+1);
            break;
        default:
            break;
        }

        return obj; // returns the new GameObject

        /* X  Wrong
        if (direction == Direction.LEFT ) snakeParts.add(0, new GameObject(headX-1, headY) );
        else if (direction == Direction.RIGHT ) snakeParts.add(0, new GameObject(headX+1, headY) );
        else if (direction == Direction.UP ) snakeParts.add(0, new GameObject(headX, headY-1) );
        else if (direction == Direction.DOWN ) snakeParts.add(0, new GameObject(headX, headY+1) );

        return snakeParts.get(0); // returns the new GameObject
        */
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1); //remove the last item in the snakeParts list.   [9]
    }

}
