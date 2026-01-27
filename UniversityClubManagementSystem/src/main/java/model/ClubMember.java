/**
 * @izyanie
 * @28/01/2026
 */

package model;

import java.io.Serializable;
import java.sql.Date;

public class ClubMember implements Serializable {

    private int clubMemberId;
    private int userId;
    private int clubId;
    private String role; //Advisor, President, Vice President, Secretary, Treasurer, Member
    private Date joinDate;

    public ClubMember() {}

    public ClubMember(int clubMemberId, int userId, int clubId, String role, Date joinDate) {
        this.clubMemberId = clubMemberId;
        this.userId = userId;
        this.clubId = clubId;
        this.role = role;
        this.joinDate = joinDate;
    }

    public int getClubMemberId() { return clubMemberId; }
    public void setClubMemberId(int clubMemberId) { this.clubMemberId = clubMemberId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getClubId() { return clubId; }
    public void setClubId(int clubId) { this.clubId = clubId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
}
