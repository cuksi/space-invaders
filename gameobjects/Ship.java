package com.codegym.games.spaceinvaders.gameobjects;


import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Ship extends GameObject {
    
    
    public boolean isAlive = true;
    private List<int[][]> frames;
    private int frameIndex;
    private boolean loopAnimation = false;
    
    
    
    public Ship(double x, double y) {
        super(x, y);
        
        
    }
    
    
    
    public void setStaticView(int[][] viewFrame) {
        
        
        setMatrix(viewFrame);
        
        frames = new ArrayList<int[][]>();
        frames.add(viewFrame);
        
        
        frameIndex = 0;
        
    }
    
    
    public Bullet fire() {
        
        
        
        return null;
        
    }
    
    
    public void kill() {
        
        isAlive = false;
        
    }
    
    


    public void nextFrame() {

        frameIndex++;

        if (frameIndex >= frames.size() && !loopAnimation) {

        } else if (frameIndex >= frames.size() && loopAnimation) {

            frameIndex = 0;

        }
        else {

             matrix = frames.get(frameIndex);

        }

    }


    @Override
    public void draw (Game game) {

        super.draw(game);
        nextFrame();

    }

    public boolean isVisible() {

        if (!isAlive && frameIndex >= frames.size()) {

            return false;

        } else {

            return true;

        }

    }
    
    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames) {

        loopAnimation = isLoopAnimation;

        setMatrix(viewFrames[0]);
        frames = Arrays.asList(viewFrames);
        frameIndex = 0;

    }
    
    
    
}