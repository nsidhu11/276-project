package trackour.trackour.views.error;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;

import jakarta.servlet.http.HttpServletResponse;

/**
 * {@summary Handles 404 Page Not Found error type}
 */
@Tag(Tag.DIV)
public class ErrorPage404Handler extends Component implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
      getElement().setText("Could not navigate to '" + event.getLocation().getPath() + "'");
      event.forwardTo("error");
      return HttpServletResponse.SC_NOT_FOUND;
    }
}
