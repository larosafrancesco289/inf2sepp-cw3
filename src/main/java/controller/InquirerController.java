package controller;

import external.*;
import model.*;
import view.*;
public class InquirerController extends Controller{
    public InquirerController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void consultFAQ() {
    }

    public void searchPages() {
    }

    public void contactStaff() {
    }

    private void requestFAQUpdates(String topic, String email) {
    }

    private void stopFAQUpdates(String topic, String email) {
    }
}
