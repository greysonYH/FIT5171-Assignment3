package flymetomars.model;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "persons")
public class Person extends SeriablizableEntity {
    @DatabaseField
    private String firstName;

    @DatabaseField(canBeNull = false)
    private String lastName;

    @DatabaseField(canBeNull = false, unique = true)
    private String email;

    @DatabaseField(canBeNull = false)
    private String password;

    @ForeignCollectionField
    private Collection<Expertise> expertise;

    private Set<Equipment> equipmentOwned;

    private Set<Mission> missionRegistered;

    @ForeignCollectionField
    private Collection<Invitation> invitationsReceived;

    public Person() {
        expertise = new HashSet<>();
        missionRegistered = new HashSet<>();
        invitationsReceived = new HashSet<>();
        equipmentOwned = new HashSet<>();

    }

    public Person(String email) {
        this();
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (null == firstName) {
            throw new IllegalArgumentException("firstName cannot be null.");
        } else if (firstName.trim().equals("")) {
            throw new IllegalArgumentException("firstName cannot be empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (null == lastName) {
            throw new IllegalArgumentException("lastName cannot be null.");
        } else if (lastName.trim().equals("")) {
            throw new IllegalArgumentException("lastName cannot be empty.");
        }
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (null == password) {
            throw new IllegalArgumentException("Password cannot be null.");
        } else if (password.trim().equals("")) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (null == email) {
            throw new IllegalArgumentException("email cannot be null.");
        } else if (email.trim().equals("")) {
            throw new IllegalArgumentException("email cannot be empty.");
        }
        this.email = email;
    }

    public Set<Mission> getMissionRegistered() {
        return missionRegistered;
    }

    public void setMissionRegistered(Set<Mission> missionRegistered) {
        if(missionRegistered == null) throw new IllegalArgumentException("missionRegistered set cannot be null");
        this.missionRegistered = missionRegistered;
    }

    public Collection<Invitation> getInvitationsReceived() {
        return invitationsReceived;
    }

    public void setInvitationsReceived(Set<Invitation> invitationsReceived) {
        if(invitationsReceived == null) throw new IllegalArgumentException("invitationsReceived set cannot be null");
        this.invitationsReceived = invitationsReceived;
    }

    public Collection<Expertise> getExpertise() {
        return expertise;
    }

    public void setExpertise(Set<Expertise> expertise) {
        if(expertise == null) throw new IllegalArgumentException("expertise set cannot be null");
        this.expertise = expertise;
    }

    public void addExpertise(Expertise... experties) {
        if (null == experties) {
            throw new IllegalArgumentException("Expertise cannot be null.");
        }
        for (Expertise exp : experties) {
            if (null == exp.getDescription()) {
                throw new IllegalArgumentException("Expertise cannot have null description.");
            } else if (exp.getDescription().trim().isEmpty()) {
                throw new IllegalArgumentException("Expertise cannot have empty description.");
            }
            expertise.add(exp);
        }
    }

    public Set<Equipment> getEquipmentOwned() {
        return equipmentOwned;
    }

    public void setEquipmentOwned(Set<Equipment> equipmentOwned) {
        if(equipmentOwned == null) throw new IllegalArgumentException("equipmentOwned set cannot be null");
        this.equipmentOwned = equipmentOwned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equal(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
