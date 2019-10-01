package Lintcode;

import java.util.Arrays;

/**
 * Let's try to guess complexity first:
 *
 * Basic Assumption: related to n and k
 *
 * Analysis:
 * If k = 1, no operations needed.
 * If k = 2, one partition. O(n) = O(nlg2)
 * If k = n, this problem equals to sorting an array. O(nlgn)
 *
 * -> Time complexity could be O(nlgk)
 *
 * -------------
 * How does lgk appear?
 *
 * Think of [Merge Sort], (0 - n/2) (n/2 + 1 - n), we keep chopping each space
 * (Also similar: Trie tree)
 *
 * We might just keep dividing k by 2
 *
 *     0-k/2  k/2+1 - k
 *    /   \   /       \
 *
 *  Total levels: lgk
 *  Time spent on each level: n (partition)
 *
 */
public class _143SortColorsII {
    /**
     * 1. Rainbow Sort, Two pointers;
     *
     * Time complexity: O(nlgk)
     * Space complexity: O(lgk)
     *
     * @param colors
     * @param k
     */
    public void sortColors(int[] colors, int k) {
        if (colors == null) {
            return;
        }

        rainbowSort(colors, 0, colors.length - 1, 1, k);
    }

    private void rainbowSort(int[] colors, int left, int right, int startCol, int endCol) {
        if (left >= right || startCol >= endCol) {
            return;
        }

        int midCol = startCol + (endCol - startCol) / 2;
        int lastFrontHalfIndex = left - 1;

        for (int i = left; i <= right; i++) {
            if (colors[i] <= midCol) {
                lastFrontHalfIndex++;
                swap(colors, lastFrontHalfIndex, i);
            }
        }

        rainbowSort(colors, left, lastFrontHalfIndex, startCol, midCol);
        rainbowSort(colors, lastFrontHalfIndex + 1, right, midCol + 1, endCol);
    }

    private void swap(int[] colors, int i, int j) {
        int temp = colors[i];
        colors[i] = colors[j];
        colors[j] = temp;
    }

    /**
     * 2. Another implementation as solution 1
     *
     * Time complexity: O(nlgk)
     * Space complexity: O(lgk)
     *
     */
    public void sortColors2(int[] colors, int k) {
        if (colors == null) {
            return;
        }

        rainbowSort2(colors, 0, colors.length - 1, 1, k);
    }

    private void rainbowSort2(int[] colors, int left, int right, int colorFrom, int colorTo) {
        if (colorFrom == colorTo || left >= right) {
            return;
        }

        int colorMid = colorFrom + (colorTo - colorFrom) / 2;
        int l = left;
        int r = right;

        while (l <= r) {
            while (l <= r && colors[l] <= colorMid) {
                l++;
            }

            while (l <= r && colors[r] > colorMid) {
                r--;
            }

            if (l <= r) {
                int temp = colors[l];
                colors[l] = colors[r];
                colors[r] = temp;

                l++;
                r--;
            }
        }

        rainbowSort(colors, left, r, colorFrom, colorMid);
        rainbowSort(colors, l, right, colorMid + 1, colorTo);
    }

    /**
     * 1. Quick sort
     *
     * Time complexity: O(nlgn)
     * Space complexity: o(logn)
     *
     * @param colors
     * @param k
     */
    public void sortColors3(int[] colors, int k) {
        Arrays.sort(colors);
    }

    /**
     * 2. Count sort
     *
     * Time complexity: O(n)
     * Space complexity: O(k)
     *
     * @param colors
     * @param k
     */
    public void sortColors4(int[] colors, int k) {
        int[] count = new int[k + 1];

        for (int color: colors) {
            count[color]++;
        }

        int index = 0;

        for (int color = 1; color <= k; color++) {
            for (int t = 0; t < count[color]; t++) {
                colors[index++] = color;
            }
        }
    }

    /**
     * 3. K times partitions
     *
     * Time complexity: O(nk)
     * Space complexity: O(1)
     *
     */
    public void sortColors5(int[] colors, int k) {
        int start = 0;

        for (int i = 1; i <= k; i++) {
            // two ways to write parition
            start = partition1(colors, start, colors.length - 1, i);
        }
    }

    // least favorite, long, hard to follow
    private int partition1(int[] colors, int start, int end, int c) {
        // colors[start] got covered by colors[left++] = colors[right]
        int temp = colors[start];

        int left = start;
        int right = end;

        while (left < right) {
            // while (two pointers inBound && (from right) first c color)
            while (left < right && colors[right] != c) {
                right--;
            }

            if (left < right) {
                colors[left++] = colors[right];
            }

            // while (two pointers inBound && (from left) first non c color)
            while (left < right && colors[left] == c) {
                left++;
            }

            if (left < right) {
                colors[right--] = colors[left];
            }
        }

        colors[left] = temp;

        if (temp == c) {
            return left + 1;
        } else {
            return left;
        }
    }

    // favorite, short, easy to follow
    private int partition2(int[] colors, int start, int end, int c) {
        int lastCIndex = start - 1;

        for (int i = start; i <= end; i++) {
            if (colors[i] == c) {
                lastCIndex++;

                int temp = colors[lastCIndex];
                colors[lastCIndex] = colors[i];
                colors[i] = temp;
            }
        }

        return lastCIndex + 1;
    }

    private int parition3(int[] colors, int start, int end, int c) {
        int left = start;
        int right = end;

        // hard to decide <= or <
        while (left <= right) {
            if (left <= right && colors[left] == c) {
                left++;
            }

            if (left <= right && colors[right] != c) {
                right--;
            }

            if (left <= right) {
                int temp = colors[left];
                colors[left] = colors[right];
                colors[right] = temp;
            }
        }

        // hard to decide left or left + 1;
        return left;
    }
}
