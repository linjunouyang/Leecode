import java.util.Map;
import java.util.TreeMap;

public class _0853CarFleet {
    /**
     * 1. TreeMap
     *
     * https://leetcode.com/problems/car-fleet/discuss/139999/Easy-understanding-JAVA-TreeMap-Solution-with-explanation-and-comment
     *
     * if a car has greater distance left than another car & its time needed is smaller than another car, it wil chase up and become the same fleet
     *
     * The problem is how can you know if a car can catch the car in front of it?
     * In my solution, I calculate the time that a car can reach the target without any block.
     * The car has shorter time can catch up other cars. Does it make more sense to you?
     *
     * others:
     * using PQ:
     * https://leetcode.com/problems/car-fleet/discuss/180287/Java-Priority-Queue-Explained
     *
     * Time: O(nlogn)
     * Space: O(n)
     */
    public int carFleet(int target, int[] positions, int[] speeds) {
        TreeMap<Integer, Integer> distanceToSpeed = new TreeMap<>();
        int cars = positions.length;
        for (int car = 0; car < cars; car++){
            distanceToSpeed.put(target - positions[car], speeds[car]);
        }

        int fleets = 0;
        double prevTime = -1.0;
        /*for all car this value must > 0, so we can count for the car closeset to target*/
        for (Map.Entry<Integer, Integer> entry: distanceToSpeed.entrySet()){
            int distance = entry.getKey(); // distance
            int speed = entry.getValue(); // speed
            double curTime = 1.0 * distance / speed; // time to target
            if (curTime > prevTime) {
                // this car is unable to catch up previous one, form a new group and update the value
                ++fleets;
                prevTime = curTime;
            }
        }
        return fleets;
    }

    /**
     * 2.
     * Time: O(target)
     * Space: O(target)
     * https://leetcode.com/problems/car-fleet/discuss/140819/Solution-without-Map
     */
}
