package pw.practica1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Document type is an object that can contains a hold HTML Document
        //Documentation here https://jsoup.org/apidocs/org/jsoup/nodes/Document.html
        Document document = Jsoup.connect("http://localhost:4567/").get();
        System.out.println("A) Lines: " + lineCount(document));
        System.out.println("B) Parragraphs: " + parragraphCount(document));
        System.out.println("C) Images: " + imageCount(document));
    }

    private static int lineCount(Document document){
        return 0;
    }

    private static int parragraphCount(Document document){
        Elements elements = document.getElementsByTag("p");
        return elements.size();
    }

    private static int imageCount(Document document){
        Elements elements = document.getElementsByTag("img");
        return elements.size();
    }
}
