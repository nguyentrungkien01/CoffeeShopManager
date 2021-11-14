package InfrastructureManagementMVC.Enum;

/**
 * Material enum contains all kinds of material
 */
public enum Material {
    WOODEN,
    IRON,
    GLASSES,
    PLASTIC,
    STONE;

    /**
     * <p>This static method uses to check the material of the infrastructure </p>
     * <p>This method loops through each element in {@code Material} enum
     * and compares it with input material and return the corresponding {@code Material} value</p>
     *
     * @param material a {@code String} object represents for the material of the infrastructure wants to check
     *
     * @return a {@code Material} enum represents for the material, if the input
     *       {@code String} isn't correct return null
     */
    public static Material checkMaterial(String material){
        for (Material value: Material.values())
            if(material.equals(value.toString()))
                return value;
        return null;
    }
}
