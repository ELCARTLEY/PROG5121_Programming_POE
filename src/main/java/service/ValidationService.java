package service;

public class ValidationService {


    //
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        // SA format: +27XXXXXXXXX
        String regex = "^\\+27\\d{9}$";
        return phoneNumber.matches(regex);
    }
}
