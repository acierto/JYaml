package org.ho.yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.apache.commons.io.FileUtils;
import org.junit.*;

public class JyamlConfigTests {

    private File jyamlFile;

    @Before
    public void setUp() {
        jyamlFile = new File(FileUtils.getTempDirectory(), "jyaml.yml");
    }

    @After
    public void tearDown() {
        FileUtils.deleteQuietly(jyamlFile);
    }

    @Test
    public void testJyamlYml() throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(jyamlFile));
        out.print("minimalOutput: false\r\n" +
                "indentAmount: \"    \"\r\n" +
                "suppressWarnings: true\r\n" +
                "encoding: \"ISO-8859-1\"\r\n" +
                "transfers:\r\n" +
                "  person: org.ho.yaml.tests.Person\r\n");
        out.close();
        Person p = new Person();
        System.out.println(Yaml.dump(p));
        Assert.assertEquals("--- !org.ho.yaml.Person {}".trim(), Yaml.dump(p).trim());
    }

}
