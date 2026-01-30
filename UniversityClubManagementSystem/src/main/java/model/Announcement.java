/**
 * @izyanie
 * @24/12/2025
 */

package model;

public class Announcement {
    private int announceID;
    private String announceTitle;
    private String announceContent;
    private String announceCategory;
    private String imagePath;
    private String attachmentPath;
    private int eventID;

    //Constructor
    public Announcement() {}
    
    // getters
    public int getAnnounceID() { return announceID; }
    public String getAnnounceTitle() { return announceTitle; }
    public String getAnnounceContent() { return announceContent; }
    public String getAnnounceCategory() { return announceCategory; }
    public String getImagePath() { return imagePath; }
    public String getAttachmentPath() { return attachmentPath; }
    public int getEventID() { return eventID; }
    
    //setters
    public void setAnnounceID(int announceID) { this.announceID = announceID; }
    public void setAnnounceTitle(String announceTitle) { this.announceTitle = announceTitle; }
    public void setAnnounceContent(String announceContent) { this.announceContent = announceContent; }
    public void setAnnounceCategory(String announceCategory) { this.announceCategory = announceCategory; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }
    public void setEventID(int eventID) { this.eventID = eventID; }
}