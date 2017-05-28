package flymetomars.model;

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class Equipment {
    public enum Attribute {
        weight, volume, cost
    }

    private String name;
    private Map<Attribute, Integer> attributes;

    public  Equipment(){
        this.attributes = new HashMap<>();
    }

    public Equipment(String name, int weight, int volume, int cost) {

        this.attributes = new HashMap<>();
        setName(name);
        setWeight(weight);
        setVolume(volume);
        setCost(cost);

    }

    public Map<Attribute, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Attribute, Integer> attributes) {
        this.attributes = attributes;
    }


    public String getName() {
        return name;
    }
    public int getWeight() {

        return attributes.get(Attribute.weight);
    }
    public int getVolume() {
        return attributes.get(Attribute.volume);
    }
    public int getCost(){
        return  attributes.get(Attribute.cost);
    }

    public void setName(String name){
        if(name == null) throw new IllegalArgumentException("Name cannot be null");
        if(name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setWeight(int weight){
        if(weight < 0 ) throw new IllegalArgumentException("Weight cannot be negative");
        attributes.put(Attribute.weight, weight);
    }

    public void setVolume(int volume){
        if(volume < 0 ) throw new IllegalArgumentException("Volume cannot be negative");
        attributes.put(Attribute.volume, volume);
    }

    public void setCost(int cost){
        if(cost < 0 ) throw new IllegalArgumentException("Cost cannot be negative");
        attributes.put(Attribute.cost, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equal(name, equipment.name) &&
                Objects.equal(attributes, equipment.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, attributes);
    }

}
