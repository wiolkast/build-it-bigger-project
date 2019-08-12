package com.example.jokegenerator;

import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class JokeGenerator {

    private final ArrayList<String> jokes = new ArrayList<>();

    public JokeGenerator(){
        try {
            File file = new File("jokes.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            jokes.add(document.getElementsByTagName("joke1").item(0).getTextContent());
            jokes.add(document.getElementsByTagName("joke2").item(0).getTextContent());
            jokes.add(document.getElementsByTagName("joke3").item(0).getTextContent());
            jokes.add(document.getElementsByTagName("joke4").item(0).getTextContent());
            jokes.add(document.getElementsByTagName("joke5").item(0).getTextContent());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getJoke(){
        int i = (int) (Math.random()*jokes.size());
        return jokes.get(i);
    }
}
