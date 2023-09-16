package com.example.sofivamassage;

public class ReadWriteUserDetails {
    public String Name,surname,date,phone;

    //Constructor
    private ReadWriteUserDetails(){};
    public ReadWriteUserDetails(String TextName, String Textsurname, String Textdate, String Textphone) {
        this.Name = TextName;
        this.surname = Textsurname;
        this.date = Textdate;
        this.phone = Textphone;
    }
}
