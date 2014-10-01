package yaml.parser;

import java.io.*;
import org.apache.commons.io.IOUtils;
import org.ho.yaml.TestYamlParserEvent;

public class YamlParserTest {

	public static void parse(File file) throws FileNotFoundException {
		parse(new FileReader(file));
	}

	public static void parse(String yamlText){
		parse(new StringReader(yamlText));
	}

	public static void parse(Reader reader) {
		try {
			ParserEvent handler = new TestYamlParserEvent();
			YamlParser y = new YamlParser(reader,handler);
			y.parse();
		} catch (IOException | SyntaxException e) {
			throw new RuntimeException(e);
		} finally {
            IOUtils.closeQuietly(reader);
        }
    }
}
