package controller;

import external.*;
import model.*;
import view.*;

public class MenuController extends Controller{
    private GuestMainMenuOption guestMainMenuOptions;
    private StudentMainMenuOption studentMainMenuOptions;
    private TeachingStaffMainMenuOption teachingStaffMainMenuOptions;
    private AdminStaffMainMenuOption adminMainMenuOptions;

    private InquirerController inquirerController;
    private StaffController staffController;
    private AdminStaffController adminStaffController;
    private TeachingStaffController teachingStaffController;
    private GuestController guestController;
    private AuthenticatedUserController authenticatedUserController;

    public MenuController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void mainMenu() {
        handleGuestMainMenu();
    }

    private boolean handleGuestMainMenu() {
        for (GuestMainMenuOption option : GuestMainMenuOption.values()) {
            System.out.println(option);
        }
        return true;
    }

    private boolean handleStudentMainMenu() {
        return true;
    }

    private boolean handleTeachingStaffMainMenu() {
        return true;
    }

    private boolean handleAdminStaffMainMenu() {
        return true;
    }
}
