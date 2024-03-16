package controller;

import external.*;
import model.*;
import view.*;
public class GuestController extends Controller{
    public GuestController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void login(){
    }
}
