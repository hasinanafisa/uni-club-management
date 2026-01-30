/**
 * @izyanie
 * @28/01/2026
 */

package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Club implements Serializable {

    private int clubID;
    private String clubName;
    private String description;
    private String logoPath;
    private int createdBy; // user_id (advisor)
    private Timestamp createdAt;

    // Constructor
    public Club() {}

    public int getClubID() { return clubID; }
    public void setClubID(int clubID) { this.clubID = clubID; }

    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
