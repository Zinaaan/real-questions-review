package Google;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzn
 * @date 2023/09/04 21:32
 * @description 给一些sentences作为training data, 写一个predict method - 给一个word，return出现在这个word之后频率最高的词。
 * 例如：sentences: [“today is sunny.” “this problem is hard.” “california is sunny.”] predict(“is”) -> “sunny”.
 * followup: fuzzyPredict(word) -> return word based on probability.
 */
public class PredictMethod {

    private String predict(String[] sentences, String word) {
        Map<String, Map<String, Integer>> nextWordMap = new HashMap<>();
        Arrays.stream(sentences).forEach(sentence -> {
            String[] sArray = sentence.replaceAll("\\.", "").split(" ");
            for (int j = 0; j < sArray.length - 1; j++) {
                nextWordMap.putIfAbsent(sArray[j], new HashMap<>());
                Map<String, Integer> freqMap = nextWordMap.get(sArray[j]);
                freqMap.put(sArray[j + 1], freqMap.getOrDefault(sArray[j + 1], 0) + 1);
            }
        });
        System.out.println(nextWordMap);
        if (!nextWordMap.containsKey(word)) {
            return "";
        }
        Map<String, Integer> predictMap = nextWordMap.get(word);
        String predictWord = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : predictMap.entrySet()) {
            if (entry.getValue() >= max) {
                predictWord = entry.getKey();
                max = entry.getValue();
            }
        }
        return predictWord;
    }

    public static void main(String[] args) {

        PredictMethod predictMethod = new PredictMethod();
        // sunny
        String predict = predictMethod.predict(new String[]{"today is sunny.", "this problem is hard.", "california is sunny."}, "is");
        System.out.println(predict);

        // hard
        String predict1 = predictMethod.predict(new String[]{"today is sunny.", "this problem is hard.", "california is sunny.", "this problem is hard.", "this problem is hard."}, "is");
        System.out.println(predict1);

        // problem
        String predict2 = predictMethod.predict(new String[]{"today is sunny.", "this problem is hard.", "california is sunny.", "this problem is hard.", "this problem is hard."}, "this");
        System.out.println(predict2);

        // ""
        String predict3 = predictMethod.predict(new String[]{"today is sunny.", "this problem is hard.", "california is sunny.", "this problem is hard.", "this problem is hard."}, "who");
        System.out.println(predict3);
    }
}
