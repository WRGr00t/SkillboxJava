import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private int countRec = 0;
    private static boolean isEnd = false;

    public static boolean isEnd() {
        return isEnd;
    }

    @Override
    public void startDocument() {
        System.out.println("Start parse XML...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        try {
            if (qName.equals("voter")) {
                Connect.countVoter(attributes.getValue("name"), attributes.getValue("birthDay"));
                countRec++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() {
        System.out.println("Stop parse XML...");
        isEnd = true;
    }

    public void printCountRec() {
        System.out.println(countRec);
    }
}