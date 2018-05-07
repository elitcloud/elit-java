/*
 * Copyright 2018 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cloud.elit.ddr.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class CharTokenizer {
    private char c_delim;

    public CharTokenizer(char delim) {
        initDelimiter(delim);
    }

    public void initDelimiter(char delim) {
        c_delim = delim;
    }

    public List<String> toList(String s) {
        return toList(s, false);
    }

    public List<String> toList(String s, boolean includeEmpty) {
        List<String> list = new ArrayList<>();
        int i, bIndex = 0, len = s.length();
        char[] cs = s.toCharArray();

        for (i = 0; i < len; i++) {
            if (cs[i] == c_delim) {
                if (bIndex < i) list.add(s.substring(bIndex, i));
                else if (includeEmpty) list.add(StringConst.EMPTY);
                bIndex = i + 1;
            }
        }

        if (list.isEmpty()) {
            list.add(s);
            return list;
        }

        if (bIndex < len)
            list.add(s.substring(bIndex));

        return list;
    }

    public String[] tokenize(String s, boolean includeEmpty) {
        List<String> list = toList(s, includeEmpty);
        return list.toArray(new String[list.size()]);
    }

    /**
     * Not including empty strings.
     */
    public String[] tokenize(String s) {
        return tokenize(s, false);
    }

//	public String[] tokenize(String s, boolean includeDelim)
//	{
//		IntArrayList list = new IntArrayList();
//		char[] cs = s.toCharArray();
//		int d, len = s.length();
//		
//		for (d=0; d<len; d++)
//		{
//			if (cs[d] == c_delim)
//				list.addToken(d);
//		}
//		
//		len = list.size();
//		int j, size = len + 1;
//		if (includeDelim) size += len;
//		String[] array = new String[size];
//		int bIndex = 0, dIndex;
//		
//		for (d=0,j=0; d<len; d++)
//		{
//			dIndex = list.getForm(d);
//			array[j++] = s.substring(bIndex, dIndex);
//			if (includeDelim) array[j++] = Character.toString(c_delim);
//			bIndex = dIndex + 1;
//		}
//		
//		array[j] = s.substring(bIndex);
//		return array;
//	}
}
