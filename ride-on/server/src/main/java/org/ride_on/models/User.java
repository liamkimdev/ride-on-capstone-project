package org.ride_on.models;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String bankingAccount;
    private String identification;
    private String preferences;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String bankingAccount, String identification, String preferences) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankingAccount = bankingAccount;
        this.identification = identification;
        this.preferences = preferences;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBankingAccount() {
        return bankingAccount;
    }

    public void setBankingAccount(String bankingAccount) {
        this.bankingAccount = bankingAccount;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}
