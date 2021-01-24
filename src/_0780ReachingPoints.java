public class _0780ReachingPoints {
    /**
     * 1. Brute Force: Recursion (TLE)
     *
     * Go from root to children nodes
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return true;
        }

        boolean res = false;
        if (sx + sy <= tx) {
            res = res || reachingPoints(sx + sy, sy, tx, ty);
        }
        if (sx + sy <= ty) {
            res = res || reachingPoints(sx, sx + sy, tx, ty);
        }

        return res;
    }

    /**
     * 2. Bottom-up:
     *
     * Go up from target left node, see whether root is reachable.
     *
     * Good illustration:
     * https://leetcode.com/problems/reaching-points/discuss/230588/Easy-to-understand-diagram-and-recursive-solution
     *
     * Explanation:
     * https://leetcode.com/problems/reaching-points/discuss/816596/EXPLANATION-or-For-math-dummies-like-me
     *
     * // Why checking (tx - sx) % ty & (ty - sy) % tx
     * Consider a case where sx, sy is (3,2) with sx > sy
     * First few transformations were all (sx+sy, sy) (sx1 + sy, sy) (sx2 + sy, sy) ... this is essentially (sx + n * sy, sy). In our example
     *
     * (sx,sy)                            (tx,ty)
     * (3,2) --> (5,2)--> (7,2)-->(9,2)-->(11,2) which is (3 + 4 * 2, 2) with n = 4
     * If we were to do tx %=ty (11%2) we would get sx = 1 which is not right answer. Hence when sy == ty we need to check if we have a condition (tx = sx + n * sy) or (11 = 3 + 4 * 2) this also means (tx-sx) % sy should be 0 or (11-3) % 2 should be 0.
     *
     * The same reasoning can be applied when sx == tx
     */
    public boolean reachingPoints2(int sx, int sy, int tx, int ty) {
        while(tx >= sx && ty >= sy){
            if(tx > ty) {
                if(sy == ty) {
                    return (tx - sx) % ty == 0;
                } else {
                    tx %= ty;
                }

            } else {
                if(sx == tx) {
                    return (ty - sy) % tx == 0;
                } else {
                    ty %= tx;
                }
            }
        }
        return false;
    }
}

