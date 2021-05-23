import java.util.*;

/**
 * Similar:
 * Merge K Sorted List
 *
 * 757 Set Intersection Size At Least Two
 * 56
 * 729
 */
public class _0759EmployeeFreeTime {
    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };

    /**
     * 1. Greedy, sort arraylist of all intervals
     *
     * Time: O(c logc)
     * Space:
     * sort: O(logc) to O(c)
     * timeLine: O(c)
     */
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> result = new ArrayList<>();
        List<Interval> work = new ArrayList<>();
        schedule.forEach(e -> result.addAll(e));
        //Collections.sort(timeLine, ((a, b) -> a.start - b.start));

        Interval temp = work.get(0);
        for (Interval each : work) {
            if (temp.end < each.start) {
                result.add(new Interval(temp.end, each.start));
                temp = each;
            } else {
                temp = temp.end < each.end ? each : temp;
            }
        }
        return result;
    }

    /**
     * 2. PQ
     *
     * Worse version:
     * https://leetcode.com/problems/employee-free-time/discuss/650527/Java-PriorityQueue-loud-and-clear
     *
     * n: number of intervals, k: number of people
     * Time: O(nlogk)
     * Space: O(k)
     */
    public List<Interval> employeeFreeTime2(List<List<Interval>> schedule) {
        // sort employee in the non-decreasing order of the starting time of his/her first unvisited interval
        PriorityQueue<int[]> allEmployees = new PriorityQueue<>((a, b) -> schedule.get(a[0]).get(a[1]).start - schedule.get(b[0]).get(b[1]).start);

        for (int i = 0; i < schedule.size(); i++) {
            // int[] {employeeId, first unvisited interval id for this employee}
            allEmployees.add(new int[] { i, 0 });
        }

        List<Interval> employeesFreeTime = new ArrayList<>();

        int[] prev = allEmployees.peek();

        while (!allEmployees.isEmpty()) {
            // during first time prev = curr -> add({sameId, i)

            int[] current = allEmployees.poll();

            Interval previousInterval = schedule.get(prev[0]).get(prev[1]);
            Interval currentInterval = schedule.get(current[0]).get(current[1]);

            if (previousInterval.end < currentInterval.start) {
                employeesFreeTime.add(new Interval(previousInterval.end, currentInterval.start));
            }

            if (previousInterval.end < currentInterval.end) {
                prev = current;
            }

            if (current[1] + 1 < schedule.get(current[0]).size()) {
                allEmployees.add(new int[] { current[0], current[1] + 1 });
            }
        }

        return employeesFreeTime;
    }

    /**
     * 3. Merge Sort
     *
     * Time complexity: O(number of intervals * log (number of employee))
     *
     */
    public List<Interval> employeeFreeTime3(List<List<Interval>> schedule) {
        int n=schedule.size();
        List<Interval> time=mergeSort(schedule, 0, n-1);
        List<Interval> free=new ArrayList<>();
        int end=time.get(0).end;
        for(int i=1;i<time.size();i++) {
            if(time.get(i).start>end) {
                free.add(new Interval(end, time.get(i).start));
            }
            end=Math.max(end, time.get(i).end);
        }
        return free;
    }

    private List<Interval> mergeSort(List<List<Interval>> schedule, int l, int r) {
        if(l==r) return schedule.get(l);
        int mid=(l+r)/2;
        List<Interval> left=mergeSort(schedule, l, mid);
        List<Interval> right=mergeSort(schedule, mid+1, r);
        return merge(left, right);
    }

    private List<Interval> merge (List<Interval> A, List<Interval> B) {
        List<Interval> res=new ArrayList<>();
        int m=A.size(), n=B.size();
        int i=0, j=0;
        while(i<m||j<n) {
            if(i==m) {
                res.add(B.get(j++));
            }
            else if(j==n) {
                res.add(A.get(i++));
            }
            else if(A.get(i).start<B.get(j).start) {
                res.add(A.get(i++));
            }
            else res.add(B.get(j++));
        }
        return res;
    }

    /**
     * 4. Sweep-line
     * https://leetcode.com/problems/employee-free-time/discuss/175081/Sweep-line-Java-with-Explanations
     *
     * I am curious why this O(N) algorithm is not the top 3 voted one.. It is good to know "merge intervals" or "Priority queue" solution, but this one has a potential to reach O(N+M) if we modified it a bit. (M is the difference between the min and max of the hashset key). In this case, M (10**8 -1) is much greater than Nlog(N) (50log(50)), thus the other two algos win in actual time cost.
     *
     * Personally speaking, why this question is marked as "Hard".. Especially when this hard question is after 757. Set Intersection Size At Least Two..
     *
     */
    // More efficient
    public List<Interval> employeeFreeTime41(List<List<Interval>> schedule) {
        int FREEEND = -1, FREESTART = 1;

        List<int[]> events = new ArrayList<>();
        for (List<Interval> employee: schedule)
            for (Interval iv: employee) {
                events.add(new int[]{iv.start, FREEEND});
                events.add(new int[]{iv.end, FREESTART});
            }

        // a[0] - b[0] or a[0] - b[0] : b[1]-a[1] won't work
        // Suppose right before 38min, almost everyone is not working except A
        // at this 38min, A stop working and B starts working. [38, 38] is not valid free time
        Collections.sort(events, (a, b) -> a[0] != b[0] ? a[0]-b[0] : a[1]-b[1] );
        List<Interval> ans = new ArrayList<>();

        int prev = -1; // last event timestamp
        int bal = 0; // number of active working interval
        for (int[] event: events) {
            // event[0] = time, event[1] = command
            if (bal == 0 && prev >= 0)
                ans.add(new Interval(prev, event[0]));
            bal += event[1] == FREEEND ? 1 : -1;
            prev = event[0];
        }

        return ans;
    }

    public List<Interval> employeeFreeTime4(List<List<Interval>> schedule) {
        List<Interval> result = new ArrayList<>();
        // time point -> score
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (List<Interval> list : schedule) {
            for (Interval interval : list) {
                map.put(interval.start, map.getOrDefault(interval.start, 0) + 1);
                map.put(interval.end, map.getOrDefault(interval.end, 0) - 1);
            }
        }

        int start = -1, score = 0;
        for (int point : map.keySet()) {
            score += map.get(point);
            if (score == 0 && start == -1) {
                start = point;
            } else if (start != -1 && score != 0) {
                result.add(new Interval(start, point));
                start = -1;
            }
        }

        return result;
    }
}
