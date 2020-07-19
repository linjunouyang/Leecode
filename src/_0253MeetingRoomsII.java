import java.util.*;

public class _0253MeetingRoomsII {
    /**
     * 1. Priority Queues
     *
     * Intuition: If we do this manually, we would sort meetings by starting time
     * Reason:
     * If sorted, we only need to consider possible overlaps between previous ending times and new starting time
     * Otherwise, we need to consider conflicts at two ends
     *
     * Consider a concrete example and think about how to solve it.
     * We can find out that:
     * 1) We compare the new starting time with all ongoing ones' ending time, determine a new room or not
     * 2) We don't care about which specific room is free. As long as there is one free room, we use it
     * -> Keep all rooms in a min heap, with key being ending time
     *
     * 1. Sort the given meetings by their start time.
     * 2. Initialize a new min-heap and add the first meeting's ending time to the heap.
     * 3. For every meeting room check if the minimum element of the heap i.e. the room at the top of the heap is free or not.
     * If the room is free, then we extract the topmost element and add it back with the ending time of the current meeting we are processing.
     * If not, then we allocate a new room and add it to the heap.
     * 4. After processing all the meetings, the size of the heap will be the answer
     *
     * O(log n) time for the enqueing and dequeing methods (offer, poll, remove() and add)
     * O(n) for the remove(Object) and contains(Object) methods
     * O(1) for the retrieval methods (peek, element, and size)
     *
     * Time complexity: O(NlogN)
     * sorting: O(NlogN)
     *
     * Space commplexity: O(N)
     *
     */

    public int minMeetingRooms(int[][] intervals) {

        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        // Min heap
        PriorityQueue<Integer> allocator =
                new PriorityQueue<Integer>(
                        intervals.length,
                        new Comparator<Integer>() {
                            public int compare(Integer a, Integer b) {
                                return a - b;
                            }
                        });

        // Sort the intervals by start time
        Arrays.sort(
                intervals,
                new Comparator<int[]>() {
                    public int compare(int[] a, int[] b) {
                        return a[0] - b[0];
                    }
                });

        // Add the first meeting
        allocator.add(intervals[0][1]);

        // Iterate over remaining intervals
        for (int i = 1; i < intervals.length; i++) {

            // If the room due to free up the earliest is free, assign that room to this meeting.
            if (intervals[i][0] >= allocator.peek()) {
                allocator.poll();
            }

            // If a new room is to be assigned, then also we add to the heap,
            // If an old room is allocated, then also we have to add to the heap with updated end time.
            allocator.add(intervals[i][1]);
        }

        // The size of the heap tells us the minimum rooms required for all the meetings.
        return allocator.size();
    }

    /**
     * 2. Two Pointers, greedy
     *
     * the minimal number of rooms equal to the max number of overlapping meeting in any time point.
     *
     * When we encounter an ending event, that means that some meeting that started earlier has ended now.
     * We are not really concerned with which meeting has ended.
     * All we need is that some meeting ended thus making a room available.
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms2(int[][] intervals) {
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }

        Arrays.sort(start);
        Arrays.sort(end);

        int rooms = 0;
        int startPointer = 0;
        int endPointer = 0;

        while (startPointer < intervals.length) {
            if (start[startPointer] >= end[endPointer]) {
                rooms--;
                endPointer++;
            }

            rooms++;
            startPointer++;
        }

        return rooms;
    }

    /**
     * 3. TreeMap for scheduling problems
     *
     * This is the same approach Elements of Programming Interviews book takes for Meeting Rooms type problems.
     * The idea is that we only need to keep track of "space occupied".
     *
     * Say you have [1,10],[2,12],[14,15] (2 rooms needed, for 1,10 and 2,12 to 14,15).
     *
     * We know 1,10 and 2,12 overlap, because 2,10's start time (2) comes before 1,10's end time (10).
     * So, we can reduce that logic to:
     * increment counter for the number of start times (represents space occupied by each interval),
     * decrement for end times.
     * The maximum value of counter at any given point translates to the number of space occupied (in meeting rooms terms, a room).
     * To facilitate this list of times while keeping track of their status (start vs end),
     * we can use a TreeMap since it keeps track of the natural order of the keys. We will use the time as keys, start/end as value.
     *
     * The TreeMap step will be populated like so:
     *
     * 1(start), 2(start),10(end),12(end), 14(start),15(end).
     *
     * Keep in mind treemap insertion is log(n) time for each element (nlogn to populate with n items)
     *
     * 九章算法的扫描线算法差不多，只不过用的是List(unordered)，所以需要Collections.sort来排序
     *
     * ----
     *
     * My understanding: this method is a bit like Valid Parentheses:
     * we need to pair an open time of meeting with exactly a close time of meeting followed by it.
     * So example 1(start), 2(start),10(end),12(end), 14(start),15(end) -
     * > open, open, close, close, open, close.
     * We can easily see there are two consecutive open, we need 2 meeting rooms.
     *
     * ____
     *
     * Related: 56, 57, 252, 253, 729, 731, 732
     *
     * Time complexity:
     * @param intervals
     * @return
     */
    public int minMeetingRooms3(int[][] intervals) {
        Map<Integer, Integer> changes = new TreeMap<>();
        for (int[] i : intervals) {
            changes.put(i[0], changes.getOrDefault(i[0], 0) + 1);
            changes.put(i[1], changes.getOrDefault(i[1], 0) - 1);
        }
        int room = 0, maxrooms = 0;
        for (int v : changes.values())
            maxrooms = Math.max(maxrooms, room += v);
        return maxrooms;
    }
}
