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
package cloud.elit.ddr.conversion;

import cloud.elit.ddr.constituency.CTNode;
import cloud.elit.ddr.constituency.CTTree;
import cloud.elit.ddr.conversion.headrule.HeadRule;
import cloud.elit.ddr.conversion.headrule.HeadRuleMap;
import cloud.elit.ddr.util.DDGTag;
import cloud.elit.ddr.util.StringUtils;
import cloud.elit.sdk.structure.Sentence;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class DefaultC2DConverter extends C2DConverter {
    public DefaultC2DConverter(HeadRuleMap headrule_map) {
        super(headrule_map);
    }

    @Override
    public Sentence toDependencyGraph(CTTree tree) {
        setHead(tree.getRoot());
        finalizeDependencies(tree.getRoot());
        return createDependencyGraph(tree);
    }

    @Override
    protected void findHead(CTNode node, HeadRule rule) {
        CTNode head = findHeadDefault(node.getChildren(), rule);
        node.setPhraseHead(head);
    }

    @Override
    protected int getHeadFlag(CTNode node) {
        if (node.hasPrimaryHead())
            return -1;

        if (node.isEmptyCategoryPhrase() || (node.isTerminal() && StringUtils.containsPunctuationOnly(node.getForm())))
            return 1;

        return 0;
    }

    @Override
    protected String getDependencyLabel(CTNode curr, CTNode head) {
        return DDGTag.DEP;
    }
}
