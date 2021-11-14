package HumanManagementMVC.EnumAndSubclass;

/**
 * Sex enum contains kinds of sex of the human
 */
public enum Sex {
    MALE,
    FEMALE,
    OTHER;

    /**
     * <p>This static method uses to check the sex of the staff </p>
     * <p>This method loops through each element in {@code Sex} enum
     * and compares it with input sex and return the corresponding {@code Sex} value</p>
     * @param sex a {@code String} object represents for the sex of the staff wants to check
     * @return a {@code Sex} enum represents for the sex, if the input
     *           {@code String} isn't correct return null
     */
    public static Sex checkSex(String sex){
        for(Sex sexValue: Sex.values())
            if(sexValue.toString().equals(sex))
                return sexValue;

        return null;
    }
}
