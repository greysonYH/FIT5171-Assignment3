package flymetomars.core.check;

import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import flymetomars.model.Equipment;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class Validator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    /**
     * Needs to validate the following.
     *
     * 1. A mission needs to have a time, a name, a duration, a location, a description,
     *    as well as a non-empty maxAttributes field variable.
     * 2. A mission needs satisfy its equipment requirements. For this purpose, two pieces
     *    of equipments are considered the same if they have the same name.
     * 3. The sum of a particular attribute value of all equipments in a mission cannot exceed
     *    the maximally allowed value of that attribute of the mission.
     * 4. A mission needs to satisfy all its required expertise to be viable.
     *
     * @param mission
     * @return
     * @throws ValidationException
     */

    public Mission finaliseBase(Mission mission) throws ValidationException {
        Set<ValidationError> validationErrors = Sets.newHashSet();

        Set<Person> persons = mission.getParticipantSet();
        Set<Expertise> expertisesRequired = mission.getExpertiseRequired();

        Set<Expertise> allExpertises = Sets.newHashSet();
        for (Person p : persons) {
            allExpertises.addAll(p.getExpertise());
        }

        Sets.SetView<Expertise> difference = Sets.difference(expertisesRequired, allExpertises);
        if (!difference.isEmpty()) {
            ValidationError error = new ValidationError("expertiseRequired",
                    "Required expertise not satisfied: " + difference.toString());
            validationErrors.add(error);
        }

        if (validationErrors.isEmpty()) {
            mission.setStatus(Mission.Status.finalised);
            return mission;
        } else {
            ValidationException exception = new ValidationException("Mission cannot be validated", validationErrors);
            throw exception;
        }
    }

    public Mission finalise(Mission mission) throws ValidationException {
        Set<ValidationError> validationErrors = Sets.newHashSet();

        if(!isStringValid(mission.getName())){
            validationErrors.add(new ValidationError("Name","Name null or empty"));
        }
        if(!isStringValid(mission.getLocation())){
            validationErrors.add(new ValidationError("Location","Location null or empty"));
        }
        if(!isStringValid(mission.getDescription())){
            validationErrors.add(new ValidationError("Description","Description null or empty"));
        }
        if(mission.getDuration() <= 0){
            validationErrors.add(new ValidationError("Duration","Duration is invalid"));
        }
        if(mission.getTime() == null){
            validationErrors.add(new ValidationError("Time","Time is null"));
        }
        if(!isEquipmentValid(mission)) {
            validationErrors.add(new ValidationError("Equipment", "Equipment is required"));
        }
        if(!isMaxAttributeValid(mission)) {
            validationErrors.add(new ValidationError("MaxAttribute", "MaxAttribute is not valid"));
        }
        if(!isExpertiseValid(mission)) {
            validationErrors.add(new ValidationError("Expertise", "Expertise is required"));
        }
        if (validationErrors.isEmpty()) {
            mission.setStatus(Mission.Status.finalised);
            return mission;
        } else {
            throw new ValidationException("Mission cannot be validated", validationErrors);
        }
    }

    private  boolean isMaxAttributeValid(Mission mission){
        if(mission.getMaxAttributes() == null || mission.getMaxAttributes().isEmpty()) return  false;
        if(mission.getMaxAttributeVolume() < mission.getEquipmentTotalVolume()) return false;
        if(mission.getMaxAttributeWeight() < mission.getEquipmentTotalWeight()) return  false;
        if(mission.getMaxAttributeCost()< mission.getEquipmentTotalCost()) return  false;

        return  true;
    }

    private  boolean isEquipmentValid(Mission mission){
        Multiset<Equipment> currentEquipment = mission.getEquipmentsRequired();
        boolean valid = true;
        if(!currentEquipment.containsAll(mission.getEquipmentsRequired())) {
            valid = false;
        } else {
            Iterator i = mission.getEquipmentsRequired().iterator();
            while (i.hasNext() && valid) {
                Equipment q = (Equipment) i.next();
                if (mission.getEquipmentsRequired().count(q) > currentEquipment.count(q)) {
                    valid = false;
                }
            }
        }
        return  valid;
    }


    private boolean isExpertiseValid(Mission mission){
        Set<Expertise> currentExpertise = mission.getExpertiseRequired();
        if(currentExpertise.containsAll(mission.getExpertiseRequired()))
            return  true;
        else
            return  false;
    }

    /**
     * Needs to validate the following.
     *
     * 1. A person needs to have a valid email address.
     * 2. A person needs to have a non-empty password.
     * 3. A person needs to have a non-empty last name.
     *
     * @param person
     * @return
     * @throws ValidationException
     */

    public Person setPersonEmail(Person person, String email) throws ValidationException {
        Set<ValidationError> validationErrors = Sets.newHashSet();
        if (!emailValidation(email)) {
            validationErrors.add(new ValidationError("Email","Email is invalid."));
        }
        if (validationErrors.isEmpty()) {
            person.setEmail(email);
            return person;
        } else {
            throw new ValidationException("Mission cannot be validated", validationErrors);
        }
    }

    public Person setPersonPassword(Person person, String password) throws ValidationException {
        Set<ValidationError> validationErrors = Sets.newHashSet();
        if (!passwordValidation(password)) {
            validationErrors.add(new ValidationError("Password","Password is invalid."));
        }
        if (validationErrors.isEmpty()) {
            person.setPassword(password);
            return person;
        } else {
            throw new ValidationException("Mission cannot be validated", validationErrors);
        }
    }
    // Email constraint
    private boolean emailValidation(String email) {
        boolean valid = true;
        if (null == email) {
            valid = false;
        } else if (email.trim().equals("")) {
            valid = false;
        } else {
            pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            valid = matcher.matches();
        }
        return valid;
    }

    /**
     * Password constraint
     * Password should be less than 16 and more than 8 characters in length.
     * Password should contain at least one upper case and one lower case alphabet.
     * Password should contain at least one number.
     * Password should contain at least one special character.
     **/
    // extended by llx
    private static boolean passwordValidation(String password)
    {
        boolean valid = true;
        if (password.length() > 16 || password.length() < 8)
            valid = false;
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
            valid = false;
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
            valid = false;
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
            valid = false;
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
            valid = false;
        return valid;
    }

    // General string constraint
    private boolean isStringValid(String anyString) {
        boolean valid = true;
        if (anyString == null || anyString.trim().equals("")) {
            valid = false;
        }
        return valid;
    }
}
