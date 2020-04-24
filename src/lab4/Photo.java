package lab4;

import java.io.PrintStream;

public class Photo {

    private String url;
    private String alt = "Smiley face";

    private String align = "right";

    private int height = 200;
    private int width = 200;
    Photo(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    void writeHTML(PrintStream out){
        out.print("<div align=\"" + this.align + "\">");
        out.printf("<img src=\"%s\" alt=\"%s\" height=\"%s\" width=\"%s\"/>\n",this.url,this.alt, this.height, this.width);
        out.print("</div>");

    }
}