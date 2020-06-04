package com.decagon.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tunde Michael <tm@tundemichael.com>
 * @Date Jun 3, 2020
 * @Time 8:55:29 PM
 * @Quote To code is human, to debug is coffee.
 *
 */
public class SocksLaundering {

//    public static void main(String[] args) {
//        try {
//            int max = 2;
//            int[] clean = {5, 5, 1, 2}; // {1, 2, 1, 1};
//            int[] dirty = {1, 4, 3, 2, 4};
//            int ans = solution(max, clean, dirty);
//            System.out.println("===============Ans=============");
//            System.out.println(ans);
//        } catch (Exception ex) {
//            Logger.getLogger(SocksLaundering.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static int solution(int max, int[] clean, int[] dirty) throws Exception {
        if (max < 0 || clean.length == 0 || dirty.length == 0) {
            // throw new Exception("Invalid parameters");
            return 0;
        }
        if (max > 50 || clean.length > 50 || dirty.length > 50) {
            // throw new Exception("Invalid parameters");
            return 0;
        }
        // Edge case 'machine faulty', no of pairs of socks is 1
        if (max == 0) {
            return 1;
        }
        Map<Integer, Integer> cleanMap = new HashMap<>();
        int pairs = 0;
        for (int i : clean) {
            Integer v = cleanMap.get(i);
            if (v == null) {
                cleanMap.put(i, 1);
            } else if (v == 1) {
                cleanMap.put(i, 2);
            } else if (v == 2) {
                pairs = pairs + 1;
                cleanMap.put(i, 1);
            }
        }
        // Edge case 'maximum number allowed' of pairs of socks is 4
        if (pairs >= 4) {
            return 4;
        }
        Arrays.sort(dirty);
        List<Integer> processedIndices = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : cleanMap.entrySet()) {
            Integer k = e.getKey();
            Integer v = e.getValue();
            // Edge case 'maximum number allowed' of pairs of socks is 4
            if (v == 2 && pairs < 4) {
                pairs = pairs + 1;
            } else {
                int index = Arrays.binarySearch(dirty, k);
                // Edge case 'maximum number allowed' of pairs of socks is 4
                if (index >= 0 && max > 0 && pairs < 4) {
                    pairs = pairs + 1;
                    max = max - 1;
                    processedIndices.add(index);
                }
            }
        }
        // Edge case 'maximum number allowed' of pairs of socks is 4
        if (pairs >= 4) {
            return 4;
        }
        // Max-ed out the number of wash
        if (max < 1) {
            return pairs;
        }
        // We need to select max-pair possible from dirty
        Collections.sort(processedIndices);
        Map<Integer, Integer> dirtyMap = new HashMap<>();
        for (int i : dirty) {
            int index = Collections.binarySearch(processedIndices, i);
            if (index >= 0) {
                // Index already paired, jump
                continue;
            }
            // Max-ed out the number of wash
            if (max < 1) {
                return pairs;
            }
            // Edge case 'maximum number allowed' of pairs of socks is 4
            if (pairs >= 4) {
                return 4;
            }
            Integer v = dirtyMap.get(i);
            if (v == null) {
                dirtyMap.put(i, 1);
            } else if (v == 1) {
                dirtyMap.put(i, 2);
            } else if (v == 2) {
                pairs = pairs + 1;
                dirtyMap.put(i, 1);
                max = max - 1;
            }
        }
        for (Map.Entry<Integer, Integer> e : dirtyMap.entrySet()) {
            Integer v = e.getValue();
            // Edge case 'maximum number allowed' of pairs of socks is 4
            if (v == 2 && pairs < 4 && max > 0) {
                pairs = pairs + 1;
                max = max - 1;
            }
        }
        return pairs;
    }

}
