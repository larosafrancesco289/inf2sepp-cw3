package model;

/**
 * The AuthenticatedUser class is a subclass of the User class.
 * It is used to store the authenticated user's email and role.
 * It has two attributes: email and role.
 * It has a constructor, getters, and setters for the attributes.
 */
public class AuthenticatedUser extends User {
    private String email; // The email of the authenticated user
    private String role; // The role of the authenticated user

    /**
     * Constructs a new AuthenticatedUser object with the specified email and role.
     *
     * @param email the email of the authenticated user
     * @param role  the role of the authenticated user (can be "AdminStaff", "TeachingStaff", or "Student")
     */
    public AuthenticatedUser(String email, String role) {
        this.email = email;
        this.role = role;
    }

    /**
     * Returns the email of the authenticated user.
     *
     * @return the email of the authenticated user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the authenticated user.
     *
     * @param email the new email of the authenticated user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the role of the authenticated user.
     *
     * @return the role of the authenticated user (can be "AdminStaff", "TeachingStaff", or "Student")
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the authenticated user.
     *
     * @param role the new role of the authenticated user (can be "AdminStaff", "TeachingStaff", or "Student")
     */
    public void setRole(String role) {
        this.role = role;
    }
}