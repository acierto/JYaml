package org.ho.yaml;

import java.util.List;
import java.util.Map;
import org.ho.yaml.exception.YamlException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BadYamlTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testShouldBeBadValuesInMap() {
        exception.expect(RuntimeException.class);
        String yamlText =
                "map:\n" +
                        "one\n" +
                        "two\n";
        Yaml.load(yamlText);
    }

    @Test
    public void testShouldBeBadValuesInList() {
        exception.expect(RuntimeException.class);
        String yamlText =
                "- one\n" +
                        "one\n" +
                        "two\n";
        Yaml.load(yamlText);
    }

    @Test
    public void testJavascript() throws Exception {
        exception.expect(YamlException.class);
        Yaml.load(getClass().getResourceAsStream("/bad_yaml/javascript.js"));
    }

    @Test
    public void testXML() throws Exception {
        exception.expect(YamlException.class);
        Yaml.load(getClass().getResourceAsStream("/bad_yaml/some.xml"));
    }

    @Test
    public void testProperties() throws Exception {
        exception.expect(YamlException.class);
        Yaml.load(getClass().getResourceAsStream("/bad_yaml/properties.properties"));
    }

    @Test
    public void testIni() throws Exception {
        exception.expect(YamlException.class);
        Yaml.load(getClass().getResourceAsStream("/bad_yaml/ini.ini"));
    }

    @Test
    public void testSharonYml() throws Exception {
        Object o = Yaml.load(getClass().getResourceAsStream("/bad_yaml/sharon.yml"));
        Assert.assertEquals(3, ((List) o).size());
    }

    @Test
    public void testBaseBall() throws Exception {
        Object o = Yaml.load(getClass().getResourceAsStream("/bad_yaml/baseball.yml"));
        Assert.assertEquals(2, ((List) ((Map) o).get("players")).size());
    }
}
