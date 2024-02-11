package models;

import api.OrderClient;

import java.util.ArrayList;

public class Ingredients {
    int idBun;
    int idMain;
    int idSauce;

   OrderClient orderClient = new OrderClient();
   public ArrayList<String> getIngredientsForOrder(){
    for (int id= 0; id<5; id++){
        String pathType= "data["+id+"].type";
        if(orderClient.getListOfIngredients().path(pathType).equals("bun")){
            idBun= id;
        }
        else if (orderClient.getListOfIngredients().path(pathType).equals("main")){
            idMain = id;
        }
        else if (orderClient.getListOfIngredients().path(pathType).equals("sauce")){
            idSauce = id;
        }
    }
    String pathId= "data["+idBun+", "+idMain+", "+idSauce+"]._id";
       return orderClient.getListOfIngredients().then().extract().body().path(pathId);
   }
}
