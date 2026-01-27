/**
 * @izyanie
 * @28/01/2026
 */

package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Club implements Serializable {

    private int clubId;
    private String clubName;
    private String description;
    private String logo;
    private int createdBy; // user_id (advisor)
    private Timestamp createdAt;

    // Default constructor
    public Club() {}

    // Constructor for creating a new club
    public Club(int clubId, String clubName, String description, String logo, int createdBy) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.description = description;
        this.logo = logo;
        this.createdBy = createdBy;
    }
    
    // Full cnstructor (used when fetching from DB)
    public Club(int clubId, String clubName, String description, String logo, int createdBy, Timestamp createdAt) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.description = description;
        this.logo = logo;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getClubId() { return clubId;}
    public void setClubId(int clubId) { this.clubId = clubId; }

    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
