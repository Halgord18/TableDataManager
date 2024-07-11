package com.halgord.swing;
public class DvdReturner {

    int id;
    String firstName;
    String lastName;
    int age;

    public DvdReturner(int id, String firstName, String lastName, int age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId(){return this.id;}

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public int getAge(){return this.age;}

    public void setId(int id){this.id = id;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public void setAge(int age){this.age = age;}
}