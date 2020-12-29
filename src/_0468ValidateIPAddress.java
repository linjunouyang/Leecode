import java.util.regex.Pattern;

public class _0468ValidateIPAddress {
    /**
     * 1. Regex
     *
     * https://leetcode.com/problems/validate-ip-address/solution/
     *
     * 1. Chunk contains only one digit, from 0 to 9.
     * 2. Chunk contains two digits. The first one could be from 1 to 9, and the second one from 0 to 9.
     * 3. Chunk contains three digits, and the first one is 1. The second and the third ones could be from 0 to 9.
     * 4. Chunk contains three digits, the first one is 2 and the second one is from 0 to 4. Then the third one could be from 0 to 9.
     * 5. Chunk contains three digits, the first one is 2, and the second one is 5. Then the third one could be from 0 to 5.
     *
     *
     * Time: O(1)
     * Space: O(1)
     *
     * Here the patterns are not greedy, i.e. there is no characters like * which could match whatever number of characters. Hence it's enough just to check the pattern length.
     *
     * t's so important to start regex by the "beginning of the string" notation. This way we explicitly tell that we want a match which starts right here, and not a pattern search in the string.
     *
     * I think one way to think of it is like this: "How does the regex based algo scale when you scale your input"? Because the patterns use ^ and $, and there is no greedy quantifier (no * or +) - i.e the regex pattern (ipv4 or ipv6) is of constant length. So if you use an input string of length 100 or even 1 million, it doesn't matter - the regex pattern would at max check for the given pattern length. So essentially the algo running time doesn't increase linearly, in fact it is constant time.
     *
     * Note: This doesn't necessarily mean that the Regex based solution is necessarily faster! It just has better time complexity in Big-O terms!
     * @param IP
     * @return
     */
    String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    Pattern pattenIPv4 =
            Pattern.compile("^(" + chunkIPv4 + "\\.){3}" + chunkIPv4 + "$");

    String chunkIPv6 = "([0-9a-fA-F]{1,4})";
    Pattern pattenIPv6 =
            Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");

    public String validIPAddress(String IP) {
        if (pattenIPv4.matcher(IP).matches()) {
            return "IPv4";
        }
        return (pattenIPv6.matcher(IP).matches()) ? "IPv6" : "Neither";
    }


    /**
     * 2. Iteration
     * @param IP
     * @return
     */
    public String validIPAddress3(String IP) {
        int firstColonIdx = IP.indexOf(':');
        int firstPeriodIdx = IP.indexOf('.');

        if (firstPeriodIdx != -1) {
            return checkIPv4(IP);
        } else if (firstColonIdx != -1) {
            return checkIPv6(IP);
        } else {
            return "Neither";
        }
    }

    private String checkIPv4(String IP) {
        int firstDigitIdx = 0;
        int x = 0;
        int numOfPeriod = 0;
        for (int i = 0; i < IP.length(); i++) {
            char c = IP.charAt(i);
            if (Character.isDigit(c)) {
                if (i == firstDigitIdx + 1 && IP.charAt(i - 1) == '0') {
                    return "Neither";
                }
                x = x * 10 + c - '0';
                if (x > 255) {
                    return "Neither";
                }
            } else if (c == '.') {
                if (i == IP.length() - 1 || i == firstDigitIdx) {
                    return "Neither";
                }
                x = 0;
                numOfPeriod++;
                if (numOfPeriod == 4 && i < IP.length() - 1) {
                    return "Neither";
                }
                firstDigitIdx = i + 1;
            } else {
                return "Neither";
            }
        }

        if (x > 255 || numOfPeriod != 3) {
            return "Neither";
        }

        return "IPv4";
    }

    private String checkIPv6(String IP) {
        int xLen = 0;
        int numOfColon = 0;
        for (int i = 0; i < IP.length(); i++) {
            char c = IP.charAt(i);
            if (Character.isDigit(c) || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F')) {
                xLen++;
                if (xLen == 5) {
                    return "Neither";
                }
            } else if (c == ':') {
                if (xLen == 0) {
                    return "Neither";
                }
                numOfColon++;
                if (i == IP.length() - 1 || numOfColon == 8) {
                    return "Neither";
                }
                xLen = 0;
            } else {
                return "Neither";
            }
        }

        if (numOfColon < 7) {
            return "Neither";
        }
        return "IPv6";
    }

    /**
     * Cleanerr
     *
     * String.split("\\.", -1);
     *
     * Integer.parseInt(a, 16), NumberFormatException
     *
     */
    public String validIPAddress4(String IP) {
        if(isIPv4(IP)) return "IPv4";
        else if(isIPv6(IP)) return "IPv6";
        else return "Neither";
    }
    private boolean isIPv4(String ip) {
        String[] arr = ip.split("\\.", -1);
        for(String a: arr) {
            try{
                if(Integer.parseInt(a)>255 || (a.charAt(0)=='0' && a.length()!=1)) return false;
            } catch (NumberFormatException e) { return false; }
        }
        return arr.length == 4;
    }
    private boolean isIPv6(String ip) {
        String[] arr = ip.split(":", -1);
        for(String a: arr) {
            try{
                if(Integer.parseInt(a, 16)>65535 || a.length()>4) return false;
            } catch (NumberFormatException e) { return false; }
        }
        return arr.length == 8;
    }
}
