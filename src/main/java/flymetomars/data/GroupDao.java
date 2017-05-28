package flymetomars.data;

import flymetomars.model.Group;
import flymetomars.model.Person;

import java.util.Set;

/**
 * Created by greyson on 24/5/17.
 */
public interface GroupDao {
    Set<Group> getGroupsByPerson(Person person);
}
