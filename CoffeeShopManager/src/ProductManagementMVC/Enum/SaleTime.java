package ProductManagementMVC.Enum;

import java.util.Set;
import java.util.Vector;

/**
 * SaleTime enum contains all kinds of sale time
 */
public enum SaleTime {

    /**
     * MORNING = 0
     * MRNING begins the 5am to 11am
     */
    MORNING(0,5,11),

    /**
     * NOON = 1
     * NONN begins the 11am to 13pm
     */
    NOON(1,11,13),

    /**
     * AFTERNOON = 2
     * AFTERNOON begins the 13pm to 18pm
     */
    AFTERNOON(2,13,18),

    /**
     * NIGHT = 3
     * NIGHT begins the 18pm to 22pm
     */
    NIGHT(3,18,22),

    /**
     * LATE = 4
     * LATE begins the 22pm to 24pm
     */
    LATE(4,22,24);

    private final int DATA;//the number represents for the sale time
    private final int BEG;//the time of the beginning
    private final int END;//the time of the ending

    /**
     * <p>This constructor uses to create a <code>SaleTime</code> value</p>
     *
     * @param data a {@code int} value represents for the specific sale time
     * @param beg a {@code int} value represents for the time of the beginning
     * @param end a {@code int} value represents for the time of the ending
     */
    private SaleTime(int data, int beg, int end){
        this.DATA =data;
        this.BEG =beg;
        this.END =end;
    }

    /**
     *
     * @return the DATA
     */
    public int getData(){
        return this.DATA;
    }

    /**
     *
     * @return the BEG
     */
    public int getBeg(){
        return this.BEG;
    }

    /**
     *
     * @return the END
     */
    public int getEnd(){
        return this.END;
    }

    /**
     *
     * @param saleTimeSet a {@code Set<SaleTime>} object represents for the set of sale time of the product
     *
     * @return a {@code Vector<Integer} object represents for th sale time of the product.
     *          <ul>
     *              <li> the value 1 => represents the product with this sale time</li>
     *              <li> the value 0 => represents the product without this sale time</li>
     *          </ul>
     */
    public static Vector<Integer> getSaleTime(Set<SaleTime> saleTimeSet) {
        Vector<Integer> saleTimeVector = new Vector<>(5);
        saleTimeSet.forEach(e -> {
            for (SaleTime saleTime : SaleTime.values())
                saleTimeVector.set(e.getData(), e == saleTime ? 1 : 0);
        });
        return saleTimeVector;
    }

    /**
     * <p>THis method uses to convert list of sale time vector to a String</p>
     *
     * @param saleTimeVectorTime a {@code Vector<Integer} object represents for th sale time of the product.
     *     <ul>
     *          <li> the value 1 => represents the product with this sale time</li>
     *          <li> the value 0 => represents the product without this sale time</li>
     *     </ul>
     *
     * @return a {@code String} object represents the sale time list
     */
    public static String getSaleTime(Vector<Integer> saleTimeVectorTime) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (SaleTime saleTime : SaleTime.values())
            if (saleTimeVectorTime.get(index++) == 1) {
                if (result.length() > 0)
                    result.append(", ");
                result.append(saleTime.toString());
            }
        return result.toString();

    }
}
