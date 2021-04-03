package project;

public class Validator {
    //validates all of the input

    protected static String errorMsg = "";

    public static boolean validateRegistration(String username, String password, String firstname, String lastname) {
        if(!validateCredentials(username, password)) return false;
        if(!validateFirstname(firstname)) return false;
        if(!validateLastname(lastname)) return false;
        return true;
    }

    public static boolean validateFirstname(String fname) {
        //First name should only have alpha and space
        if(!isAlphaSpace(fname)) {
            setErrorMess("First name should be alphabet characters only");
            return false;
        }

        //If the text field is empty, prompt
        if(!isNotEmpty(fname)) {
            setErrorMess("First name field is empty");
            return false;
        }

        //Check length of name, minimum is 2
        if(!isValidLength(fname, 2)) {
            setErrorMess("First name should contain 2 or more characters");
            return false;
        }

        return true;
    }

    public static boolean validateLastname(String lname) {
        //should only contain alphabet letters
        if(!isAlphaSpace(lname)) {
            setErrorMess("Last name should be alphabet characters only");
            return false;
        }

        //check if textfield is empty
        if(!isNotEmpty(lname)) {
            setErrorMess("Last name field is empty");
            return false;
        }

        //check length of last name field
        if(!isValidLength(lname, 2)) {
            setErrorMess("Last name should contain 2 or more characters");
            return false;
        }

        return true;
    }

    public static boolean validateCredentials(String username, String password) {
        if (!validateUsername(username)) return false;
        if (!validatePassword(password)) return false;

        return true;
    }

    public static boolean validateUsername(String username) {
        if(!isValidLength(username, 2)) {
            setErrorMess("Username must be at least 2 characters");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if(password.length() < 5) {
            setErrorMess("Password must be at least 5 characters!");
            return false;
        }
        return true;
    }


    //customized functions of settings
    public static boolean isValidLength(String str, int minimum) {
        return str.length() >= minimum;
    }

    public static boolean isNotEmpty(String str) {
        return !str.isEmpty();
    }

    public static boolean isAlphaSpace(String str) {
        return str.matches("^[a-zA-Z ]+$");
    }

    public static boolean isAlpha(String str) {
        return str.matches("^[a-zA-Z]+$");
    }

    public static boolean isNumeric(String str) {
        return str.matches("^[0-9]+$");
    }

    public static void displayError() {
        Alert.Error(errorMsg);
    }

    public static void setErrorMess(String msg) {
        errorMsg = msg;
    }

    public static String getErrorMess() {
        return errorMsg;
    }

    public static void clearErrorMess() {
        errorMsg = "";
    }
}

