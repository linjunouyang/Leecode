public class _1095FindInMountainArray {
    class MountainArray {
        int[] arr;

        public int length() {
            return arr.length;
        }

        public int get(int i) {
            return arr[i];
        }
    }

    /**
     * 1. Triple Binary Search
     *
     * Time: O(logn)
     * Space: O(1)
     *
     * Cache the result of get, in case we make the same calls.
     * In sacrifice of O(logN) space for the benefit of less calls.
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        // Find the peak
        int[] peak = findPeak(mountainArr);

        if (peak[1] == target) {
            return peak[0];
        } else if (target > peak[1]) {
            return -1;
        }

        int idx = -1;

        // find target in left
        idx = findTarget(target, mountainArr, 0, peak[0] - 1, true);

        if (idx != -1) {
            return idx;
        }

        // find target in right
        idx = findTarget(target, mountainArr, peak[0] + 1, mountainArr.length() - 1, false);

        return idx;
    }

    // guaranteed to have a peak
    private int[] findPeak(MountainArray mountainArr) {
        int left = 0;
        int right = mountainArr.length() - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return new int[]{left, mountainArr.get(left)};
    }

    private int findTarget(int target, MountainArray mountainArr, int left, int right, boolean goUp) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                if (goUp) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            } else {
                if (goUp) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
        }
        if (mountainArr.get(left) == target) {
            return left;
        } else {
            return -1;
        }
    }
}
