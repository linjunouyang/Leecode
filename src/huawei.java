import java.util.ArrayList;
import java.util.Scanner;

public class huawei {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        Scanner sc2 = new Scanner(line);
        String prev = "";
        int length = 0;

        ArrayList<String> t = new ArrayList<>();
        boolean isFirst = true;

        while (sc2.hasNext()) {
            String str = sc2.next();
            t.add(str);
            if (!str.equals("5a")) {
                if (!(str.equals("ba") && prev.equals("5b"))) {
                    length++;
                }
                prev = str;
            } else {
                if (!isFirst) {
                    int len = Integer.valueOf(prev);
                    if (length - 1 == len) {
                        for (int i = 0; i < t.size(); i++) {
                            System.out.print(t.get(i) + " ");
                        }
                    }
                    t.clear();
                    length = 0;
                } else {
                    isFirst = false;
                }
            }
        }



    }
}
