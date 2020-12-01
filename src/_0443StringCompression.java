public class _0443StringCompression {
    /**
     * 1. Two Pointer
     *
     * Time: O(n)
     * Space: O(logn) from Integer.toString
     *
     * @param chars
     * @return
     */
    public int compress(char[] chars) {
        int indexAns = 0, index = 0;
        while(index < chars.length){
            char currentChar = chars[index];
            int count = 0;
            while(index < chars.length && chars[index] == currentChar){
                index++;
                count++;
            }
            chars[indexAns++] = currentChar;
            if(count != 1)
                for(char c : Integer.toString(count).toCharArray())
                    chars[indexAns++] = c;
        }
        return indexAns;
    }
}
