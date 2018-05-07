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
package cloud.elit.ddr.constituency;

import cloud.elit.sdk.structure.node.Arc;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class CTArc extends Arc<CTNode> {
    public CTArc(CTNode node, String label) {
        super(node, label);
    }

    public CTArc(CTArc arc) {
        this(arc.node, arc.label);
    }
}