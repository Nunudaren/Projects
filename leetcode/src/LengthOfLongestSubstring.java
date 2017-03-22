import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

/**
 * Given "abcabcbb", the answer is "abc", which the length is 3.

 * Given "bbbbb", the answer is "b", with the length of 1.

 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * @author Andy
 *
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character,Integer> map = new HashMap();
        int result = 0;
        int len = s.length();
        int j = 0;
        for(int i = 0; i < len; i++) {
            if(map.containsKey(s.charAt(i))){
                j = Math.max(j, map.get(s.charAt(i)));
            }
            map.put(s.charAt(i), i + 1);
            result = Math.max(result, i + 1 - j);
        }
        return result;
    }
    
    @Test
    public void test() {
    	Scanner sc = new Scanner(System.in);
    	String s = sc.nextLine();
    	int result = lengthOfLongestSubstring(s);
    	System.out.println(result);
    }
}