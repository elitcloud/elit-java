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
package cloud.elit.ddr.dictionary;

import java.io.InputStream;
import java.util.Set;
import cloud.elit.ddr.util.DSUtils;
import cloud.elit.ddr.util.IOUtils;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class Abbreviation {
    private Set<String> s_period;

    public Abbreviation() {
        String filename = Dictionary.ROOT + "abbreviation-period.txt";
        init(IOUtils.getInputStreamsFromResource(filename));
    }

    public Abbreviation(InputStream abbreviationPeriod) {
        init(abbreviationPeriod);
    }

    public void init(InputStream abbreviationPeriod) {
        s_period = DSUtils.createStringHashSet(abbreviationPeriod, true, true);
    }

    public boolean isAbbreviationEndingWithPeriod(String lower) {
        return s_period.contains(lower);
    }
}
