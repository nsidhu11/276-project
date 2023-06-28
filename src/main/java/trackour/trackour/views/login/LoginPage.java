package trackour.trackour.views.login;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import trackour.trackour.security.SecurityViewHandler;
// import trackour.trackour.views.signup.SignUpView;
import trackour.trackour.views.signup.SignupPageView;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed

public class LoginPage extends VerticalLayout implements BeforeLeaveObserver, BeforeEnterObserver {
    
    SecurityViewHandler securityViewHandler;

    private LoginForm login = new LoginForm();

    // inject view auth handler
    public LoginPage(SecurityViewHandler securityViewHandler) {
        this.securityViewHandler = securityViewHandler;

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // submit action
        login.setAction("login");

        // Dialog resetPassDialog = handleForgotPasswordDialog();

        // login.addForgotPasswordListener(ev -> {
        //     resetPassDialog.open();
        // });

        // link to signup page
        RouterLink signUpLink = new RouterLink("or Signup", SignupPageView.class);

        add(new H1("Trackour"), login, signUpLink);
    }

    Dialog handleForgotPasswordDialog() {
        Dialog dialog = new Dialog();

        VerticalLayout resetDialogLayout = new VerticalLayout();

        EmailField dialogEmailField = new EmailField("Email");

        dialog.setHeaderTitle("Forgot Password");
        Button saveButton = new Button("Send", e -> dialog.close());
        Button cancelButton = new Button("Cancel", e -> dialog.close());

        resetDialogLayout.add(dialogEmailField);
        dialog.add(resetDialogLayout);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        return dialog;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // this method call reroutes get requests to this view if the current session is already authenticated
        this.securityViewHandler.handleAnonymousOnly(beforeEnterEvent, true, "/");
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        // reroute to error page
        if (event.hasUnknownReroute()){
            System.out.println("Rerouting to Error Page!");
        }
    }
}
