import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Saving total time vs average time
 *
 * average time:
 * less likely overflow
 * need to update it with division everytime -> compounded floating-point error
 *
 * In a real world system,
 * you would use a Decimal library that supports arbitrary precision and doesn't suffer from floating-point error.
 *
 * Number of HashMaps to use:
 * 4
 * 2 (if we use Pair or self define objects)
 *
 * Pair class:
 * whether or not this is good design is probably down to personal preference.
 * It does have the benefit of being less code to write (and less whiteboard space in an interview!).
 * However, it is a bit contextually strange that one member of the Pair is known as "the key"
 * and the other as "the value".
 *
 * Time: O(1) for all
 * Space: O(P + S^2)
 * where SS is the number of stations on the network, and P is the number of passengers making a journey concurrently during peak time.
 *
 * The program uses two HashMaps. We need to determine the maximum sizes these could become.
 *
 * checkInData. This HashMap holds one entry for each passenger who has checkIn(...)ed, but not checkOut(...)ed.
 * Therefore, the maximum size this HashMap could be is the maximum possible number of passengers making a journey at the same time, which we defined to be P.
 *
 * journeyData. one entry for each pair of stations that has had at least one passenger start and end a journey at those stations.
 * Over time, we could reasonably expect every possible pair of the S stations on the network to have an entry in this HashMap, which would be O(S^2)
 *
 * Seeing as we don't know whether S^2 or P is larger,
 * we need to add these together, giving a total space complexity of O(P + S^2)
 *
 *
 */
public class _1396DesignUndergroundSystem {
    private Map<String, Pair<Double, Double>> journeyData = new HashMap<>();
    private Map<Integer, Pair<String, Integer>> checkInData = new HashMap<>();

    public void checkIn(int id, String stationName, int t) {
        checkInData.put(id, new Pair<>(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        // Look up the check in station and check in time for this id.
        // You could combine this "unpacking" into the other lines of code
        // to have less lines of code overall, but we've chosen to be verbose
        // here to make it easy for all learners to follow.
        Pair<String, Integer> checkInDataForId = checkInData.get(id);
        String startStation = checkInDataForId.getKey();
        Integer checkInTime = checkInDataForId.getValue();

        // Lookup the current travel time data for this route.
        String routeKey = stationsKey(startStation, stationName);
        Pair<Double, Double> routeStats  = journeyData.getOrDefault(routeKey, new Pair<>(0.0, 0.0));
        Double totalTripTime = routeStats.getKey();
        Double totalTrips = routeStats.getValue();

        // Update the travel time data with this trip.
        double tripTime = t - checkInTime;
        journeyData.put(routeKey, new Pair<>(totalTripTime + tripTime, totalTrips + 1));

        // Remove check in data for this id.
        // Note that this is optional, we'll talk about it in the space complexity analysis.
        checkInData.remove(id);
    }

    public double getAverageTime(String startStation, String endStation) {
        // Lookup how many times this journey has been made, and the total time.
        String routeKey = stationsKey(startStation, endStation);
        Double totalTime = journeyData.get(routeKey).getKey();
        Double totalTrips = journeyData.get(routeKey).getValue();
        // The average is simply the total divided by the number of trips.
        return totalTime / totalTrips;
    }

    private String stationsKey(String startStation, String endStation) {
        // be aware of possible characters in station name
        return startStation + "->" + endStation;
    }

    /**
     * int: 32 bit, long: 64 bit
     *
     * OOD solution:
     * https://leetcode.com/problems/design-underground-system/discuss/672744/Java-solution-for-easy-understanding-using-OOPS
     * extract passenger and route info
     */
}
