package abdulrahman.alluqmani.HyViewerWeb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Auction", layout=AppLayoutBasic.class)
@PageTitle("Auction - Check the market!")

public class Auction extends VerticalLayout {
    Grid<DisplayAuction> grid = new Grid<>();
    public Auction(){
        TextField textField1 = new TextField("Enter tool name");
        Button send = new Button("Send");
        HorizontalLayout content = new HorizontalLayout(textField1, send);
        
        content.setDefaultVerticalComponentAlignment(Alignment.END);
        grid.addColumn(DisplayAuction::getDataDate).setHeader("Date").setResizable(true).setSortable(true);
        grid.addColumn(DisplayAuction::getItemName).setHeader("Name").setResizable(true).setSortable(true);
        grid.addColumn(DisplayAuction::getAveragePrice).setHeader("Average Price").setResizable(true).setSortable(true);
        
        grid.setColumnReorderingAllowed(true);
        ArrayList<DisplayAuction> displayer = new ArrayList<>();
        ListDataProvider<DisplayAuction> dataProvider = new ListDataProvider<>(displayer);
        grid.setDataProvider(dataProvider);

        send.addClickListener(e -> {
            try {
                DisplayAuction newDisplay = new DisplayAuction(textField1.getValue());
                displayer.add(0, newDisplay);
                dataProvider.refreshAll();
            } catch (Exception e1) {
                add(new Paragraph("Something went wrong! either you wrote the name incorrectly, or there aren't any offers."));
                e1.printStackTrace();
            }
    
        });

        
        add(content, grid);

    }

}
