package pw.practica1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Document type is an object that can contains a hold HTML Document
        //Documentation here https://jsoup.org/apidocs/org/jsoup/nodes/Document.html

        //URL Of the Page
        String URL = "http://itachi.avathartech.com:4567/opcion2.html";

        //Connecting to the URL and bringing the result without parsing
        Connection.Response pageWithoutParse = Jsoup.connect(URL).execute();

        //Variable that has the number of lines of the document
        int lines = pageWithoutParse.body().split("\n").length;

        //Connecting to the URL and bringing the document by the GET method and parse the response
        Document document = Jsoup.connect(URL).get();

        //Arraylist of <p> tags
        Elements paragraphs = document.getElementsByTag("p");

        //This variable is for count <img> tags
        int imgCount = 0;
        //Iterating over <p> tags looking for <img> tags
        for (Element p: paragraphs) {
            imgCount += p.getElementsByTag("img").size();
        }

        //Arraylist of <form> tags
        Elements forms = document.getElementsByTag("form");

        //These variables are for count the GET and POST form respectively
        int countGET = 0, countPOST = 0;
        //Iterating over <form> tags looking for method attribute
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("POST"))
                countPOST++;
            else if(form.attr("method").equalsIgnoreCase("GET"))
                countGET++;
        }

        //Variable only for count and printing
        int countForms = 1;
        //Variable that contains all the inputs and types
        String inputsWithType = "";
        //Iterating over <form> tags looking for input tags
        for (Element form: forms) {
            int aux = 0;
            inputsWithType += "\nForm #" + (countForms++) + ":\n";
            //Iterating over the <input> tag of this form
            for (Element input: form.getElementsByTag("input")) {
                inputsWithType += (++aux) + "-Etiqueta input con type: " + input.attr("type").toString() + "\n";
            }
        }

        //Iterating over <form> tags looking for method attribute POST
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("POST")){
                Connection.Response x = Jsoup.connect(URL).method(Connection.Method.POST).data("asignatura", "practica1").header("matricula", "20161229").execute();
                System.out.println(x.header("asignatura"));

            }
        }

        System.out.println("A) Lines: " + lines);
        System.out.println("B) Parragraphs: " + paragraphs.size());
        System.out.println("C) Images: " + imgCount);
        System.out.println("D) Forms: "+ forms.size() + " -> GET: " + countGET + " POST: " + countPOST);
        System.out.println("E) Forms Inputs:" + inputsWithType);
    }
}
