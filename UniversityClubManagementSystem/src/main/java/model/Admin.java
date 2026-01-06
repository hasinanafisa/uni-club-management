/**
 * @izyanie
 * @24/12/2025
 */

package model;

public class Admin {
    private int adminID;
    private String adminEmail;
    private String adminPassword;
    
    //getters
    public int getAdminID() { return adminID; }
    public String getAdminEmail() { return adminEmail; }
    public String getAdminPassword() { return adminPassword; }
    
    //setters
    public void setAdminID(int adminID) { this.adminID = adminID; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}
