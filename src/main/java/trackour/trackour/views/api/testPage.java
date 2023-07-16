package trackour.trackour.views.api;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("testPage")
@AnonymousAllowed
public class testPage extends Div{

    public testPage() {
        Paragraph info = new Paragraph(infoText());
        info.setText(infoText());

        HorizontalLayout horizontalLayout = new HorizontalLayout(info);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(horizontalLayout);
    }

    private String infoText() {
        return "HELLO";
    }

    // private void infoText2() {
    //     return APIController.searchTracks("Abba");
    // }
}
