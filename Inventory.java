import java.util.HashMap;
import java.util.Map;

public class Inventory {
    static HashMap<String, Product> productMap = new HashMap<>();
    static HashMap<Location, Unit> locationMap = new HashMap<>();

    public Inventory(){}

    public static void addProduct(Product product){
        productMap.put(product.productId, product);
    }

    public static Product getProduct(String productId){
        return productMap.get(productId);
    }

    public static void placeUnit(Unit unit){
        for(Map.Entry<Location, Unit> item: locationMap.entrySet()){
            if(item.getValue() == null)
            {
                unit.locationId = item.getKey().locationId;
            }
        }
    }

    public static void removeUnit(Product product){
        for(Map.Entry<Location, Unit> item: locationMap.entrySet()){
            if(item.getValue() == null && product.productId == item.getValue().productId){
                locationMap.remove(item.getKey());
            }
        }
    }

    public static HashMap<Location, Unit> getShelveStatus(){
        return locationMap;
    }

    public static void updateUnitStatus(Unit unit, UnitStatus status){
        unit.unitstatus = status;
    }
}

class Product{
    String productId;
    String name;
    Double price;
    String description;
    Double weight;
    Size size;

    public Product(String productId, String name, Double price, String description, Double weight, Size size)
    {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.size = size;
    }
}

class User{
    String name;
    String userId;
    
    public void addProduct(){
        Product product = new Product("dshfhfs1232","Cheese",10.00,"Buy some awesome cheese", 5.0, Size.SMALL);
        Inventory.addProduct(product);
    }

    public void executeOrder(Order order){
        for(Map.Entry<Product, Integer> item: order.orderList.entrySet()){
            for(int idx = 0; idx < item.getValue(); idx++){
                Inventory.removeUnit(item.getKey());
            }
        }
    }

    
}

class Location{
    String locationId;
    Size size;
}

class Unit{
    String productId;
    String locationId;
    String unitId;
    UnitStatus unitstatus;
}

enum UnitStatus{
    INVENTORY, TRANSIT, DELIVERY;
}

enum Size{
    SMALL, MEDIUM, LARGE;
}

class Order{
    HashMap<Product, Integer> orderList = new HashMap<>();
}