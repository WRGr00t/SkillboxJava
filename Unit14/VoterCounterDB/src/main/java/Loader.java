import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Loader {
    public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException, IOException, ParseException {
        String fileName = "src/main/res/data-1M.xml";
        long start = System.currentTimeMillis();
        DBConnection.getConnection();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
        DBConnection.executeMultiInsert();
        System.out.printf("Pursing duration: %d ms%n", System.currentTimeMillis() - start);
        DBConnection.printVoterCounts();
        handler.printCountRec();
    }
}
