package flymetomars.model;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "invitation")
public class Invitation extends SeriablizableEntity {

    public enum InvitationStatus {
        SENT("sent"),
        CREATED("created"),
        ACCEPTED("accepted"),
        DECLINED("declined");

        private String name;

        private InvitationStatus(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Mission mission;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person creator;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person recipient;

    @DatabaseField(canBeNull = false)///
    private Date lastUpdated;

    @DatabaseField(canBeNull = false)
    private InvitationStatus status;

    public Invitation() {
        status = InvitationStatus.CREATED;
        mission = new Mission();
        creator = new Person();
        recipient = new Person();
        lastUpdated = new Date(System.currentTimeMillis());
    }

    public Mission getMission() {
        return mission;
    }


    public void setMission(Mission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("Mission cannot be null.");
        }
        this.mission = mission;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null.");
        }
        this.creator = creator;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        if (lastUpdated == null) {
            throw new IllegalArgumentException("LastUpdated cannot be null.");
        }
        this.lastUpdated = lastUpdated;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null.");
        } else if (creator.getEmail() == recipient.getEmail()) {
            throw new IllegalArgumentException("recipient and creator cant be the same person.");
        }
        this.recipient = recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return Objects.equal(mission, that.mission) &&
                Objects.equal(creator, that.creator) &&
                Objects.equal(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mission, creator, recipient);
    }

}
