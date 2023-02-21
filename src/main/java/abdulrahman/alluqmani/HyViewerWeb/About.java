package abdulrahman.alluqmani.HyViewerWeb;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "About", layout = AppLayoutBasic.class)
@PageTitle("About - Information about the dev!")
public class About extends VerticalLayout {
    public About(){
        String firstText = "Hello, welcome to my web app, HyViewerWEB. Fully built with my java knowledge!";
        String secondText = "This app contacts HyPixel API to get data regarding the bazaar & auction house. You can try it out, \njust enter an item name on the relavant section, click send, and this will print a nice set of information about your item";
        String author = "Abdulrahman Abdullah Alluqmani";
        String authorEmail = "vxsz@vxsz.net";
        String extraInfo = "a personal project for my OOP course :D";
        H1 firstGraph = new H1(firstText);
        H3 secondGraph = new H3(secondText);
        H4 thirdGraph = new H4("Author name: " + author);
        H4 fourthGraph = new H4("Contact email: " + authorEmail);
        H4 fifthGraph = new H4("Info: " + extraInfo);

        add(firstGraph, secondGraph, thirdGraph, fourthGraph, fifthGraph);
        
    }
}
