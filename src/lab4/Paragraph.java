package lab4;

import java.io.PrintStream;
import java.util.List;

abstract class Paragraph {
    String content;

    Paragraph(){

    }
    Paragraph(String content){
        this.content = content;
    }
    Paragraph setContent(String content){
        this.content = content;
        return this;
    }
    public String getContent() {
        return content;
    }

    public abstract Paragraph addListItem(String c);

    void writeHTML(PrintStream out){
        out.printf("<p>%s</p>", getContent());
    }


}

class ParagraphWithList extends Paragraph{
    private UndecoratedList undecoratedList = new UndecoratedList();
    @Override
    public Paragraph addListItem(String c) {
        undecoratedList.addListItem(c);
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<p>%s</p>", getContent());
        undecoratedList.writeHTML(out);
    }



}
