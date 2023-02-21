package abdulrahman.alluqmani.HyViewerWeb;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;

public class DataViewer extends VerticalLayout {
    TextField textField1 = new TextField("Bazaar");
    Button button1 = new Button("Get data!");
    H1 h1 = new H1("Hello darkness");
    Button toggleButton = new Button("Switch theme");

    public DataViewer() throws IOException, JSONException{
        addClassName("data-printer");

        Data dataMachine = new Data();
        Request bazaarRequester = new Request("bazaar");
    
        button1.addClickShortcut(Key.ENTER);
        button1.addClassName("toggle");
    
        button1.addClickListener(e -> {
            try {
                JSONObject dataBlob = bazaarRequester.data();
                dataMachine.save(Helper.fixItemName(textField1.getValue()), dataBlob);
                String response = dataMachine.getReadable(Helper.fixItemName(textField1.getValue()), dataBlob);
                add(new Paragraph(response));
    
            } catch (IOException | JSONException e1) {
                add(new Paragraph("Something went wrong! did you write a correct item name?"));
                e1.printStackTrace();
            }
    
        });
        toggleButton.setClassName("togbtn");
        toggleButton.addClickListener(click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if (themeList.contains(Lumo.DARK)) {
    
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
        add(createLayout());
    
        
    }

    private Component createLayout() {
        HorizontalLayout view = new HorizontalLayout(textField1, button1);
        view.setDefaultVerticalComponentAlignment(Alignment.END);
        return view;
    }



}
