import java.util.ArrayList;
import java.util.List;

public class _0093RestoreIPAddresses {
    /**
     * 1. Backtracking, StringBuilder
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList();
        if(s.length() > 12) {
            // not necessary
            return list;
        }

        helper(s, list, 0, new StringBuilder(), 0);
        return list;
    }

    void helper(String s, List<String> list, int pos, StringBuilder sb, int sec){
        if (sec > 4) {
            // not necessary, speed up
            return;
        }

        if(sec == 4 && pos == s.length()) {
            list.add(sb.toString());
            return;
        }

        for(int i = 1; i <= 3; i++){
            if(pos + i > s.length()) {
                break;
            }
            String section = s.substring(pos, pos + i);
            if(section.length() > 1 && section.startsWith("0") || Integer.parseInt(section) >= 256)  {
                break;
            }
            if (sec == 0) {
                sb.append(section);
                helper(s, list, pos + i, sb, sec + 1);
                sb.delete(0, sb.length());
            } else {
                sb.append('.').append(section);
                helper(s, list, pos + i, sb, sec + 1);
                sb.delete(sb.length() - section.length() - 1, sb.length());
            }
        }
    }

    /**
     * Backtracking with fixed size char array
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses2(String s) {
        char[] temp = new char[s.length() + 3];
        List<String> out = new ArrayList<String>();
        recurse(s, 0, 0, 0, 3, temp, out);
        return out;
    }

    public void recurse(String s, int pos, int outPos, int curOctVal, int remOctets, char[] temp, List<String> out) {
        if(pos == s.length()) {
            if(remOctets == 0) out.add(new String(temp));
            return;
        }

        curOctVal = curOctVal * 10 + (s.charAt(pos) - '0');
        if(curOctVal > 255) {
            return;
        }
        temp[outPos] = s.charAt(pos);
        if(curOctVal != 0 || pos == s.length()-1) {
            recurse(s, pos+1, outPos+1, curOctVal, remOctets, temp, out);
        }
        if(remOctets > 0 && pos < s.length()-1) {
            temp[outPos+1] = '.';
            curOctVal = 0;
            recurse(s, pos+1, outPos+2, curOctVal, remOctets - 1, temp, out);
        }
    }

}
