package Amazon;

import java.util.HashMap;

/**
 * @author lzn
 * @date 2023/04/19 13:41
 * @description
 */
public class MatchingScore {

    /**
     * times: outside loop * nested loop + hash map iterate = O(n) * O(n / p) + O(k) = O(n2)
     * space: Use hash map to store the character and frequency, so space complexity = o(n)
     * <p>
     * step1: Traverse username1 from the start to the end
     * step2: Use nested loop and move p steps in each round
     * step3: Save the character of current index and the frequency to the hash map
     * step4: Compare the character in hash map with the username2, if it can be rearranged to username2, add the count to the result
     * otherwise continue to the next loop
     *
     * @param username1
     * @param username2
     * @param p
     * @return
     */
    public int getMatchingScore(String username1, String username2, int p) {
        int n = username1.length(), m = username2.length();
        if (n < m) {
            return 0;
        }
        HashMap<String, Integer> mapForUserIds = new HashMap<>();
        char[] user2Ids = new char[26];
        for (int i = 0; i < m; i++) {
            user2Ids[username2.charAt(i) - 'a']++;
        }
        String user2Id = new String(user2Ids);
        mapForUserIds.put(user2Id, 0);
        for (int i = 0; i <= n - m; i++) {
            char[] user1Ids = new char[26];
            int count = 0;
            for (int j = i; j < n; j += p) {
                if (count == m) {
                    break;
                }
                count++;
                user1Ids[username1.charAt(j) - 'a']++;
            }
            String user1Id = new String(user1Ids);
            if (mapForUserIds.containsKey(user1Id)) {
                mapForUserIds.put(user1Id, mapForUserIds.get(user1Id) + 1);
            }
        }
        return mapForUserIds.get(user2Id);
    }

    public static void main(String[] args) {
        MatchingScore matchingScore = new MatchingScore();
        //2
        System.out.println(matchingScore.getMatchingScore("acaccaa", "aac", 2));
        //0
        System.out.println(matchingScore.getMatchingScore("ac", "aac", 2));
        //2
        System.out.println(matchingScore.getMatchingScore("acaccaa", "aac", 1));
        //4
        System.out.println(matchingScore.getMatchingScore("acaccaa", "a", 1));
    }
}
