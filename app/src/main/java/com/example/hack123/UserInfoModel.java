package com.example.hack123;

public class UserInfoModel {

    public UserInfoModel(){

    }

    public String getDocURl() {
        return docURl;
    }

    public void setDocURl(String docURl) {
        this.docURl = docURl;
    }

    String name,email,password,phone,mess_bill,verify,docURl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMess_bill() {
        return mess_bill;
    }

    public void setMess_bill(String mess_bill) {
        this.mess_bill = mess_bill;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public UserInfoModel(String name, String email, String password, String phone, String mess_bill, String verify,String docURl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.mess_bill = mess_bill;
        this.verify = verify;
        this.docURl=docURl;
    }
}
