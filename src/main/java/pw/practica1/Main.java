package pw.practica1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Document type is an object that can contains a hold HTML Document
        //Documentation here https://jsoup.org/apidocs/org/jsoup/nodes/Document.html
        Document document = Jsoup.connect("http://itachi.avathartech.com:4567/opcion2.html").get();
        System.out.println("A) Lines: " + lineCount(document));
        System.out.println("B) Parragraphs: " + parragraphCount(document));
        System.out.println("C) Images: " + imageCount(document));
        System.out.println("D) Forms: "+ formCount(document) + " -> GET: " + formCountGet(document) + " POST: " + formCountPost(document));
        System.out.println("E) Forms Inputs:\n" + formsInputs(document));
    }

    private static int lineCount(Document document){
        return 0;
    }

    private static int parragraphCount(Document document){
        return document.getElementsByTag("p").size();
    }

    private static int imageCount(Document document){
        return document.getElementsByTag("img").size();
    }

    private static int formCount(Document document) {
        return document.getElementsByTag("form").size();
    }

    private static int formCountGet(Document document){
        int count = 0;
        Elements forms = document.getElementsByTag("form");
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("get"))
                count++;
        }
        return count;
    }

    private static int formCountPost(Document document){
        int count = 0;
        Elements forms = document.getElementsByTag("form");
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase("post"))
                count++;
        }
        return count;
    }

    private static String formsInputs(Document document){
        int count = 1;
        String result = "";
        Elements forms = document.getElementsByTag("form");
        for (Element form: forms) {
            result += "Form #" + (count++) + "\n";
            result += form.getElementsByTag("input").toString();
            result += "\n";
        }
        return result;
    }
}
