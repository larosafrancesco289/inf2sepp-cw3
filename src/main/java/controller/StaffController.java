package controller;

import external.*;
import model.*;
import view.*;

import java.util.Collection;
import java.util.List;

public class StaffController extends Controller{
    public StaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    protected Collection<String> getInquiryTitles(Collection<Inquiry> inquiries){
        return null;
    }

    protected void respondToInquiry(Inquiry inquiry){
    }
}
