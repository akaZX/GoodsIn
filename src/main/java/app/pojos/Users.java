package app.pojos;


public class Users {

    private String username;

    private String name;

    private String surname;

    private String email;

    private int goodsIn;

    private int rmtGoodsIn;

    private int security;

    private int fruit;

    private int admin;

    private int rmtAdmin;


    public String getEmail() {

        return email;
    }


    public void setEmail(String email) {

        this.email = email;
    }


    public boolean getGoodsIn() {

        return goodsIn == 1;
    }


    public void setGoodsIn(int goodsIn) {

        this.goodsIn = goodsIn;
    }


    public boolean getRmtGoodsIn() {

        return rmtGoodsIn == 1;
    }


    public void setRmtGoodsIn(int rmtGoodsIn) {

        this.rmtGoodsIn = rmtGoodsIn;
    }


    public boolean getSecurity() {

        return security == 1;
    }


    public void setSecurity(int security) {

        this.security = security;
    }


    public boolean getFruit() {

        return fruit == 1;
    }


    public void setFruit(int fruit) {

        this.fruit = fruit;
    }


    public boolean getAdmin() {

        return (admin == 1);
    }


    public void setAdmin(int admin) {

        this.admin = admin;
    }


    public boolean getRmtAdmin() {

        return rmtAdmin == 1;
    }


    public void setRmtAdmin(int rmtAdmin) {

        this.rmtAdmin = rmtAdmin;
    }


    @Override
    public String toString() {


        return "Username: " + getUsername() + "\n" + getName() + " " + getSurname();
    }


    public String getUsername() {

        return username;
    }


    public void setUsername(String username) {

        this.username = username;
    }


    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


    public String getSurname() {

        return surname;
    }


    public void setSurname(String surname) {

        this.surname = surname;
    }
}
