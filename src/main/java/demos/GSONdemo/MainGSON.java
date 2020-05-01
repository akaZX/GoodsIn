package demos.GSONdemo;

import app.pojos.RmtQaRecords;
import com.google.gson.Gson;

import java.time.LocalDateTime;

public class MainGSON {

    public static void main(String[] args){

        RmtQaRecords atributes = new RmtQaRecords();
        atributes.setAuthor("asdassd");


        atributes.setDate(LocalDateTime.now());
        System.out.println("Before:" + atributes);

        Gson gson = new Gson();
        String json = gson.toJson(atributes);
        RmtQaRecords atributes1 = gson.fromJson(json, RmtQaRecords.class);
        System.out.println("\n\n\n\n\nAfter: " + atributes1);
        System.out.println(json);


    }


}
