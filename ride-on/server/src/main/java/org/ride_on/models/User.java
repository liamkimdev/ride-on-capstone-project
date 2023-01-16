package org.ride_on.models;

public class User {
    private int userId;
    private String bankingAccount;
    private String identification;
    private String preferences;

    public User(int userId, String bankingAccount, String identification, String preferences) {
        this.userId = userId;
        this.bankingAccount = bankingAccount;
        this.identification = identification;
        this.preferences = preferences;
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
