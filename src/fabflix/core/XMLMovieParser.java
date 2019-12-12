package fabflix.core;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLMovieParser extends DefaultHandler {
    ArrayList<XMLMovie> xmlmovies;
    private String tempValue;
    private XMLMovie tempMovie;
    private String cDirector;

    public XMLMovieParser() {
        xmlmovies = new ArrayList<>();
    }

    public ArrayList<XMLMovie> parseMovieFile() {
        System.out.println("    parseMovieFile()");
        parseDocument();
    //   printResults();
        return xmlmovies;
    }

    private void parseDocument() {
        System.out.println("        parseDocument()");
        // Set a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();

        try {
            // Get a new instance of parser
            SAXParser sp = spf.newSAXParser();
            // Parse the file and register this class for call backs
            sp.parse("C:\\Users\\David\\Documents\\GitHub\\cs122b\\cs122b-spring18-team-1\\project3\\mains243.xml", this);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printResults() {
        System.out.println("        printResults()");
        for (int i = 0; i < xmlmovies.size(); ++i) {
            System.out.println("Movie #" + i + " = " + xmlmovies.get(i).toString());
        }
    }

    /** ------------------------- EVENT HANDLERS ------------------------- **/
    public void startElement(String uri, String localName, String qName, Attributes a) throws SAXException {
//        System.out.println("                startElement(" +  uri + ", " + localName + ", " + qName + ")");
        // Reset tempValue
        tempValue = "";

        // Check if we've started a new film
        if (qName.equalsIgnoreCase("film")) {
         //  System.out.println("HAVE A NEW FILM");
            // Create a new instance of an XMLMovie
            tempMovie = new XMLMovie();
            tempMovie.setDirector(cDirector);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
//        System.out.println("                characters()");
        tempValue = new String(ch, start, length);
//        System.out.println("                    tempValue = " + tempValue);
    }

    public boolean isInteger(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println("                endElement  (" + uri + ", " + localName + ", " + qName + ")");

        if (qName.equalsIgnoreCase("film")) {
            tempMovie.setDirector(cDirector);
            // Add the film to the list
//            System.out.println("ADDING NEW MOVIE TO LIST: " + tempMovie.toString());
            xmlmovies.add(tempMovie);
        }
        else if (qName.equalsIgnoreCase("dirname")) {
//            System.out.println("HAVE A NEW DIRNAME");
            cDirector = tempValue;
//            System.out.println("cDirector is now: " + cDirector);
        }
        else if (qName.equalsIgnoreCase("dirname")) {
            cDirector = tempValue;
        }
        else if (qName.equalsIgnoreCase("t")) {
            tempMovie.setTitle(tempValue);
//            System.out.println("Title is now: " + tempMovie.getTitle());
        }
        else if (qName.equalsIgnoreCase("year")) {
            String s = tempValue;
            if (s.contains("yy") || s.contains("x")) {
                tempMovie.setYear(1900);
            }
            else {
                if (isInteger(s)) {
                    tempMovie.setYear(Integer.parseInt(s));
                }
                else {
                    tempMovie.setYear(1900);
                }
            }
        }
        else if (qName.equalsIgnoreCase("cat")) {
            String genre = tempValue;
//            System.out.println("genre is now: " + genre);
            tempMovie.getGenres().add(genre);
//            System.out.println("Added genre to tempMovie!");
        }
    }
}