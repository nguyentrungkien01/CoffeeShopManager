package HumanManagementMVC.EnumAndSubclass;

/**
 * Shift enum contains all kinds of shift
 */
public enum Shift {
    DAY,
    NIGHT,
    ALL;

    /**
     * <p>This static method uses to check the shift of the staff </p>
     * <p>This method loops through each element in {@code Shift} enum
     * and compares it with input shift and return the corresponding {@code Shift} value</p>
     * @param shift a {@code String} object represents for the shift of the staff wants to check
     * @return a {@code Shift} enum represents for the shift, if the input
     *          {@code String} isn't correct return null
     */
    public static Shift checkShift(String shift) {
        for (Shift value : Shift.values())
            if (value.toString().equals(shift))
                return value;
        return null;
    }
}
