package trackour.trackour.views.explore;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import se.michaelthelin.spotify.model_objects.specification.Category;

import trackour.trackour.spotify.explore;

@Route("Explore")
@PageTitle("Login")
@AnonymousAllowed
public class ExploreView extends VerticalLayout {
    public ExploreView() {

        H1 header = new H1("How are you feeling today");
        add(header);
        List<Category> categories = explore.getCategories();

        FlexLayout categoryLayout = new FlexLayout();
        categoryLayout.addClassName("category-layout");
        categoryLayout.setWidthFull();
        categoryLayout.getStyle().set("flex-wrap", "wrap");
        categoryLayout.setClassName("category-wrap");

        // Display the categories
        for (Category category : categories) {
            Image coverImage = new Image(category.getIcons()[0].getUrl(), "Category Cover");
            coverImage.setWidth("200px");
            coverImage.setHeight("200px");

            Button catButton = new Button(coverImage);
            catButton.setWidth("200px");
            catButton.setHeight("200px");
            catButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Div categoryInfo = new Div(new Text(category.getName()));
            categoryInfo.setWidth("200px");

            VerticalLayout catLayout = new VerticalLayout();
            catLayout.add(catButton, categoryInfo);
            catLayout.setSpacing(false);
            catLayout.setMargin(false);

            categoryLayout.add(catLayout);
        }

        // Add the FlexLayout to the main layout
        add(categoryLayout);

        // HorizontalLayout categoryLayout = new HorizontalLayout();
        // categoryLayout.addClassName("category-layout");
        // categoryLayout.setWidthFull();
        // // Display the categories
        // for (Category category : categories) {
        // Image coverImage = new Image(category.getIcons()[0].getUrl(), "Category
        // Cover");
        // coverImage.setWidth("200px");
        // coverImage.setHeight("200px");

        // Button catButton = new Button(coverImage);
        // catButton.getStyle().setWidth("200px");
        // catButton.getStyle().setHeight("200px");
        // catButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // Div categoryInfo = new Div(new Text(category.getName()));
        // categoryInfo.setWidth("200px");

        // VerticalLayout catLayout = new VerticalLayout();
        // catLayout.add(catButton, categoryInfo);
        // // Add the category VerticalLayout to the FlexLayout
        // Grid<Category> grid = new Grid<>(Category.class);
        // grid.setItems();
        // categoryLayout.add(catLayout);

        // }
        // Add the FlexLayout to the main layout
        // add(categoryLayout);
    }
}
