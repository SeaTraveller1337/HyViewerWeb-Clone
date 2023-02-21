package abdulrahman.alluqmani.HyViewerWeb;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.text.html.ListView;

import org.json.JSONException;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
@PageTitle("HyViewerWEB - View items data!")
public class AppLayoutBasic extends AppLayout {

    public AppLayoutBasic() throws IOException, JSONException, InterruptedException {
        Request grabData = new Request("bazaar");

        Thread thread1 = new Thread() {
            public void run() {
                try {
                    grabData.infinityRunner();
                } catch (IOException | InterruptedException e) {
                    System.out.println("Error occured");
                    e.printStackTrace();
                }
            }
        };
        if ((Request.dataBlob == null) && (Request.dataBlobAuction == null) && (Request.runningThread == false)) {
            thread1.start();
        }
        DrawerToggle toggle = new DrawerToggle();
        RouterLink linkBazaar = new RouterLink(Bazaar.class);
        RouterLink linkAuction = new RouterLink(Auction.class);
        RouterLink linkAbout = new RouterLink(About.class);
        linkBazaar.add(Svg.BAZAAR.create());
        linkAuction.add(Svg.AUCTION.create());
        linkBazaar.add("ㅤ\t  Bazaar");
        linkAuction.add("ㅤ\tAuction");
        linkAbout.add("About");

        H1 title = new H1("HyViewerWEB");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        Tab bazaarTab = new Tab();
        bazaarTab.add(linkBazaar);

        Tab marketTab = new Tab();
        marketTab.add(linkAuction);


        Tab aboutTab = new Tab();
        aboutTab.add(linkAbout);

        Tabs tabs = new Tabs(bazaarTab, marketTab, aboutTab);
        tabs.addClassName("tabs");
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setClassName("tabsLeft");

        addToDrawer(tabs);
        addToNavbar(toggle, title);
    }

}