package lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraps = new ArrayList<>();

    public Section(String title){
        this.title  = title;
    }
    void setTitle(String title){this.title = title;}
    Section addParagraph(String paragraphText){
        Paragraph newParagraph = new Paragraph(paragraphText) {
            @Override
            public Paragraph addListItem(String c) {
                return null;
            }
        };
        return addParagraph(newParagraph);
    }
    Section addParagraph(Paragraph p){
        this.paragraps.add(p);
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<div id=\"div_%s\">", title);
        out.printf("<h1 id=\"h1_%s\" lang=\"pl\">%s</h1>", title, title);
        for ( Paragraph paragraph: paragraps) {
            paragraph.writeHTML(out);
            out.print("\n");
        }
        out.print("</div>");


    }
}
