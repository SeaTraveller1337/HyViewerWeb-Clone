package abdulrahman.alluqmani.HyViewerWeb;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Bazaar", layout = AppLayoutBasic.class)
@PageTitle("Bazaar - Check the market!")
public class Bazaar extends VerticalLayout {
    Grid<Displayer> grid = new Grid<>();
    public Bazaar() throws IOException, JSONException {
        TextField textField1 = new TextField("Enter item name");
        Button send = new Button("Send");
        HorizontalLayout content = new HorizontalLayout(textField1, send);

        content.setDefaultVerticalComponentAlignment(Alignment.END);
        grid.addColumn(Displayer::getDataDate).setHeader("Date").setResizable(true).setSortable(true);
        grid.addColumn(Displayer::getItemName).setHeader("Name").setResizable(true).setSortable(true);
        grid.addComponentColumn(Displayer::getPriceFormatted).setHeader("Price").setResizable(true).setSortable(true);
        
        grid.setColumnReorderingAllowed(true);
        ArrayList<Displayer> displayer = new ArrayList<>();
        ListDataProvider<Displayer> dataProvider = new ListDataProvider<>(displayer);
        grid.setDataProvider(dataProvider);
        
        send.addClickListener(e -> {
            try {
                Displayer newDisplay = new Displayer(textField1.getValue());
                displayer.add(0, newDisplay);
                dataProvider.refreshAll();
            } catch (Exception e1) {
                add(new Paragraph("Something went wrong! did you write a correct item name?"));
                e1.printStackTrace();
            }
    
        });

        
        add(content, grid);

    }
}
