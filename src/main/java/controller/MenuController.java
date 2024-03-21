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
    private AdminStaffController adminStaffController;
    private TeachingStaffController teachingStaffController;
    private GuestController guestController;
    private AuthenticatedUserController authenticatedUserController;


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
            case 2:
                view.displayFAQSection(null, false);
                break;
            case 3:  //waiting for the search implementation
                String searchKeyword = view.getInput("> What do you want to search for \n");
                break;
               
            case 4:
                break;
        }
        return true;
    }

    private boolean handleStudentMainMenu() {
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        int userSelection = authenticatedUserController.selectFromMenu(Arrays.asList(StudentMainMenuOption.values()), null);
        switch (userSelection) {
            case 1:
                authenticatedUserController.logout();
                break;
            case 2:
                view.displayFAQSection(null, false);
                break;
            case 3:  //waiting for the search implementation
                String searchKeyword = view.getInput("> What do you want to search for \n");
                break;
               
            case 4:
                break;
        }
        return true;
    }

    private boolean handleTeachingStaffMainMenu() {
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        teachingStaffController = new TeachingStaffController(sharedContext, view, authService, emailService);
        int userSelection = teachingStaffController.selectFromMenu(Arrays.asList(TeachingStaffMainMenuOption.values()),
                null);
                switch (userSelection) {
                    case 1:
                        authenticatedUserController.logout();
                        break;
                    case 2:
                        teachingStaffController.manageReceivedInquiries();
                        break;
                }
        return true;
    }

    private boolean handleAdminStaffMainMenu() {
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        adminStaffController = new AdminStaffController(sharedContext, view, authService, emailService);
        int userSelection = adminStaffController.selectFromMenu(Arrays.asList(AdminStaffMainMenuOption.values()), null);
        switch (userSelection) {
            case 1:
                authenticatedUserController.logout();
                break;
            case 2:
                adminStaffController.manageInquiries();
                break;
            case 3:
                adminStaffController.addPage();
                break;    
            case 4:
                adminStaffController.viewAllPages();
                break;
            case 5:
                adminStaffController.manageFAQ();
                break;
        }
        return true;
    }
}
