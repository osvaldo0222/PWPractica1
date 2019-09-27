package pw.practica1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //Documentation here https://jsoup.org/apidocs/org/jsoup/nodes/Document.html

        //URL Of the Page
        System.out.println("Practica #1 2016-1229\nInsert the URL of the page: ");
        String URL = new Scanner(System.in).nextLine();

        //Document type is an object that can contains a hold HTML Document
        //Getting the page by GET method and Parsing the page
        Document document = Jsoup.connect(URL).get();

        System.out.println("\nA) Lines: " + lineCount(URL));
        System.out.println("\nB) Parragraphs: " + paragraphsCount(document));
        System.out.println("\nC) Images: " + imageCount(document));
        System.out.println("\nD) Forms: "+ formElements(document).size() + " -> GET: " + getForms(document) + " POST: " + postForms(document));
        System.out.println("\nE) Forms Inputs:" + inputs(document));
        System.out.println("F) Server Response: \n" + postRequest(document));
    }

    private static int lineCount(String URL) throws IOException {
        //Connecting to the URL and bringing the result without parsing
        Connection.Response pageWithoutParse = Jsoup.connect(URL).execute();

        //Returning the numbers of lines of the plain text
        return pageWithoutParse.body().split("\n").length;
    }

    private static int paragraphsCount(Document document){
        //Arraylist of <p> tags
        Elements paragraphs = document.getElementsByTag("p");

        //Variable that has the number of <p> tags
        return paragraphs.size();
    }

    private static int imageCount(Document document) {
        //Arraylist of <p> tags
        Elements paragraphs = document.getElementsByTag("p");

        //This variable is for count <img> tags
        int imgCount = 0;
        //Iterating over <p> tags looking for <img> tags
        for (Element p: paragraphs) {
            imgCount += p.getElementsByTag("img").size();
        }
        return imgCount;
    }

    private static Elements formElements(Document document){
        return document.getElementsByTag("form");
    }

    private static int getForms(Document document) {
        //Arraylist of <form> tags
        Elements forms = formElements(document);

        //These variables are for count the GET and POST form respectively
        int countGET = 0;
        //Iterating over <form> tags looking for method attribute
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("GET"))
                countGET++;
        }
        return countGET;
    }

    private static int postForms(Document document) {
        //Arraylist of <form> tags
        Elements forms = formElements(document);

        //These variables are for count the GET and POST form respectively
        int countPOST = 0;
        //Iterating over <form> tags looking for method attribute
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("POST"))
                countPOST++;
        }
        return countPOST;
    }

    private static String inputs(Document document) {
        //Variable only for count and printing
        int countForms = 1;
        //Variable that contains all the inputs and types
        String inputsWithType = "";
        //Iterating over <form> tags looking for input tags
        for (Element form: formElements(document)) {
            int aux = 0;
            inputsWithType += "\nForm #" + (countForms++) + ":\n";
            //Iterating over the <input> tag of this form
            for (Element input: form.getElementsByTag("input")) {
                inputsWithType += (++aux) + "-Etiqueta input con type: " + input.attr("type").toString() + "\n";
            }
        }
        return inputsWithType;
    }

    private static String postRequest(Document document) throws IOException {
        String response = "";
        //Iterating over <form> tags looking for method attribute POST
        for (Element form: formElements(document)) {
            if (form.attr("method").equalsIgnoreCase("POST")){
                Connection.Response responseText = ((FormElement) form).submit().data("asignatura", "practica1").header("matricula","20161229").execute();
                response += responseText.body() + "\n";
            }
        }
        return response;
    }
}
