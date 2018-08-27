public class GasStation {
   public int canCompleteCircuit(int[] gas, int[] cost) {
       if (gas.length == 0 || cost.length == 0 || gas.length != cost.length) return -1;
       int start = 0, total = 0, sum = 0;

       for (int i = 0; i < gas.length; i++) {
           total += gas[i] - cost[i];
           if (sum < 0) {
               start = i;
               sum = gas[i] - cost[i];
           } else {
               sum += gas[i] - cost[i];
           }
       }

       return total < 0 ? -1 : start;
   }

    public static class ContainerWithMostWater {
        public int maxArea(int[] height) {
            int maxArea = 0, l = 0, r = height.length - 1;

            while (l < r) {
                maxArea = Math.max(maxArea, Math.min(height[l], height[r]) * (r - l));
                if (height[l] < height[r]) {
                    l++;
                } else {
                    r--;
                }
            }

            return maxArea;
        }
    }
}
