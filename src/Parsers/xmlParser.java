package Parsers;

// Provides everything you need to work with the DOM
// Document, Element, Node, NodeList, Text, Exceptions, etc.

import org.w3c.dom.*;
//XPath makes it easy to grab information from an XML document

import javax.xml.xpath.*;
// All the parsers for working with XML
// DocumentBuilder, DocumentBuilderFactory, SAXParser

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// SAX Simple API for XML
import org.xml.sax.SAXException;

// Class that will represent a system file name

import java.io.File;

// Used to write data to a file

import java.io.FileOutputStream;

// Triggered when an I/O error occurs

import java.io.IOException;

// Represents your XML document and contains useful methods

import org.jdom2.Document;

// Represents XML elements and contains useful methods

import org.jdom2.Element;

// Top level JDOM exception

import org.jdom2.JDOMException;

// Represents text used with JDOM

import org.jdom2.Text;

// Creates a JDOM document parsed using SAX Simple API for XML

import org.jdom2.input.SAXBuilder;

// Formats how the XML document will look

import org.jdom2.output.Format;

// Outputs the JDOM document to a file

import org.jdom2.output.XMLOutputter;

public class xmlParser {

    public static void main(String[] args){

        // Allows your app to get a parser that turns a xml doc into a DOM tree

        DocumentBuilderFactory domFactory =
            DocumentBuilderFactory.newInstance();

        // Provides support for XML namespaces if needed

        domFactory.setNamespaceAware(true);

        // Turns xml into a DOM tree

        DocumentBuilder builder;

        Document doc = null;

        /*
        try {

            // parses the file supplied

            builder = domFactory.newDocumentBuilder();
            String root = "C:/Users/1/James/grctc/GRCTC_Project/Classification/XMLParsers/AkomaNtoso/";
            String filePath = root + "Act_constitution_final-Kenya.xml";
            doc = builder.parse(filePath);
        }

        catch (SAXException e) {

            e.printStackTrace();
        }

        catch (IOException e) {

            e.printStackTrace();
        }

        catch (ParserConfigurationException e) {

            e.printStackTrace();
        }
        */

        // Allows you to grab data from the document using the codes below

        XPath xpath = XPathFactory.newInstance().newXPath();

        getNodeNameAndValue(doc, xpath);

    }

    private static void getNodeNameAndValue(Document doc, XPath xpath){

        // XPath Query
        XPathExpression expr;
        Object result = null;

        try {

            // Returns characters with the profession Student
            expr = xpath.compile("//show/actors/actor/character[@profession='Student']//text()");
            // Returns the result of the query
            result = expr.evaluate(doc, XPathConstants.NODESET);
        }

        catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        // Outputs the results of the query
        NodeList nodes = (NodeList) result;
        // Cycles through the results

        for (int i = 0; i < nodes.getLength(); i++) {
            // Print the matching node name and value
            System.out.println(nodes.item(i).getParentNode().getNodeName() + " " + nodes.item(i).getNodeValue());
        }

        // Display every name
        // : //show/name//text()
        // Display everything
        // : //show/*//text()

        // Show names based on an attribute
        // : //show/name[@id_code='show_001']//text()

        // Show actors and character names
        // : //show/actors/actor/*//text()

        // Show character names if they are Students
        // : //show/actors/actor/character[@profession='Student']//text()

    }

    private HashMap<String,String> getNodeName(Document doc, XPath xpath){

        // XPath Query
        XPathExpression expr;
        Object result = null;
        HashMap<String,String> sentences = new HashMap<>();

        try {

            // Returns characters with the profession Student
            expr = xpath.compile("//show/actors/actor/character[@profession='Student']//text()");
            // Returns the result of the query
            result = expr.evaluate(doc, XPathConstants.NODESET);
        }

        catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        // Outputs the results of the query
        NodeList nodes = (NodeList) result;
        // Cycles through the results

        for (int i = 0; i < nodes.getLength(); i++) {
            // Print the matching node name and value
            System.out.println(nodes.item(i).getParentNode().getNodeName() + " " + nodes.item(i).getNodeValue());
            sentences.put(nodes.item(i).getParentNode().getNodeName(),nodes.item(i).getNodeValue());
        }
        return (sentences);
    }

    private static void readXML(){

        SAXBuilder builder = new SAXBuilder();
        try {

            // Parses the file supplied into a JDOM document

            Document readDoc = builder.build(new File("./src/jdomMade.xml"));

            // Returns the root element for the document

            System.out.println("Root: " + readDoc.getRootElement());

            // Gets the text found between the name tags

            System.out.println("Show: " + readDoc.getRootElement().getChild("show").getChildText("name"));

            // Gets the attribute value for show_id assigned to name

            System.out.println("Show ID: " + readDoc.getRootElement().getChild("show").getChild("name").getAttributeValue("show_id") + "\n");

            Element root = readDoc.getRootElement();

            // Cycles through all of the children in show and prints them

            for (Element curEle : root.getChildren("show")) {
                System.out.println("Show Name: " + curEle.getChildText("name"));
                System.out.println("Show ID: " + curEle.getChild("name").getAttributeValue("show_id"));
                System.out.print("On " + curEle.getChildText("network") + " in the ");
                System.out.println(curEle.getChild("network").getAttributeValue("country") + "\n");
            }

        }

        catch (JDOMException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void writeXML(){
        try{

            // Creates a JDOM document

            Document doc = new Document();

            // Creates a element named tvshows and makes it the root

            Element theRoot = new Element("tvshows");
            doc.setRootElement(theRoot);

            // Creates elements show and name

            Element show = new Element("show");
            Element name = new Element("name");

            // Assigns an attribute to name and gives it a value

            name.setAttribute("show_id", "show_001");

            // Adds text between the name tags

            name.addContent(new Text("Life On Mars"));

            Element network = new Element("network");

            network.addContent(new Text("ABC"));

            network.setAttribute("country", "US");

            // Adds name and network to the show tag

            show.addContent(name);
            show.addContent(network);

            // Adds the show tag and all of its children to the root

            theRoot.addContent(show);

            // Add a new show element like above

            Element show2 = new Element("show");

            Element name2 = new Element("name");

            name2.setAttribute("show_id", "show_002");

            name2.addContent(new Text("Freaks and Geeks"));

            Element network2 = new Element("network");

            network2.addContent(new Text("ABC"));

            network2.setAttribute("country", "US");

            show2.addContent(name2);
            show2.addContent(network2);

            theRoot.addContent(show2);

            // Uses indenting with pretty format

            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());

            // Create a new file and write XML to it

            xmlOutput.output(doc, new FileOutputStream(new File("./src/jdomMade.xml")));

            System.out.println("Wrote to file");

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }


}
