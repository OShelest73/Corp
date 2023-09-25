package lab3;

import java.util.ArrayList;

public class PCComponent {
    private String Name;
    private String Origin;
    private double Price;
    //private ArrayList<String> Type = new ArrayList();
    private boolean IsCritical;

    public PCComponent(){}

    public String getName(){return Name;}
    public void setName(String Name){this.Name = Name;}
    public String getOrigin(){return Origin;}
    public void setOrigin(String Origin){this.Origin = Origin;}
    public double getPrice(){return Price;}
    public void setPrice(double Price){this.Price = Price;}
    //public ArrayList<String> getType(){return Type;}
    //public void setType(ArrayList<String> Type){this.Type = Type;}
    public boolean getIsCritical(){return IsCritical;}
    public void setIsCritical(boolean IsCritical){this.IsCritical = IsCritical;}

    public void validate() throws InvalidComponentException {
        if (Price < 0) {
            throw new InvalidComponentException("Invalid Price (must be more then 0)");
        }
    }
}


