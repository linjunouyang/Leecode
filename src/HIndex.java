public class HIndex {
    public int hIndex(int[] citations) {
        int length = citations.length;

        int[] count = new int[length + 1];

        for (int i = 0; i < length; i++) {
            if (citations[i] > length) {
                count[length]++;
            } else {
                count[citations[i]]++;
            }
        }

        int sum = 0;

        for (int i = length; i >= 0; i--) {
            sum += count[i];
            if (sum >= i) {
                return i;
            }
        }

        return 0;
    }
}
