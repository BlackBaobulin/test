/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.action.get;

import java.util.HashMap;
import java.util.Map;

public class Solution {
        public static int lengthOfLongestSubstring(String s) {
            int maxLength = 0;
            int length = s.length();
            if(length >0 ){
                maxLength = 1;
            }
            int lastCur = 0;
            Map<String,Integer> map = new HashMap<>();
            for (int i = 0; i < length; i++) {
                Integer old = map.put(s.charAt(i) + "", i);
                if(old != null){
                    maxLength = Math.max(maxLength,i-old);
                    lastCur = i;
                }
                if(i == length -1){
                    maxLength = Math.max(maxLength,i-lastCur+1);
                }
            }
            return maxLength;
        }

    public static void main(String[] args) {
        int abcabcbb = lengthOfLongestSubstring("abcabcbb");
        System.err.println(abcabcbb);
    }
}
