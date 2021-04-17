package com.codegym.games.spaceinvaders;

import com.codegym.engine.cell.*;
import com.codegym.games.spaceinvaders.gameobjects.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SpaceInvadersGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    public static final int DIFFICULTY = 5;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;
    private List<Bullet> playerBullets;
    private static final int PLAYER_BULLETS_MAX = 1;
    private int score;


    @Override
    public void initialize() {

        setScreenSize(WIDTH, HEIGHT);

        createGame();

    }

    private void createGame() {

        createStars();

        enemyFleet = new EnemyFleet();

        enemyBullets = new ArrayList<Bullet>();

        playerShip = new PlayerShip();

        isGameStopped = false;

        animationsCount = 0;

        playerBullets = new ArrayList<Bullet>();

        score = 0;

        drawScene();

        setTurnTimer(40);

    }


    private void drawScene() {

        drawField();
        enemyFleet.draw(this);

        for (Bullet item : enemyBullets) {

            item.draw(this);

        }

        for (Bullet item : playerBullets) {

            item.draw(this);

        }

        playerShip.draw(this);

    }


    private void drawField() {

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {


                setCellValueEx((int) x, (int) y, Color.BLACK, "");
            }

        }

        for (Star objects : stars) {
            objects.draw(this);
        }

    }


    private void createStars() {

        stars = new ArrayList<Star>();
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {


            int starX = rand.nextInt(64);
            int starY = rand.nextInt(64);

            stars.add(new Star(starX, starY));

        }

    }


    @Override
    public void onTurn(int step) {

        moveSpaceObjects();

        check();

        Bullet bullet = enemyFleet.fire(this);

        if (bullet != null) {

            enemyBullets.add(bullet);

        }

        setScore(score);


        drawScene();

    }


    private void moveSpaceObjects() {

        enemyFleet.move();

        for (Bullet item : enemyBullets) {

            item.move();

        }

        for (Bullet item : playerBullets) {

            item.move();

        }

        playerShip.move();

    }


    private void removeDeadBullets() {

        List<Bullet> copyEnemyBullets = new ArrayList<Bullet>(enemyBullets);

        for (Bullet item : copyEnemyBullets) {

            if (item.y >= (HEIGHT - 1) || !item.isAlive) {

                enemyBullets.remove(item);

            }

        }

        List<Bullet> copyPlayerBullets = new ArrayList<Bullet>(playerBullets);

        for (Bullet item : copyPlayerBullets) {

            if (item.y + ShapeMatrix.BULLET.length  < 0 || !item.isAlive) {

                playerBullets.remove(item);

            }

        }

    }


    private void check() {

        playerShip.checkHit(enemyBullets);

        enemyFleet.checkHit(playerBullets);
        enemyFleet.deleteHiddenShips();

        removeDeadBullets();

        double borderCheck = enemyFleet.getBottomBorder();

        if (borderCheck >= playerShip.y) {

            playerShip.kill();

        }

        int enemyCount = enemyFleet.getShipCount();

        if (enemyCount == 0) {

            playerShip.win();
            stopGameWithDelay();

        }

        if (!playerShip.isAlive) {
            stopGameWithDelay();
        }

        score += enemyFleet.checkHit(playerBullets);

    }


    private void stopGame(boolean isWin) {

        isGameStopped = true;
        stopTurnTimer();

        if (isWin) {

            showMessageDialog(Color.WHITE, "YOU WON \n SCORE = " + score, Color.GREEN, 30);

        } else {

            showMessageDialog(Color.WHITE, "GAME OVER \n INSERT COIN", Color.RED, 30);

        }

    }

    private void stopGameWithDelay() {

        animationsCount++;

        if (animationsCount >= 10) {

            stopGame(playerShip.isAlive);

        }

    }

    @Override
    public void onKeyPress(Key key) {

        if (key == Key.SPACE && isGameStopped) {

            createGame();

        } else if (key == Key.LEFT) {

            playerShip.setDirection(Direction.LEFT);

        } else if (key == Key.RIGHT) {

            playerShip.setDirection(Direction.RIGHT);

        }else if (key == Key.SPACE) {

            Bullet newPlayerBullet = playerShip.fire();

            if (newPlayerBullet != null && playerBullets.size() < PLAYER_BULLETS_MAX) {

                playerBullets.add(newPlayerBullet);

            }

        }

    }

    @Override
    public void onKeyReleased(Key key) {

        if (key == Key.LEFT && playerShip.getDirection() == Direction.LEFT) {

            playerShip.setDirection(Direction.UP);
        } else if (key == Key.RIGHT && playerShip.getDirection() == Direction.RIGHT) {

            playerShip.setDirection(Direction.UP);

        }
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {

        if (x > 0 && x < WIDTH && y > 0 && y < HEIGHT){

            super.setCellValueEx(x, y, cellColor, value);

        }
    }
}