package component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.Game;

public class BirdKeyListener implements KeyListener {
    public BirdKeyListener() {
    }

    // Press the button to call different methods based on the game status
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        switch (Game.getGameState()) {
            case Game.GAME_READY:
                if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                    Game.getBird().birdFlap();
                    Game.getBird().birdFall();
                    Game.setGameState(Game.GAME_START);
                }
                break;
            case Game.GAME_START:
                if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                    /* Pressing space or up button during the game
                     * will make the wings vibrate once
                     * and continue to be affected by gravity */
                    Game.getBird().birdFlap();
                    Game.getBird().birdFall();
                }
                break;
            case Game.GAME_OVER:
                if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                    resetGame();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Game.getGameState());
        }
    }

    // Restart Game
    public void resetGame() {
        Game.setGameState(Game.GAME_READY);
        Game.getGameElement().reset();
        Game.getBird().reset();
    }

    // Key release, change key status flag
    @Override
    public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                Game.getBird().keyReleased();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
