package flymetomars.model;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.*;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "missions")
public class Mission extends SeriablizableEntity {
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        preliminary, finalised
    }

    @DatabaseField(canBeNull = false)
    private Date time;

    @DatabaseField(canBeNull = false)
    private int budget;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private int duration;

    @DatabaseField(canBeNull = false)
    private Status status;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person captain;

    @DatabaseField(canBeNull = false)
    private String location;

    @DatabaseField()
    private String description;

    private Set<Person> participantSet;

    @ForeignCollectionField
    private Collection<Invitation> invitationSet;

    private Set<Expertise> expertiseRequired;
    private Multiset<Equipment> equipmentsRequired;

    private Set<Group> registeredGroup;
    private Map<Equipment.Attribute, Integer> maxAttributes;

    public Mission() {
        status = Status.preliminary;
        invitationSet = new HashSet<>();
        participantSet = new HashSet<>();
        expertiseRequired = new HashSet<>();
        maxAttributes = new HashMap<>();
        equipmentsRequired = HashMultiset.create();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be null");
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        if(time == null) throw new IllegalArgumentException("Time cannot be null");
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location == null) throw new IllegalArgumentException("Location cannot be null");
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null) throw new IllegalArgumentException("Description cannot be null");
        this.description = description;
    }

    public Collection<Invitation> getInvitationSet() {
        return invitationSet;
    }

    public void setInvitationSet(Collection<Invitation> invitationSet) {
        if(invitationSet == null) throw new IllegalArgumentException("Invitation set cannot be null");
        this.invitationSet = invitationSet;
    }

    public Set<Person> getParticipantSet() {
        return participantSet;
    }

    public void setParticipantSet(Set<Person> participantSet) {
        if(participantSet == null) throw new IllegalArgumentException("Participant set cannot be null");
        this.participantSet = participantSet;
    }

    public Person getCaptain() {
        return captain;
    }

    public void setCaptain(Person captain) {
        if(captain == null) throw new IllegalArgumentException("Captain cannot be null");
        this.captain = captain;
    }

    public Multiset<Equipment> getEquipmentsRequired() {
        return equipmentsRequired;
    }

    public void setEquipmentsRequired(Multiset<Equipment> equipmentsRequired) {
        if(equipmentsRequired == null) throw new IllegalArgumentException("equipmentsRequired set cannot be null");
        this.equipmentsRequired = equipmentsRequired;
    }

    public Set<Expertise> getExpertiseRequired() {
        return expertiseRequired;
    }

    public void setExpertiseRequired(Set<Expertise> expertiseRequired) {
        this.expertiseRequired = expertiseRequired;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if(duration < 0) throw  new IllegalArgumentException("Duration cannot be null");
        this.duration = duration;
    }

    public Map<Equipment.Attribute, Integer> getMaxAttributes() {
        return maxAttributes;
    }

    public void setMaxAttributes(Map<Equipment.Attribute, Integer> maxAttributes) {
        this.maxAttributes = maxAttributes;
    }

    ////
    public int getMaxAttributeVolume() {
        return maxAttributes.get(Equipment.Attribute.volume);
    }

    public int getMaxAttributeWeight() {
        return maxAttributes.get(Equipment.Attribute.weight);
    }

    public int getMaxAttributeCost() {
        return maxAttributes.get(Equipment.Attribute.cost);
    }

    public int getEquipmentTotalVolume(){
        int total = 0;
        for (Multiset.Entry<Equipment> entry : equipmentsRequired.entrySet())
        {
            total += entry.getElement().getVolume() * entry.getCount();
        }
        return  total;
    }

    public int getEquipmentTotalWeight(){
        int total = 0;
        for (Multiset.Entry<Equipment> entry : equipmentsRequired.entrySet())
        {
            total += entry.getElement().getWeight() * entry.getCount();
        }
        return  total;
    }

    public int getEquipmentTotalCost(){
        int total = 0;

        for (Multiset.Entry<Equipment> entry : equipmentsRequired.entrySet())
        {
            total += entry.getElement().getCost() * entry.getCount();
        }
        return  total;
    }

    public void addEquipment(Equipment equipment){
        equipmentsRequired.add(equipment);
    }

    public Set<Group> getGroups() {
        return registeredGroup;
    }

    public void setRegisteredGroup(Set<Group> groups) {
        if (null == groups)
            throw new IllegalArgumentException("groups cannot be null.");
        this.registeredGroup = groups;
    }

    public void addGroup(Group g) {
        if (null == g)
            throw new IllegalArgumentException("group cannot be null.");
        else if (g.getMembers().isEmpty())
            throw new IllegalArgumentException("No member in this group.");
        registeredGroup.add(g);
    }

    public void addParticipant(Person person){ participantSet.add(person); }

    public boolean isPersonInParticipantSet(Person person){ return participantSet.contains(person); }

    ///

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return duration == mission.duration &&
                Objects.equal(time, mission.time) &&
                Objects.equal(name, mission.name) &&
                Objects.equal(location, mission.location) &&
                Objects.equal(captain, mission.getCaptain()) &&
                Objects.equal(description, mission.description) &&
                Objects.equal(maxAttributes, mission.maxAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time, name, duration, location, captain, description, maxAttributes);
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
