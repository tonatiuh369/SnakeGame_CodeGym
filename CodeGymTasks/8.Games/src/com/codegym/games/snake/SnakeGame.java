package com.codegym.games.snake;
import com.codegym.engine.cell.*;


public class SnakeGame extends Game {

    
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28; // The game must also stop in the event of a win. The player wins if the snake grows to this size (GOAL).
    private int score;

    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();

    }

    private void createGame(){
        /* The SnakeGame class's createGame() method must create a 
        new snake (instance of the Snake class) with coordinates 
        (WIDTH / 2, HEIGHT / 2) before calling the drawScene() method. [6] */
        this.snake = new Snake(WIDTH / 2, HEIGHT / 2);
        // this.apple = new Apple(5, 5);
        createNewApple();
        isGameStopped = false;

        drawScene();
        
       // Apple apple = new Apple(7, 7);
       // apple.draw(this);

        this.turnDelay = 300;
        setTurnTimer(turnDelay);
        
        score = 0;
        setScore(score);
    }
    
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.GHOSTWHITE, "");
            }
        }

        snake.draw(this);
        apple.draw(this);
    }
    
    private void createNewApple(){
        Apple apple;
        
        do {
            int ranW = getRandomNumber(WIDTH);
            int ranH = getRandomNumber(HEIGHT);
            //   Apple apple = new Apple(ranW, ranH);
            //   this.apple = apple;
            apple = new Apple(ranW, ranH);
        } while (snake.checkCollision(apple) == true);
         this.apple = apple;
    }
    
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "YOU WIN", Color.WHITE, 21);
    }
    
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "GAME OVER", Color.RED, 21);
    }


    @Override
    public void onTurn(int step) {
        //snake.move();
        snake.move(apple);
        
        if(apple.isAlive == false) {
            createNewApple();
            score += 5; // = score + 5;
            setScore(score);
            turnDelay -= 10; // = turnDelay - 10;
            setTurnTimer(turnDelay);
        }
        
        if (snake.isAlive == false) gameOver();
        
        if (snake.getLength() > GOAL) win();
        
        drawScene();

    }
    
    @Override   //  override the Game parent class's onKeyPress(Key) method.
    public void onKeyPress(Key key){

        switch (key){
            case LEFT: // if we press LEFT key:
                snake.setDirection(Direction.LEFT); break; // change to LEFT
                
            case RIGHT:
                snake.setDirection(Direction.RIGHT); break; // change to RIGHT
                
            case DOWN:
                snake.setDirection(Direction.DOWN); break; // change to DOWN
                
            case UP:
                snake.setDirection(Direction.UP); break; // change to UP
        }
        
        if (key == Key.SPACE && isGameStopped == true) createGame();
        
        /*
            //case LEFT: // if we press LEFT key:
                    if (key == Key.LEFT){
                        if (snake.getDirection() == Direction.RIGHT) return; // Don't do nothing if its direction is Opposite
                        else snake.setDirection(Direction.LEFT);  // else change to LEFT
                    }
            //case RIGHT:
                    if (key == Key.RIGHT){
                        if (snake.getDirection() == Direction.LEFT) return; // Don't do nothing if its direction is Opposite
                        else snake.setDirection(Direction.RIGHT);  // else change to RIGHT
                    }
            //case UP:
                    if (key == Key.UP){
                        if (snake.getDirection() == Direction.DOWN) return; // Don't do nothing if its direction is Opposite
                        else snake.setDirection(Direction.UP);  // else change to UP
                    }
            //case DOWN:
                    if (key == Key.DOWN){ 
                        if (snake.getDirection() == Direction.UP) return; // Don't do nothing if its direction is Opposite
                        else snake.setDirection(Direction.DOWN);  // else change to DOWN
                    }
        */
    }

}
