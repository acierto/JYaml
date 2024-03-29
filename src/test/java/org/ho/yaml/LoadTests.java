package org.ho.yaml;

import java.io.File;
import java.util.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore // TODO: no resources for test available
public class LoadTests {

    @Test
    public void testLargeFile() throws Exception {
        File f = new File("yml/testLargeFile.yml");
        Yaml.dumpStream(new Iterator() {
            int count = 0;

            public boolean hasNext() {
                return count < 1000000; // about 11M file
            }

            public Object next() {
                return count++;
            }

            public void remove() {

            }

        }, f);
        int count = 0;
        for (Object n : Yaml.loadStream(f)) {
            Assert.assertEquals(count++, n);
        }
    }

    @Test
    public void testLargeDocument() throws Exception {
        File f = new File("yml/testLargeDocument.yml");
        Map<String, List<Integer>> m = new HashMap<>();
        List<Integer> largeList = new ArrayList<>();
        for (int i = 0; i < 100000; i++)
            largeList.add(i);
        m.put("large list", largeList);
        Yaml.dump(m, f);
        Yaml.load(f);
        Assert.assertEquals(100000, ((List) m.get("large list")).size());
    }
}
