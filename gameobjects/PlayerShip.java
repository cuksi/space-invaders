package com.codegym.games.spaceinvaders.gameobjects;


import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;
import java.util.List;


public class PlayerShip extends Ship {

    private Direction direction = Direction.UP;
    
    
    
    public PlayerShip() {
        super((SpaceInvadersGame.WIDTH / 2.0), (SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1));
        
        setStaticView(ShapeMatrix.PLAYER);
        
        
    }
    
    
    public void checkHit(List<Bullet> bullets) {
        
        if(!bullets.isEmpty()) {
            
            if(this.isAlive) {
                
                for (Bullet item : bullets) {
                
                if (item.isAlive) {
                boolean collision = this.isCollision(item);
                
                if(collision) {
                    
                    this.kill();
                    
                    item.kill();
                    
                }
                }
                
                
                }
                
            }
            
        }
        
    }


    @Override
    public void kill() {

        if(isAlive) {

            isAlive = false;

            setAnimatedView(false, ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST, ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
                    ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD, ShapeMatrix.DEAD_PLAYER);

        }

    }

    public void setDirection(Direction newDirection) {

        if (newDirection != Direction.DOWN) {

            this.direction = newDirection;

        }
    }

    public void move() {

        if (isAlive) {

            if (direction == Direction.LEFT) {
                x --;
            } else if (direction == Direction.RIGHT) {

                x++;
            }

            if (x < 0) {
                x = 0;
            } else if (x + width > SpaceInvadersGame.WIDTH) {

                x = SpaceInvadersGame.WIDTH - width;

            }

        }

    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public Bullet fire() {

        if (!isAlive) {

            return null;

        } else {

            return new Bullet(x+2, y - ShapeMatrix.BULLET.length, Direction.UP);

        }

    }


    public void win() {

        setStaticView(ShapeMatrix.WIN_PLAYER);

    }

}