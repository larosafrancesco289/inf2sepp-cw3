package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import model.User;
import view.View;

import java.util.Arrays;

/**
 * Controller class responsible for displaying and handling the main menu options based on the user's role.
 * This class determines which actions are available to the user (e.g., login, consult FAQ, manage inquiries)
 * and delegates to specific controllers based on the user selection.
 */
public class MenuController extends Controller {
    /**
     * Enum for guest main menu options.
     */
    private enum GuestMainMenuOption {
        LOGIN, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF
    }

    /**
     * Enum for student main menu options.
     */
    private enum StudentMainMenuOption {
        LOGOUT, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF
    }

    /**
     * Enum for teaching staff main menu options.
     */
    private enum TeachingStaffMainMenuOption {
        LOGOUT, MANAGE_RECEIVED_INQUIRIES
    }

    /**
     * Enum for admin staff main menu options.
     */
    private enum AdminStaffMainMenuOption {
        LOGOUT, MANAGE_INQUIRIES, ADD_PAGE, VIEW_ALL_PAGES, MANAGE_FAQ
    }

    private InquirerController inquirerController;
    // private StaffController staffController;
    private AdminStaffController adminStaffController;
    private TeachingStaffController teachingStaffController;
    private GuestController guestController;
    private AuthenticatedUserController authenticatedUserController;


    /**
     * Constructs a MenuController with the specified shared context, view, authentication service, and email service.
     * Initializes the controller with necessary services and context for its operation.
     *
     * @param sharedContext The shared application context.
     * @param view          The view for user interaction.
     * @param authService   The service for authentication.
     * @param emailService  The service for email operations.
     */
    public MenuController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Displays the main menu based on the current user's role and handles the user's menu selection.
     * Delegates actions to the appropriate controller based on the selected option.
     */
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

    /**
     * Handles the guest user's main menu, offering options such as login, consult FAQ, search pages, and contact staff.
     *
     * @return true if the menu was handled successfully.
     */
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

    /**
     * Handles the student user's main menu, offering options such as logout, consult FAQ, search pages, and contact staff.
     *
     * @return true if the menu was handled successfully.
     */
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

    /**
     * Handles the teaching staff's main menu, offering options such as logout and manage received inquiries.
     *
     * @return true if the menu was handled successfully.
     */
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

    /**
     * Handles the admin staff's main menu, offering options such as logout, manage inquiries, add page, view all pages, and manage FAQ.
     *
     * @return true if the menu was handled successfully.
     */
    private boolean handleAdminStaffMainMenu() {
        inquirerController = new InquirerController(sharedContext, view, authService, emailService);
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
