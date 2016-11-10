package com.example.clindeqeuist.timecalculator.model;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class EntryCollection
{

    private List<Entry> entries = new ArrayList<>();


    public List<Entry> getEntries()
    {
        return entries;
    }


    public void saveEntries(String filename, Context context)
    {
        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element entriesElement = document.createElement("entries");
            document.appendChild(entriesElement);

            for (Entry entry : entries)
            {
                // TODO: Add support for description and value
                Element entryElement = document.createElement("entry");
                entryElement.setTextContent(entry.getDescription());
                entriesElement.appendChild(entryElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(domSource, result);

            /*
            StringWriter stringWriter = new StringWriter();
            StreamResult stringResult = new StreamResult(stringWriter);
            transformer.transform(domSource, stringResult);
            StringBuffer stringBuffer = stringWriter.getBuffer();
            String finalString = stringBuffer.toString();
            */
        }
        catch (ParserConfigurationException | TransformerException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    public void loadEntries(String filename, Context context)
    {
        entries.clear();

        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            FileInputStream inputStream = context.openFileInput(filename);
            Document document = builder.parse(inputStream);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList entryNodes = (NodeList) xPath.evaluate("/entries/entry/text()",
                    document, XPathConstants.NODESET);

            for (int i = 0; i < entryNodes.getLength(); ++i)
            {
                // TODO: Add support for description and value
                String nodeValue = entryNodes.item(i).getNodeValue();
                if (nodeValue != null)
                    entries.add(new Entry(nodeValue, null));
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e)
        {
            e.printStackTrace();
        }
    }

}
