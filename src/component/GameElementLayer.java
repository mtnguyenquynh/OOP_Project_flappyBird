package component;

import java.awt.Graphics;


import java.util.ArrayList;
import java.util.List;

import util.Constant;
import util.GameUtil;

/**
 * The game element layer, which currently manages 
 * the generation logic of the water pipes and 
 * draws the water pipes in the container
 *
 * @author BUILD SUCCESSFUL
 */

public class GameElementLayer {
    private final List<Pipe> pipes; // Containers for water pipes

    // Constructor
    public GameElementLayer() {
        pipes = new ArrayList<>();
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        // Iterate through the water pipe container, draw if visible, return if not
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.isVisible()) {
                pipe.draw(g, bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;
            }
        }
        // Collision Detection
        isCollideBird(bird);
        pipeBornLogic(bird);
    }

    /**
     * logic of adding water pipes: when the last element added to 
     * the container is fully displayed on the screen, the next pair is added; 
     * water pipes appear in pairs relative to each other with a gap height of 
     * 1/6 of the height of the window.
     * the distance between each pair of water pipes is 1/4 of the screen height; 
     * the range of values for the height of the water pipes is [1/8~5/8] of the window
     */
    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT / 5;
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT >> 2;
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
    public static final int MAX_HEIGHT = ((Constant.FRAME_HEIGHT) >> 3) * 5;

    private void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            // No more water pipes added after the bird dies
            return;
        }
        if (pipes.size() == 0) {
            // If the container is empty, add a pair of water pipes
            int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); 

            Pipe top = PipePool.get("Pipe");
            top.setAttribute(Constant.FRAME_WIDTH, -Constant.TOP_PIPE_LENGTHENING,
                    topHeight + Constant.TOP_PIPE_LENGTHENING, Pipe.TYPE_TOP_NORMAL, true);

            Pipe bottom = PipePool.get("Pipe");
            bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
                    Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

            pipes.add(top);
            pipes.add(bottom);
        } else {
            // Determine if the last pair of pipes is completely in the game window, if it is, add the pipes
            Pipe lastPipe = pipes.get(pipes.size() - 1); // Get the last water pipe in the container
            int currentDistance = lastPipe.getX() - bird.getBirdX() + Bird.BIRD_WIDTH / 2; // The distance between the bird and the last pipe
            final int SCORE_DISTANCE = Pipe.PIPE_WIDTH * 2 + HORIZONTAL_INTERVAL; // Score if less than the scoring distance
            if (lastPipe.isInFrame()) {
                if (pipes.size() >= PipePool.FULL_PIPE - 2
                        && currentDistance <= SCORE_DISTANCE + Pipe.PIPE_WIDTH * 3 / 2) {
                    ScoreCounter.getInstance().score(bird);
                }
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1; // Get current score
                    // The probability of refreshing the mobile water pipe increases with the current score, 
                    //when the score is greater than 19, all refresh the mobile water pipe
                    if (GameUtil.isInProbability(currentScore, 20)) {
                        if (GameUtil.isInProbability(1, 4)) // Probability of generating moving water pipes and moving suspended water pipes
                            addMovingHoverPipe(lastPipe);
                        else
                            addMovingNormalPipe(lastPipe);
                    } else {
                        if (GameUtil.isInProbability(1, 2)) // Probability of generating a stationary normal water pipe and a stationary suspended water pipe
                            addNormalPipe(lastPipe);
                        else
                            addHoverPipe(lastPipe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Add ordinary water pipe
     *
     * @param lastPipe Pass in the last pipe to get the x coordinate
     */
    private void addNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // Randomly generated water pipe height
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // x-coordinate of new water pipe = x-coordinate of last pair of water pipes + interval of water pipes

        Pipe top = PipePool.get("Pipe"); // Obtaining objects from a pool of plumbing objects

        // Set x, y, height, type properties
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_NORMAL, true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_NORMAL, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * Add suspension water pipe
     *
     * @param lastPipe Pass in the last pipe to get the x coordinate
     */
    private void addHoverPipe(Pipe lastPipe) {

        // Randomly generated water pipe height, [1/4,1/6] of the screen height
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // x-coordinate of new water pipe = x-coordinate of last pair of water pipes + interval of water pipes
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // y-coordinate of the random water pipe, [1/6,1/12] of the window

        int type = Pipe.TYPE_HOVER_NORMAL;

        // Generating the upper suspended water pipe
        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // Generating the lower suspended water pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * Adding a moving hover hose
     *
     * @param lastPipe Pass in the last pipe to get the x coordinate
     */
    private void addMovingHoverPipe(Pipe lastPipe) {

        // Randomly generated water pipe height, [1/4,1/6] of the screen height
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // x-coordinate of new water pipe = x-coordinate of last pair of water pipes + interval of water pipes
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // y-coordinate of the random water pipe, [1/6,1/12] of the window

        int type = Pipe.TYPE_HOVER_HARD;

        // Generating the upper suspended water pipe
        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // Generating the lower suspended water pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * Add moving common water pipe
     *
     * @param lastPipe Pass in the last pipe to get the x coordinate
     */
    private void addMovingNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // Randomly generated water pipe height
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // x-coordinate of new water pipe = x-coordinate of last pair of water pipes + interval of water pipes

        Pipe top = PipePool.get("MovingPipe");
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_HARD, true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_HARD, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * Determine if an element and a bird have collided
     *
     * @param bird Pass in the bird object
     */
    public void isCollideBird(Bird bird) {
        // No further judgment if the bird is dead
        if (bird.isDead()) {
            return;
        }
        // Traversing the water pipe container
        for (Pipe pipe : pipes) {
            //  Determine if colliding rectangles have intersection
            if (pipe.getPipeRect().intersects(bird.getBirdCollisionRect())) {
                bird.deadBirdFall();
                return;
            }
        }
    }

    // Reset element layer
    public void reset() {
        for (Pipe pipe : pipes) {
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
