package no.steria.kata.texas;

import java.util.List;

public class CompareUtils {
    public static int compareMany(int... compareResults) {
        for (int compareResult : compareResults) {
            if (compareResult != 0) return compareResult;
        }
        return 0;
    }

    public static<T extends Comparable<T>> int compareLists(List<T> a, List<T> b) {
        if (a == null) return b == null ? 0 : -1;
        if (b == null) return 1;
        for (int i=0; i< Math.min(a.size(), b.size()); i++) {
            if (a.get(i).compareTo(b.get(i)) != 0) {
                return a.get(i).compareTo(b.get(i));
            }
        }
        return a.size() - b.size();
    }

}
