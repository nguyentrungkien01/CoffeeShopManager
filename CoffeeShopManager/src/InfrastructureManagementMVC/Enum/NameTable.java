package InfrastructureManagementMVC.Enum;

/**
 * NameTable enum contains all kinds of name of the table
 */
public enum NameTable {
    NORMAL,
    COUPLE,
    FAMILY,
    MEETING,
    VIP1,
    VIP2,
    VIP3,
    VIP4,
    VIP5;

    /**
     * <p>This static method uses to check the nameTable of the infrastructure </p>
     * <p>This method loops through each element in {@code NameTable} enum
     * and compares it with input nameTable and return the corresponding {@code NameTable} value</p>
     *
     * @param nameTable a {@code String} object represents for the name of the infrastructure wants to check
     *
     * @return a {@code NameTable} enum represents for the name table, if the input
     *       {@code String} isn't correct return null
     */
    public static NameTable checkNameTable(String nameTable) {
        for (NameTable value : NameTable.values())
            if (nameTable.equals(value.toString()))
                return value;
        return null;
    }
}
