package flymetomars.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by greyson on 24/5/17.
 */
public class Group {
    private String gName;
    private String gDescription;
    private Set<Person> gMembers;

    public Group() {
        gMembers = new HashSet<Person>();
    }

    public Group(String name, String description) {
        setGroupName(name);
        setGroupDescription(description);
        gMembers = new HashSet<Person>();
    }

    public String getGroupName() {
        return gName;
    }

    public String getGroupDescription() {
        return gDescription;
    }

    public Set<Person> getMembers() {
        return gMembers;
    }

    public void setGroupName(String name) {
        if (null == name) {
            throw new IllegalArgumentException("Group name cannot be null.");
        } else if (name.trim().equals("")) {
            throw new IllegalArgumentException("Group name cannot be empty.");
        }
        this.gName = name;
    }

    public void setGroupDescription(String description) {
        if (null == description) {
            throw new IllegalArgumentException("Description cannot be null.");
        } else if (description.trim().equals("")) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.gDescription = description;
    }

    public void setGroupMembers(Set<Person> people) {
        if (null == people)
            throw new IllegalArgumentException("Member list cannot be null.");
        this.gMembers = people;
    }

    public void addGroupMembers(Person p) {
        if (null == p)
            throw new IllegalArgumentException("Member cannot be null.");
        this.gMembers.add(p);
    }

    public void deleteGroupMembers(Person p) {
        if (null == p)
            throw new IllegalArgumentException("Member cannot be null.");
        this.gMembers.remove(p);
    }
}
