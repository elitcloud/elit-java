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
package cloud.elit.ddr.propbank;

import cloud.elit.sdk.nlp.structure.constituency.CTReader;
import cloud.elit.sdk.nlp.structure.constituency.CTTree;
import cloud.elit.ddr.util.StringConst;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class PBReader {
    private BufferedReader reader;

    public PBReader() {
    }

    /**
     * @param in internally wrapped by {@code new BufferedReader(new InputStreamReader(in))}}.
     */
    public PBReader(InputStream in) {
        open(in);
    }

    /**
     * @param in internally wrapped by {@code new BufferedReader(new InputStreamReader(in))}}.
     */
    public void open(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the next instance if exists; otherwise, {@code null}.
     */
    public PBInstance next() {
        try {
            String line = reader.readLine();
            if (line != null) return new PBInstance(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Int2ObjectMap<List<PBInstance>> getInstanceMap() {
        Int2ObjectMap<List<PBInstance>> map = new Int2ObjectOpenHashMap<List<PBInstance>>();
        List<PBInstance> list;
        PBInstance instance;

        while ((instance = next()) != null) {
            if (map.containsKey(instance.getTreeID()))
                list = map.get(instance.getTreeID());
            else {
                list = new ArrayList<>();
                map.put(instance.getTreeID(), list);
            }

            list.add(instance);
        }

        return map;
    }

    /**
     * @return {@link #getSortedInstanceList(String, boolean)}, where {@code normalize=false}.
     */
    public List<PBInstance> getSortedInstanceList(String treeDir) {
        return getSortedInstanceList(treeDir, false);
    }

    /**
     * @param treeDir   the Treebank directory path.
     * @param normalize if {@code true}, calls {@link CTTree#normalizeIndices()}.
     * @return the sorted list of instances including constituent trees associated with them.
     */
    public List<PBInstance> getSortedInstanceList(String treeDir, boolean normalize) {
        List<PBInstance> list = getSortedInstanceList();
        CTReader reader = new CTReader();
        CTTree tree = null;
        String treeFile = "";
        int treeID = -1;

        for (PBInstance instance : list) {
            if (!instance.isTreePath(treeFile)) {
                treeFile = instance.getTreePath();
                treeID = -1;
                reader.close();
                try {
                    reader.open(new FileInputStream(treeDir + StringConst.FW_SLASH + treeFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            for (; treeID < instance.getTreeID(); treeID++)
                tree = reader.next();

            if (normalize) tree.normalizeIndices();
            instance.setTree(tree);
        }

        return list;
    }

    /**
     * @return the sorted list of instances.
     */
    public List<PBInstance> getSortedInstanceList() {
        List<PBInstance> list = new ArrayList<>();
        PBInstance instance;

        while ((instance = next()) != null)
            list.add(instance);

        close();
        Collections.sort(list);

        return list;
    }
}