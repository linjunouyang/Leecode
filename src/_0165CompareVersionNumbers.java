public class _0165CompareVersionNumbers {
    /**
     * 1. Two Passes
     *
     * instead of thinking one is longer than the other
     * we can pad the shorter one to make them of same length -> simpler code
     *
     * Time: O(len1 + len2 + max(len1 + len2)
     * Space: O(len1 + len2)
     */
    public int compareVersion(String version1, String version2) {
        String[] nums1 = version1.split("\\.");
        String[] nums2 = version2.split("\\.");
        int n1 = nums1.length, n2 = nums2.length;

        // compare versions
        int i1, i2;
        for (int i = 0; i < Math.max(n1, n2); ++i) {
            i1 = i < n1 ? Integer.parseInt(nums1[i]) : 0;
            i2 = i < n2 ? Integer.parseInt(nums2[i]) : 0;
            if (i1 != i2) {
                return i1 > i2 ? 1 : -1;
            }
        }

        // the versions are equal
        return 0;
    }

    /**
     * 2. One Pass
     *
     * Be aware of idx change
     */
    public int compareVersion2(String version1, String version2) {
        // can't use Integer, since Integer is immutable
        int idx1 = 0;
        int idx2 = 0;

        while (idx1 < version1.length() || idx2 < version2.length()) {
            int[] res1 = getNextRevision(version1, idx1);
            int[] res2 = getNextRevision(version2, idx2);

            if (res1[0] > res2[0]) {
                return 1;
            } else if (res1[0] < res2[0]) {
                return -1;
            }
            idx1 = res1[1];
            idx2 = res2[1];
        }
        // same length
        return 0;
    }

    private int[] getNextRevision(String version, int startIdx) {
        if (startIdx >= version.length()) {
            // padding the short one
            return new int[]{0, startIdx};
        }
        int idx = startIdx;
        int revision = 0;
        while (idx < version.length() && version.charAt(idx) != '.') {
            int digit = version.charAt(idx) - '0';
            revision = revision * 10 + digit;
            idx++;
        }
        return new int[]{revision, idx + 1};
    }
}
