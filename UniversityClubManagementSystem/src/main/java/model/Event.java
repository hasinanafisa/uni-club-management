/**
 * @izyanie
 * @24/12/2025
 */

package model;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int eventID;
    private String eventTitle;
    private String eventDesc;
    private Date eventDate;
    private Time eventTime;
    private String eventLoc;
    private String bannerImagePath;
    private String qrPath;
    private String name;
    private Date date;
    
    //getters
    public int getEventID() { return eventID; }
    public String getEventTitle() { return eventTitle; }
    public String getEventDesc() { return eventDesc; }
    public Date getEventDate() { return eventDate; }
    public Time getEventTime() { return eventTime; }
    public String getEventLoc() { return eventLoc; }
    public String getBannerImagePath() { return bannerImagePath; }
    public String getQrPath() { return qrPath; }
    public String getName() { return name; }
    public Date getDate() { return date; }
    
    //setters
    public void setEventID(int eventID) { this.eventID = eventID; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public void setEventDesc(String eventDesc) { this.eventDesc = eventDesc; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
    public void setEventTime(Time eventTime) { this.eventTime = eventTime; }
    public void setEventLoc(String eventLoc) { this.eventLoc = eventLoc; }
    public void setBannerImagePath(String bannerImagePath) { this.bannerImagePath = bannerImagePath; }
    public void setQrPath(String qrPath) { this.qrPath = qrPath; }
    public void setName(String name) { this.name = name; }
    public void setDate(Date date) { this.date = date; }
}
