package controller;

import java.util.Arrays;
import java.util.Collection;

import external.*;
import model.*;
import view.*;

public class MenuController extends Controller{
    // private GuestMainMenuOption guestMainMenuOptions;
    // private StudentMainMenuOption studentMainMenuOptions;
    // private TeachingStaffMainMenuOption teachingStaffMainMenuOptions;
    // private AdminStaffMainMenuOption adminMainMenuOptions;

    // private InquirerController inquirerController;
    // private StaffController staffController;
    // private AdminStaffController adminStaffController;
    // private TeachingStaffController teachingStaffController;
    private GuestController guestController;
    // private AuthenticatedUserController authenticatedUserController;
    private Controller mainMenuController;

    public MenuController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void mainMenu() {
        User currentUser = sharedContext.getCurrentUser();
        switch (currentUser.getRole()) {
            case "Student":
                handleStudentMainMenu();
                break;
            case "TeachingStaff":
                handleTeachingStaffMainMenu();
                break;
            case "AdminStaff":
                handleAdminStaffMainMenu();
                break;
            case "Guest":
                handleGuestMainMenu();
            default:
                break;
        }

        
    }

    private boolean handleGuestMainMenu() {
        guestController = new GuestController(sharedContext, view, authService, emailService);
        int userSelection = guestController.selectFromMenu(Arrays.asList(GuestMainMenuOption.values()), null);
        switch (userSelection) {
            case 1:
                guestController.login();
                break;
        }
        return true;
    }

    private boolean handleStudentMainMenu() {
        mainMenuController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        int userSelection = mainMenuController.selectFromMenu(Arrays.asList(StudentMainMenuOption.values()), null);
        return true;
    }

    private boolean handleTeachingStaffMainMenu() {
        mainMenuController = new TeachingStaffController(sharedContext, view, authService, emailService);
        int userSelection = mainMenuController.selectFromMenu(Arrays.asList(TeachingStaffMainMenuOption.values()), null);
        return true;
    }

    private boolean handleAdminStaffMainMenu() {
        mainMenuController = new AdminStaffController(sharedContext, view, authService, emailService);
        int userSelection = mainMenuController.selectFromMenu(Arrays.asList(AdminStaffMainMenuOption.values()), null);
        return true;
    }
}
