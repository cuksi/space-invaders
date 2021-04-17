package com.codegym.games.spaceinvaders.gameobjects;


import com.codegym.games.spaceinvaders.ShapeMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.codegym.engine.cell.Game;
import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;


public class EnemyFleet {
    
    
    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;
    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;
    
    
    public EnemyFleet() {
        
        createShips();
        
    }
    
    
    
    private void createShips() {
        
        this.ships = new ArrayList<EnemyShip>();
        

        
            for (int y = 0; y <ROWS_COUNT; y++) {
                for(int x=0; x < COLUMNS_COUNT; x++){
                    
                    ships.add(new EnemyShip(x*STEP, y*STEP+12));
                }
            }

            Boss boss = new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1, 5);
            ships.add(boss);
            
        
        
    }
    
    
    public void draw(Game game) {
        
        for(int i =0; i < ships.size(); i++) {
            
            ships.get(i).draw(game);
        }
        
    }
    
    
    private double getLeftBorder() {
        
        double leftX = Integer.MAX_VALUE;
        
        for(EnemyShip item : ships) {
            
            if(item.x < leftX) {
                leftX = item.x;
            }
            
        }
        
        return leftX;
        
    }
    
    
    private double getRightBorder() {
        
        double rightX = Integer.MIN_VALUE;
        
        for(EnemyShip item : ships) {
            
            if(item.x + item.width > rightX) {
                rightX = item.x + item.width;
            }
            
        }
        
        return rightX;
        
    }
    
    
    private double getSpeed() {
        
        double speed = 3.0 / ships.size();
        
        if(speed > 2.0) {
            return speed = 2.0;
        }
        
        return speed;
        
    }
    
    
    public void move() {
        
        
        if(!ships.isEmpty()) {
            
            double leftBorder = getLeftBorder();
            double rightBorder = getRightBorder();
            
            double speed = getSpeed();
            
            if(this.direction == Direction.LEFT && leftBorder < 0) {
                
                this.direction = Direction.RIGHT;
                
                for (EnemyShip item : ships) {
                    
                    item.move(Direction.DOWN, speed);
                    
                }
                
                
            }else if(this.direction == Direction.RIGHT && rightBorder > SpaceInvadersGame.WIDTH) {
                
                this. direction = Direction.LEFT;
                
                for (EnemyShip item : ships) {
                    
                    item.move(Direction.DOWN, speed);
                    
                }
                
            } else {
                
                for (EnemyShip item : ships) {
                    
                    item.move(this.direction, speed);
                    
                }
                
            }
            
        }
    }
    
    
    public Bullet fire(Game game) {
        
        if(this.ships.isEmpty()) {
            
            return null;
        }else {
            
            int randomFirst = game.getRandomNumber(100 / SpaceInvadersGame.DIFFICULTY);
            
            if(randomFirst > 0) {
                return null;
            }
                
            int randomSecond = game.getRandomNumber(ships.size());
            
            return ships.get(randomSecond).fire();
                
            
            
            
        }
        
    }


    public int checkHit(List<Bullet> bullets) {

        if (bullets.isEmpty()) {

            return 0;

        } else {

            int totalScore = 0;

            for (EnemyShip itemShip : ships) {
            for (Bullet itemBullet : bullets) {

                boolean collision = itemShip.isCollision(itemBullet);

                if (collision && itemShip.isAlive && itemBullet.isAlive) {

                    itemShip.kill();
                    itemBullet.kill();
                    totalScore += itemShip.score;

                }

            }
        }

            return totalScore;

        }

    }
    
//    public void checkHit(List<Bullet> bullets) {
//
//        for (EnemyShip itemShip : ships) {
//            for (Bullet itemBullet : bullets) {
//
//                boolean collision = itemShip.isCollision(itemBullet);
//
//                if (collision && itemShip.isAlive && itemBullet.isAlive) {
//
//                    itemShip.kill();
//                    itemBullet.kill();
//
//                }
//
//            }
//        }
//
//    }


   

    public void deleteHiddenShips(){
        Iterator iterator = ships.iterator();
        while (iterator.hasNext()){
            Ship j = (Ship)iterator.next();
            if (!j.isVisible()){
                iterator.remove();
            }
        }
    }
    
    
    public double getBottomBorder() {

        double largest = Double.MIN_VALUE;

        for (EnemyShip shipItem : ships) {

            if (shipItem.y + shipItem.height > largest) {

                largest = shipItem.y + shipItem.height;

            }

        }

        return largest;

    }


    public int getShipCount() {

        return ships.size();

    }



    
    
}