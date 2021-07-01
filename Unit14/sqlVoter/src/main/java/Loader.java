import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Loader {
    public static void main(String[] args) throws SQLException {
        String fileName = "src/main/res/data-1572M.xml";
        long start = System.currentTimeMillis();
        Connect.getConnection();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        XMLHandler handler = new XMLHandler();
        try {
            parser = factory.newSAXParser();
            parser.parse(new File(fileName), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Pursing duration: %d ms%n", System.currentTimeMillis() - start);
        Connect.addIndex();
        Connect.printVoterCounts();
        handler.printCountRec();
    }
}
