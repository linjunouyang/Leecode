import java.util.LinkedList;
import java.util.Queue;

public class _0251Flatten2DVector {
    private Queue<Integer> q;

    // O(num of elements)
    public Vector2D(int[][] v) {
        q = new LinkedList<>();
        for (int[] arr : v){
            for (int num : arr) {
                q.offer(num);
            }
        }
    }

    // O(1) and O(1)
    public int next() {
        if (hasNext()) {
            return q.poll();
        }
        return Integer.MIN_VALUE;
    }

    // O(1) and O(1)
    public boolean hasNext() {
        return q.peek() != null;
    }

}
