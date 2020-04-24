package lab4;

import java.io.PrintStream;

public class ListItem {
    private String content;

    ListItem(){

    }
    ListItem(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    void writeHTML(PrintStream out){
        out.printf("<li>%s</li>\n", getContent());

    }
}
