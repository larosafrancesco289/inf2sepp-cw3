package controller;

import external.*;
import model.*;
import view.*;

public class MenuController extends Controller{
    private GuestMainMenuOption guestMainMenuOptions;
    private StudentMainMenuOption studentMainMenuOptions;
    private TeachingStaffMainMenuOption teachingStaffMainMenuOptions;
    private AdminStaffMainMenuOption adminMainMenuOptions;

    // private InquirerController inquirerController;
    // private StaffController staffController;
    // private AdminStaffController adminStaffController;
    // private TeachingStaffController teachingStaffController;
    // private GuestController guestController;
    // private AuthenticatedUserController authenticatedUserController;
    private Controller mainMenuController;

    public MenuController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void mainMenu() {
        User currentUser = super.getCurrentUser();
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
                break;
            default:
                break;
        }
    }

    private boolean handleGuestMainMenu() {
        for (GuestMainMenuOption option : GuestMainMenuOption.values()) {
            System.out.println(option);
        }
        mainMenuController = new GuestController(sharedContext,view, authService, emailService);
        return true;
    }

    private boolean handleStudentMainMenu() {
        for (StudentMainMenuOption option : StudentMainMenuOption.values()) {
            System.out.println(option);
        }
        mainMenuController = new AuthenticatedUserController(sharedContext,view, authService, emailService);
        return true;
    }

    private boolean handleTeachingStaffMainMenu() {
        for (TeachingStaffMainMenuOption option : TeachingStaffMainMenuOption.values()) {
            System.out.println(option);
        }
        mainMenuController = new TeachingStaffController(sharedContext,view, authService, emailService);
        return true;
    }

    private boolean handleAdminStaffMainMenu() {
        for (AdminStaffMainMenuOption option : AdminStaffMainMenuOption.values()) {
            System.out.println(option);
        }
        mainMenuController = new AdminStaffController(sharedContext,view, authService, emailService);
        return true;
    }
}
