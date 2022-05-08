package component;

import java.util.ArrayList;

import java.util.List;

import util.Constant;

/**
 * Water pipe object pool
 * To avoid repeatedly creating and destroying objects, use object pools to create some objects in advance, get them from the object pool when they are used, and return them when they are finished
 * 
 * @author BUILD SUCCESSFUL
 *
 */
public class PipePool {
	private static final List<Pipe> pool = new ArrayList<>(); // Containers for objects in the pool
	private static final List<MovingPipe> movingPool = new ArrayList<>(); // Containers for objects in the pool
	public static final int MAX_PIPE_COUNT = 30; // Maximum number of objects in the object pool, self-defined
	public static final int FULL_PIPE = (Constant.FRAME_WIDTH
			/ (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;

	static {
		for (int i = 0; i < PipePool.FULL_PIPE; i++) {
			pool.add(new Pipe());
		}
		for (int i = 0; i < PipePool.FULL_PIPE; i++) {
			movingPool.add(new MovingPipe());
		}
	}

	/**
	 * Get an object from the object pool
	 * 
	 * @return Pass in the type of the object to determine which object pool to get it from
	 */
	public static Pipe get(String className) {
		if ("Pipe".equals(className)) {
			int size = pool.size();
			if (size > 0) {
				return pool.remove(size - 1); // Remove and return the last
			} else {
				return new Pipe(); // Empty object pool, return a new object
			}
		} else {
			int size = movingPool.size();
			if (size > 0) {
				return movingPool.remove(size - 1); // Remove and return the last
			} else {
				return new MovingPipe(); // Empty object pool, return a new object
			}
		}
	}

	/**
	 * Returning objects to containers
	 */
	public static void giveBack(Pipe pipe) {
		// Determining the type of class
		if(pipe.getClass() == Pipe.class) {
			if (pool.size() < MAX_PIPE_COUNT) {
				pool.add(pipe);
			}
		}else {
			if (movingPool.size() < MAX_PIPE_COUNT) {
				movingPool.add((MovingPipe)pipe);
			}
		}
	}
}
