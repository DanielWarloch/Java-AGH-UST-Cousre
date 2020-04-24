package lab4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Document {
    public static void main(String[] args) throws IOException {
        Document cv = new Document("Jana Kowalski - CV");
        cv.setPhoto("resources/CV_photo.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph("...");
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Umiejętności")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );
        cv.writeHTML(new PrintStream("cv.html", StandardCharsets.UTF_8));
    }


    public String getTitle() {
        return title;
    }

    private String title;
    private Photo photo;
    private List<Section> sectionList = new ArrayList<Section>();


    public Document(String title) {
        this.title =title;
    }

     public Document setTitle(String title){
        this.title = title;
        return this;
    }

    public Document setPhoto(String photoUrl){
        this.photo = new Photo(photoUrl);
        return this;
    }

    public Section addSection(String sectionTitle){
        Section newSection = new Section(sectionTitle);
        return addSection(newSection);
    }
    public Section addSection(Section s){
        this.sectionList.add(s);
        return s;
    }


    public void writeHTML(PrintStream out){
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"pl\">\n" +
                    "<head>\n" +
                        "\t<meta charset=\"UTF-8\">\n" +
                        "\t<title>" + title +"</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                        "\t<div id=\"container\">\n" +
                            "<header>\n" +
                                "\t\t<h1 id=\"title\">" + title + "</h1>\n");
                                photo.writeHTML(out);
                            out.print("</header>\n");
                            out.print("<article>");
                                for (Section section: sectionList) {
                                    section.writeHTML(out);
                                }
                            out.print("</article>");
                        out.print("\t</div>\n" +
                    "</body>\n" +
                "</html>");
    }





}
