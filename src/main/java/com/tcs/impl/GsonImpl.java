package com.tcs.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.tcs.model.Address;
import com.tcs.model.Foo;
import com.tcs.model.Person;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GsonImpl {

    public static void main(String args[]){
        Gson gson = new Gson(); //Initialize GSON class object

        Person person = new Person();
        person.setFirstName("Prashast");
        //person.setLastName("Saxena");
        person.setAge(25);
        person.setGender("Male");
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10); numbers.add(20);numbers.add(30);numbers.add(40);
        person.setNumbers(numbers);

        Address address = new Address();
        address.setCity("Mumbai");
        address.setState("MH");
        address.setCountry("IN");
        person.setAddress(address);
        String json = gson.toJson(person);  //Converting object to jSON format
        System.out.println("normal converting Person object to JSON");
        System.out.println(json);
        Person per = gson.fromJson(json, Person.class);    //Converting JSON to Class
        System.out.println("Converted JSON To Person object, so printing first name attribute of it");
        System.out.println(per.getAddress().getCity());

        gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC,Modifier.FINAL).create();
        System.out.println("Only  displaying non static and non final entries");
        System.out.println(gson.toJson(person));

        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        System.out.println("only displaying entries which do have Expose annotation with them");
        System.out.println(gson.toJson(person));

        gson = new GsonBuilder().serializeNulls().create();
        System.out.println("Also printing null attribs");
        System.out.println(gson.toJson(person));

        gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Pretty printing JSON ");
        System.out.println(gson.toJson(person));


        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).serializeNulls().create();
        System.out.println("Camel case printing..Applies only to those fields which don't have @SerializedName annotation with them");
        System.out.println(gson.toJson(person));

        gson = new GsonBuilder().setVersion(1.0).serializeNulls().create();
        System.out.println("Exclude those fields having version specified more than 1.0. Done by @Specify annotation");
        System.out.println(gson.toJson(person));

        //Gson impl for Generic Types

        gson = new Gson();
        Foo<Address> addressFoo = new Foo<Address>();
        addressFoo.setValue(address);
        Type type = new TypeToken<Foo<Address>>(){}.getType();
        json = gson.toJson(addressFoo);
        System.out.println(json);
        addressFoo = gson.fromJson(json, type);
        System.out.println(addressFoo.getValue().getCountry());

        //Custom Serialization and De-serialization
        System.out.println("Serialization starts from here");
        gson = new GsonBuilder().registerTypeAdapter(Person.class, new PersonSerializer()).setPrettyPrinting().create();

        person.setAddress(address);
        json = gson.toJson(person);
        System.out.println(json);
        gson = new GsonBuilder().registerTypeAdapter(Person.class, new PersonDeSerializer()).setPrettyPrinting().create();
        person = gson.fromJson(json, Person.class);
        System.out.println(person.getAddress().getCountry());
    }


}

class PersonSerializer implements JsonSerializer<Person>{

    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", person.getFirstName());
        obj.addProperty("country", person.getAddress().getCountry());
        return obj;
    }
}


class PersonDeSerializer implements JsonDeserializer<Person>{
    public Person deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Person person = new Person();

        person.setFirstName(jsonElement.getAsJsonObject().get("name").getAsString());
        Address address = new Address();
        address.setCountry(jsonElement.getAsJsonObject().get("country").getAsString());
        person.setAddress(address);
        return person;
    }
}

