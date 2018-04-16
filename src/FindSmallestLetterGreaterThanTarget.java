public class FindSmallestLetterGreaterThanTarget {

    /**
     * Approach #1: Store visited elements
     * Time Complexity: O(N), where N is the length of letters. We scan
     * every element of the array.
     * Space Complexity: O(1), the maximum size of seen.
     * @param letters
     * @param target
     * @return
     */
    public char nextGreatestLetter1(char[] letters, char target) {
        boolean[] seen = new boolean[26];
        for (char c: letters) {
            seen[c - 'a'] = true;
        }

        while (true) {
            target++;
            if (target > 'z') target = 'a';
            if (seen[target - 'a']) return target;
        }
    }

    /**
     * Approach #2: Linear Scan
     * Time Complexity: O(N), where N is the length of letters. We scan
     * every element of the array.
     * Space Complexity: O(1), as we maintain only pointers.
     * @param letters
     * @param target
     * @return
     */
    public char nextGreatestLetter2(char[] letters, char target) {
        for (char c: letters) {
            if (c > target) return c;
        }
        return letters[0];
    }

    /**
     * Approach #3: Binary Search
     * Time Complexity: O(log N), as we only peek at log N elements
     * in the array.
     * Space Complexity: O(1), as we only maintain pointers
     * @param letters
     * @param target
     * @return
     */
    public char nextGreatestLetter3(char[] letters, char target) {
        int start = 0;
        int end = letters.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (letters[mid] <= target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (letters[start] > target) {
            return letters[start];
        }
        if (letters[end] > target) {
            return letters[end];
        }
        return letters[0];
    }
}
