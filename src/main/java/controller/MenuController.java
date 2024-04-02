package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import model.User;
import view.View;

import java.util.Arrays;

public class MenuController extends Controller {
    private enum GuestMainMenuOption {
        LOGIN, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF
    }

    private enum StudentMainMenuOption {
        LOGOUT, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF
    }

    private enum TeachingStaffMainMenuOption {
        LOGOUT, MANAGE_RECEIVED_INQUIRIES
    }

    private enum AdminStaffMainMenuOption {
        LOGOUT, MANAGE_INQUIRIES, ADD_PAGE, VIEW_ALL_PAGES, MANAGE_FAQ
    }

    private InquirerController inquirerController;
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
        }
    }

    private boolean handleGuestMainMenu() {
        guestController = new GuestController(sharedContext, view, authService, emailService);
        inquirerController = new InquirerController(sharedContext, view, authService, emailService);
        int userSelection = guestController.selectFromMenu(Arrays.asList(GuestMainMenuOption.values()), null);
        switch (userSelection) {
            case 1:
                guestController.login();
                break;
            case 2:
                inquirerController.consultFAQ();
                break;
            case 3:
                inquirerController.searchPages();
                break;
            case 4:
                inquirerController.contactStaff();
                break;
        }
        return true;
    }

    private boolean handleStudentMainMenu() {
        inquirerController = new InquirerController(sharedContext, view, authService, emailService);
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        int userSelection = authenticatedUserController.selectFromMenu(Arrays.asList(StudentMainMenuOption.values()), null);
        switch (userSelection) {
            case 1:
                authenticatedUserController.logout();
                break;
            case 2:
                inquirerController.consultFAQ();
                break;
            case 3:
                inquirerController.searchPages();
                break;
            case 4:
                inquirerController.contactStaff();
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
