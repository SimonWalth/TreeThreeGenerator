package help;

import java.util.LinkedList;
import java.util.List;

public class StringListOperations {

    /**
     * @param num        StartNumber of wanted strings
     * @param stringList StringList that is searched
     * @return StringList with Strings that start with num
     */

    public static List<String> getElementsThatStartWithNumber(int num, List<String> stringList) {
        List<String> newStringList = new LinkedList<>();
        String numString = String.valueOf(num);
        for (String s : stringList) {
            if (s.startsWith(numString)) {
                newStringList.add(s);
            }
        }
        return newStringList;
    }

    /**
     * @param num        max StartNumber of wanted strings
     * @param stringList StringList that is searched
     * @return StringList with Strings that start with num and numbers smaller
     */

    public static List<String> getElementsThatStartWithNumberSmaller(int num, List<String> stringList) {
        List<String> newStringList = new LinkedList<>();
        for (int n = 1; n <= num; n++) {
            String numString = String.valueOf(n);
            for (String s : stringList) {
                if (s.startsWith(numString)) {
                    newStringList.add(s);
                }
            }

        }
        return newStringList;
    }

}
