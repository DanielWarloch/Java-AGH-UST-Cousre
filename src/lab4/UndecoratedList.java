package lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UndecoratedList {
    private List<ListItem> listItems = new ArrayList<>();

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }


    void writeHTML(PrintStream out){
        out.print("<ol>\n");
        for (ListItem listItem :
                listItems) {
            listItem.writeHTML(out);
        }
        out.print("</ol>\n");

    }

    public UndecoratedList addListItem(String c) {
        ListItem listItem = new ListItem(c);
        addListItem(listItem);
        return this;
    }
    public UndecoratedList addListItem(ListItem listItem) {
        listItems.add(listItem);
        return this;

    }
}
