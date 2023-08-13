package datadog;

import java.util.*;

/**
 * @author lzn
 * @date 2023/08/06 21:50
 * @description 经典面经题, 给定一个array, 每个元素都是一个string array, 输入一个string array, 返回输入对每个给定string array的补集 (需要保证输入里每个单词都在string array里), 直接返回一个array就好.
 *   给定: [['apple', 'facebook', 'google'], ['banana', 'facebook'], ['facebook', 'google', 'tesla', 'apple'], ['intuit', 'google', 'facebook']]
 *   输入: ['apple'], 输出: ['facebook', 'google', 'tesla']
 *   输入: ['facebook', 'google'], 输出: ['apple', 'tesla', 'intuit']
 * <p>
 * Clarify questions:
 * 1. Is it possible for the original string array and the filter array to be null? -> The original string will not be null, if the filter array is null, return all of complement strings
 * 2. Should we consider the uppercase and the lowercase as same? For example, "Apple" equals "apple"? -> We can ignore the difference between upper and lower case letters
 * 3. What if there are some duplicate complement string appeared? Should we keep the duplicate string for the result? -> The output result should not have duplicates
 */
public class ComplementingSet {

    private final Map<String, Map<Integer, Set<Integer>>> invertedIndexMap = new HashMap<>();

//    public String[] findComplementingSet(String[][] oriStrings, String[] filterStrings) {
//        if (oriStrings == null) {
//            throw new RuntimeException("The original string can not be null");
//        }
//        if (filterStrings == null || filterStrings.length == 0) {
//            return (String[]) Arrays.stream(oriStrings).flatMap(Arrays::stream).toArray();
//        }
//        Set<String> filterSet = new HashSet<>(Arrays.asList(filterStrings));
//        Set<String> complementSet = new HashSet<>();
//
//        // O(n2)
//        for (String[] array : oriStrings) {
//            Set<String> arraySet = new HashSet<>(Arrays.asList(array));
//            if (arraySet.containsAll(filterSet)) {
//                complementSet.addAll(arraySet);
//            }
//        }
//
//        // O(n)
//        complementSet.removeAll(filterSet);
//
//        return complementSet.toArray(new String[0]);
//    }

    /**
     * freq:
     * apple: (0, 1), (2, 1), (3, 1)
     * facebook: (0, 1), (1, 1), (2, 1), (3, 1), (4, 1)
     * banana: (1, 1), (3, 1)
     * tesla: (2, 1)
     * intuit: (4, 1)
     * <p>
     * index:
     * apple: 0=[0], 2=[3], 3=[3]
     * facebook: 0=[1], 1=[1], 2=[0], 3=[0], 4=[2]
     * banana: 1=[0], 3=[2]
     * tesla: 2=[2]
     * intuit: 4=[0]
     */
    public String[] findComplementingSet(String[][] oriStrings, String[] filterStrings) {
        if (oriStrings == null) {
            throw new RuntimeException("The original string can not be null");
        }
        if (filterStrings == null || filterStrings.length == 0) {
            return Arrays.stream(oriStrings).flatMap(Arrays::stream).toArray(String[]::new);
        }
        Set<String> complementSet = new HashSet<>();
        for (String filterStr : filterStrings) {
            if (invertedIndexMap.containsKey(filterStr)) {
                Map<Integer, Set<Integer>> indexMap = invertedIndexMap.get(filterStr);
                System.out.println("indexMap : " + indexMap);
                indexMap.forEach((key, values) -> {
                    for (int k = 0; k < oriStrings[key].length; k++) {
                        if (!values.contains(k)) {
                            complementSet.add(oriStrings[key][k]);
                        }
                    }
                });
            }
        }

        complementSet.removeAll(Arrays.asList(filterStrings));

        return complementSet.toArray(new String[0]);
    }

    public void generateInvertedIndexMap(String[][] oriStrings) {
        for (int i = 0; i < oriStrings.length; i++) {
            String[] strings = oriStrings[i];
            for (int j = 0; j < strings.length; j++) {
                invertedIndexMap.putIfAbsent(strings[j], new HashMap<>());
                Map<Integer, Set<Integer>> indexMap = invertedIndexMap.get(strings[j]);
                indexMap.putIfAbsent(i, new HashSet<>());
                indexMap.get(i).add(j);
            }
        }
        System.out.println("invertedIndexMap: " + invertedIndexMap);
    }

    public static void main(String[] args) {
        ComplementingSet complementingSet = new ComplementingSet();
        String[][] oriStrings = new String[5][];

        oriStrings[0] = new String[]{"apple", "facebook", "google"};
        oriStrings[1] = new String[]{"banana", "facebook"};
        oriStrings[2] = new String[]{"facebook", "google", "tesla", "apple"};
        oriStrings[3] = new String[]{"facebook", "google", "banana", "apple", "google", "banana"};
        oriStrings[4] = new String[]{"intuit", "google", "facebook"};

        // Generate inverted index map
        complementingSet.generateInvertedIndexMap(oriStrings);

        String[] filterStrings1 = new String[]{"apple"};
        String[] filterStrings2 = new String[]{"facebook", "google"};

        // [banana, tesla, facebook, google]
        System.out.println(Arrays.toString(complementingSet.findComplementingSet(oriStrings, filterStrings1)));

        // [banana, intuit, apple, tesla]
        System.out.println(Arrays.toString(complementingSet.findComplementingSet(oriStrings, filterStrings2)));
    }
}
