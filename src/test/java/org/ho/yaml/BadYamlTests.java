package org.ho.yaml;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.ho.yaml.exception.YamlException;
import org.junit.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yaml.parser.YamlParserTest;

@Ignore // TODO: add missing sources for tests
public class BadYamlTests {

    private Logger log = LoggerFactory.getLogger(BadYamlTests.class);

    @Test
    public void testBadTab() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/bad_tab.yml"));
        } catch (YamlException e) {
            Assert.assertEquals("Error near line 2: Tabs may not be used for indentation.", e.getMessage());
            return;
        }

        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testBadTabMap() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/bad_tab_map.yml"));
        } catch (YamlException e) {
            Assert.assertEquals("Error near line 2: Tabs may not be used for indentation.", e.getMessage());
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testBadYaml() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/bad_yaml.yml"));
        } catch (YamlException e) {
            Assert.assertEquals("Error near line 14: Tabs may not be used for indentation.", e.getMessage());
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testGrails() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/grails.yml"));
        } catch (YamlException e) {
            Assert.assertEquals("Error near line 22: Can't create object of type class java.util.HashMap$Values using default constructor.", e.getMessage());
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testEricsGroovy() throws Exception {
        Map m = (Map) Yaml.load(new File("bad_yaml/erics_groovy.yml"));
        Assert.assertEquals("primary key", ((Map) ((List) ((Map) ((List) m.get("columns")).get(0)).get("constraints")).get(0)).get("constraint"));
        Assert.assertEquals("not null", ((Map) ((List) ((Map) ((List) m.get("columns")).get(0)).get("constraints")).get(1)).get("constraint"));
        log.debug(m.toString());
    }

    @Test
    public void testDonVExample() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/donV_example.yml"));
        } catch (YamlException e) {
            Assert.assertEquals("Error near line 8: Tabs may not be used for indentation.", e.getMessage());
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testShouldBeBadValuesInMap() {
        try {
            String yamlText =
                    "map:\n" +
                            "one\n" +
                            "two\n";
            Map m = (Map) Yaml.load(yamlText);
            log.debug(m.toString());
            YamlParserTest.parse(yamlText);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testShouldBeBadValuesInList() {
        try {
            String yamlText =
                    "- one\n" +
                            "one\n" +
                            "two\n";
            Object m = Yaml.load(yamlText);
            log.debug(m.toString());
            YamlParserTest.parse(yamlText);
        } catch (YamlException e) {
            log.error(e.getMessage(), e);
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testJavascript() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/javascript.js"));
        } catch (YamlException e) {
            log.error(e.getMessage(), e);
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testXML() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/xml.xml"));
        } catch (YamlException e) {
            log.error(e.getMessage(), e);
            return;
        }
        throw new RuntimeException("Should have failed.");


    }

    @Test
    public void testProperties() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/properties.properties"));
        } catch (YamlException e) {
            log.error(e.getMessage(), e);
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testIni() throws Exception {
        try {
            Yaml.load(new File("bad_yaml/ini.ini"));
        } catch (YamlException e) {
            log.error(e.getMessage(), e);
            return;
        }
        throw new RuntimeException("Should have failed.");
    }

    @Test
    public void testSharonYml() throws Exception {
        Object o = Yaml.load(new File("bad_yaml/sharon.yml"));
        log.error(o.toString());
        log.error(Yaml.dump(o));
        Assert.assertEquals(3, ((List) ((Map) o).get("nodes")).size());
    }

    @Test
    public void testSharon2Yml() throws Exception {
        Object o = Yaml.load(new File("bad_yaml/sharon2.yml"));
        log.error(o.toString());
        log.error(Yaml.dump(o));
        Assert.assertEquals(3, ((List) o).size());
    }

    @Test
    public void testBaseBall() throws Exception {
        Object o = Yaml.load(new File("bad_yaml/baseball.yml"));
        log.error(o.toString());
        log.error(Yaml.dump(o));
        Assert.assertEquals(2, ((List) ((Map) o).get("players")).size());
    }
}
