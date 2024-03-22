package model;

/**
 * The User class is an abstract class representing a user.
 * It provides a method to retrieve the role of the user.
 */
public abstract class User {

    /**
     * Retrieves the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return "Guest";
    }
}

