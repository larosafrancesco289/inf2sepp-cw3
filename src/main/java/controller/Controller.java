package controller;
import external.*;
import view.*;
import model.*;

import java.util.Collection;

public abstract class Controller {
    public SharedContext sharedContext;
    public View view;
    public AuthenticationService authService;
    public EmailService emailService;

    protected Controller(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        this.sharedContext = sharedContext;
        this.view = view;
        this.authService = authService;
        this.emailService = emailService;
    }

    protected <T> int selectFromMenu(Collection<T> items, String selection) {
        // ...
        return 0;
    }

    protected User getCurrentUser() {
        return sharedContext.getCurrentUser();
    }
}
