/**
 * @izyanie
 * @24/12/2025
 */

package model;

import java.sql.Timestamp;

public class Announcement {
    private int announcementId;
    private int clubId;
    private int eventId;
    private String title;
    private String content;
    private String category;
    private String imagePath;
    private String attachmentPath;
    private int postedBy;
    private Timestamp postedAt;

    //Constructor
    public Announcement() {}
    
    // getters
    public int getAnnouncementId() { return announcementId; }
    public int getClubId() { return clubId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getImagePath() { return imagePath; }
    public String getAttachmentPath() { return attachmentPath; }
    public int getPostedBy() { return postedBy; }
    public Timestamp getPostedAt() { return postedAt; }
    public int getEventId() { return eventId; }
    
    //setters
    public void setAnnouncementId(int announcementId) { this.announcementId = announcementId; }
    public void setClubId(int clubId) { this.clubId = clubId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }
    public void setPostedBy(int postedBy) { this.postedBy = postedBy; }
    public void setPostedAt(Timestamp postedAt) { this.postedAt = postedAt; }
    public void setEventId(int eventId) { this.eventId = eventId; }
}