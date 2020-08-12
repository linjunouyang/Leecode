import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Remember string hash time depends on string length
 */
public class _0609FindDuplicateFileInSystem {
    /**
     * 1. HashMap
     *
     * n: number of strings
     * x: average length
     * Time complexity: O(n * x)
     * Space complexity: O(n * x)
     *
     * @param paths
     * @return
     */
    public List<List<String>> findDuplicate(String[] paths) {
        if (paths == null) {
            return new ArrayList<>();
        }

        // File Content -> file paths
        Map<String, List<String>> map = new HashMap<>();

        for (String info : paths) {
            // Find directory name
            String dir = "";
            StringBuilder sb = new StringBuilder();
            int p = 0;
            while (info.charAt(p) != ' ') {
                sb.append(info.charAt(p));
                ++p;
            }
            dir = sb.toString();

            // Process every file and its content
            ++p;
            sb = new StringBuilder();
            sb.append(dir).append('/');
            String lastFile = "";
            while (p < info.length()) {
                char c = info.charAt(p);
                if (c == '(') {
                    lastFile = sb.toString();
                    sb = new StringBuilder();
                } else if (c == ')') {
                    String content = sb.toString();
                    List<String> files = map.getOrDefault(content, new ArrayList<>());
                    files.add(lastFile);
                    map.put(content, files);

                    sb = new StringBuilder();
                    sb.append(dir).append('/');
                } else if (c != ' ') {
                    sb.append(info.charAt(p));
                }
                p++;
            }
        }

        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            if (e.getValue().size() >= 2) {
                res.add(e.getValue());
            }
        }

        return res;
    }

    /**
     * Follow-up
     * [Most frequent question for DropBox]
     * Verbal solution -> Code
     *
     * https://leetcode.com/problems/find-duplicate-file-in-system/discuss/104120/Follow-up-questions-discussion
     * (Code)
     *
     *
     * 1. Imagine you are given a real file system, how will you search files? DFS or BFS?
     *
     * It depends the depth of file system
     *
     * a) for a file system, it's more common to have 100 files stored in one folder, instead of 100 level of directories.
     * In general, DFS takes the same space as BFS, both of which are O(n), regardless the n is height or width.
     * that's why BFS is expected to be faster.
     *
     * b) Also, BFS is easier to parallelize.
     *
     * ? difference of number of cd operations ? dentry cache miss
     *
     * 2. If the file content is very large (GB level), how will you modify your solution?
     * For very large files we should do the following comparisons in this order:
     *  a) Size: DFS/BFS to create Map<Integer: file size, Set: a set of paths>
     *  b) Hash: For each size, if more than 2 files, compute hashCode with MD5 or SHA256
     *  Map<String, Set>: hashcode -> set of filepath_filename
     *  Hash is very big, use BigInteger
     *  c) compare byte by byte to avoid false positives due to collisions.
     *
     *  Optimize b) ??
     * In GFS, it stores large file in multiple "chunks" (one chunk is 64KB).
     * we have meta data, including the file size, file name and index of different chunks along with each chunk's checkSum(the xor for the content).
     * To check duplicates
     * 1) make sure same size 2) compare 1st chunk's checkSum 3) compare 2nd chunk's checkSum
     *
     * Disadvantage: there might be false positive duplicates, because two different files might share the same checkSum.
     *
     * 3. If you can only read the file by 1kb each time, how will you modify your solution?
     * create the hash from the 1kb chunks, and then read the entire file if a full byte by byte comparison is required.
     *
     * 4. What is the time complexity of your modified solution?
     * What is the most time-consuming part and memory consuming part of it? How to optimize?
     *
     * Comparing the file (by size, by hash and eventually byte by byte) is the most time consuming part.
     *
     * Time - Worst case (which is very unlikely to happen): O(N^2 * L)
     * where L is the size of the maximum bytes that need to be compared
     *
     * Generating hash for every file will be the most memory consuming part.
     *
     * Space - Worst case: all files are hashed and inserted in the hashmap, so O(H^2 * L),
     * H is the hash code size and L is the filename size
     *
     * Optimization: better hash algorithm
     *
     * 5. How to make sure the duplicated files you find are not false positive?
     * Multiple Filters: file size -> hash -> byte by byte comparison
     *
     */
}
