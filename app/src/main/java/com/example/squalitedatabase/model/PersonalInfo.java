package com.example.squalitedatabase.model;

public class PersonalInfo {
    private int id;
    private  String name;
    private String phone_no;
   public PersonalInfo(){

   }
  public PersonalInfo(String name,String phone_no){
       this.name=name;
       this.phone_no=phone_no;
   }
  public PersonalInfo(int id,String name,String phone_no){
       this(name,phone_no);
       this.id=id;
      }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
