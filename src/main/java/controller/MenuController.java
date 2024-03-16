package controller;

import external.*;
import model.*;
import view.*;

public class MenuController extends Controller{
    private GuestMainMenuOption guestMainMenuOptions;
    private StudentMainMenuOption studentMainMenuOptions;
    private TeachingStaffMainMenuOption teachingStaffMainMenuOptions;
    private AdminStaffMainMenuOption adminMainMenuOptions;

    public MenuController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }
    public void selectFromMenu(Collection<T> items, String selection) {
        // ...
        return;
    }
}
