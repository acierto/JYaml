package org.ho.yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class JyamlConfigTests {

    @Test
    public void testJyamlYml() throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter("jyaml.yml"));
        out.print("minimalOutput: false\r\n" +
                "indentAmount: \"    \"\r\n" +
                "suppressWarnings: true\r\n" +
                "encoding: \"ISO-8859-1\"\r\n" +
                "transfers:\r\n" +
                "  person: org.ho.yaml.tests.Person\r\n");
        out.close();
        Person p = new Person();
        System.out.println(Yaml.dump(p));
        Assert.assertEquals("--- !person {}\r\n", Yaml.dump(p));
        Assert.assertTrue(new File("jyaml.yml").delete());
    }

}
