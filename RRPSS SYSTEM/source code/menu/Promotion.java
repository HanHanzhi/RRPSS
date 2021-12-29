package menu;

/**
 * Represents a Promotion offered in the Restaurant
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class Promotion extends MenuSection{

    /**
     * The description of this Promotion.
     */
    private String description;
    /**
     * The single package price of this Promotion.
     */
    private double promoPrice;

    /**
     * Creates a default new Promotion with ID number -1 and no details.
     */
    public Promotion(){
        super(-1,"no promo name");
        this.promoPrice = 0;
    }
    /**
     * Creates a new Promotion with the given ID, name and price.
     * @param promotionID   This Promotion's ID number.
     * @param name          This Promotion's name.
     * @param description   This Promotion's description.
     * @param promoPrice    This Promotion's package price.
     */
    public Promotion(int promotionID, String name, String description, double promoPrice){
        super(promotionID, name);
        this.description = description;
        this.promoPrice = promoPrice;
    }
    /**
     * Get the ID number of this Promotion
     * @return this Promotion's ID number
     */

    public int getPromoID(){
        return super.getMenuSectionID();
    }
    /**
     * Change the ID number of this Promotion
     * @param promoID the Promotion new ID number
     * @return true when successful, othwerwise false
     */
    public boolean setPromoID(int promoID){
        return super.setMenuSectionID(promoID);
    }
    /**
     * Get the single package price of this Promotion
     * @return  this Promotion's single package price
     */
    public double getPromoPrice(){
        return this.promoPrice;
    }
    /**
     * Change the single package price of this Promotion
     * @param promoPrice the Promotion new single package price
     */
    public void setPromoPrice(double promoPrice){
        this.promoPrice = promoPrice;
        return;
    }
    /**
     * Get the description of this Promotion
     * @return this Promotion's description
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * Change the description of this Promotion
     * @param description the Promotion new description
     */
    public void setDescription(String description){
        this.description = description;
        return;
    }
    /**
     * Add MenuItem to this Promotion
     * @param menuItem the MenuItem to add into the Promotion
     * @return true when successful, othwerwise false
     */
    public boolean addPromoItem(MenuItem menuItem){
        return super.addMenuItem(menuItem);
    }
    /**
     * Remove MenuItem from this Promotion
     * @param menuItem the MenuItem to remove from the Promotion
     * @return true when successful, othwerwise false
     */
    public boolean removePromoItem(MenuItem menuItem){
        return super.removeMenuItem(menuItem);
    }


}
