package component;

import java.util.ArrayList;
import java.util.List;
import util.Constant;

/**
 * Water pipe object pool
 * To avoid repeatedly creating and destroying objects,
 * use object pools to create some objects in advance,
 * get them from the object pool when they are used,
 * and return them when they are finished
 * 
 * @author BUILD SUCCESSFUL
 */
public class PipePool {
	// Containers for objects in the pool
	private static final List<Pipe> normalPool = new ArrayList<>();
	private static final List<MovingPipe> movingPool = new ArrayList<>();
	// Maximum number of objects in the object pool 
	// Note: self-defined = 30
	public static final int MAX_PIPE_NUMBER = 30;
	public static final int FULL_PIPE = (Constant.FRAME_WIDTH
			/ (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;

	static {
		for (int i = 0; i < PipePool.FULL_PIPE; i++) {
			normalPool.add(new Pipe());
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
			int size = normalPool.size();
			if (size > 0) {
				return normalPool.remove(size - 1); // Remove and return the last
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
			if (normalPool.size() < MAX_PIPE_NUMBER) {
				normalPool.add(pipe);
			}
		}else {
			if (movingPool.size() < MAX_PIPE_NUMBER) {
				movingPool.add((MovingPipe)pipe);
			}
		}
	}
}
