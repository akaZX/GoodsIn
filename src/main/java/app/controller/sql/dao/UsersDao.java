package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Users;
import org.intellij.lang.annotations.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao implements Dao<Users> {

    private final String TABLE = "USERS";
    @Override
    public <R> Users get(R id) {

        Users user = new Users();
        ResultSet rs = SQLiteJDBC.select(TABLE, "username", id);
        try {
            while (rs.next()) {
                user = rsToObject(rs);
            }
            SQLiteJDBC.close();
        }
        catch (NullPointerException | SQLException exception) {
            exception.printStackTrace();
            SQLiteJDBC.close();
        }

        return user;
    }


    @Override
    public List<Users> getAll() {

        List<Users> list = new ArrayList<>();
        ResultSet rs = SQLiteJDBC.selectAll(TABLE, "username");

        try {
            while (rs.next()){
                list.add(rsToObject(rs));
            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<Users> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(Users users) {

        String values = "'"+ users.getUsername() + "', '"+ users.getName() + "','" +
                        users.getSurname() + "','" + users.getEmail() + "', " + boolToInt(users.getGoodsIn()) + ", " +
                        boolToInt(users.getRmtGoodsIn()) + ", " + boolToInt(users.getSecurity()) + ", " + boolToInt(users.getFruit()) +
                         ", " +boolToInt(users.getAdmin()) + ", " + boolToInt(users.getRmtAdmin()) ;

        @Language("SQLite")
        String sql ="INSERT INTO " + TABLE +
                    " (username, name, surname, email, goods_in, rmt_goods_in, security, fruit, admin, rmt_admin) VALUES(" + values + ")";
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean update(Users users) {
        @Language("SQLite")
        String sql = "Update " + TABLE + " set " + values(users) + " Where username= '" + users.getUsername() + "'";
        System.out.println(sql);
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean delete(Users users) {
        return SQLiteJDBC.delete(TABLE , "username", users.getUsername());
    }



    private String values(Users users){

        return "name='" + users.getName() +
               "', surname='" + users.getSurname() +
               "', email='" + users.getEmail() +
               "', goods_in=" + boolToInt(users.getGoodsIn()) +
               ", rmt_goods_in=" + boolToInt(users.getRmtGoodsIn()) +
               ", security=" + boolToInt(users.getSecurity()) +
               ", fruit=" + boolToInt(users.getFruit()) +
               ", admin=" + boolToInt(users.getAdmin()) +
               ", rmt_admin=" + boolToInt(users.getRmtAdmin());
    }


    private Users rsToObject(ResultSet rs){
        Users temp = new Users();

        try {
            temp.setEmail(rs.getString("email"));
            temp.setUsername(rs.getString("username"));
            temp.setName(rs.getString("name"));
            temp.setSurname(rs.getString("surname"));

            temp.setAdmin(rs.getInt("admin"));
            temp.setGoodsIn(rs.getInt("goods_in"));
            temp.setRmtGoodsIn(rs.getInt("rmt_goods_in"));
            temp.setSecurity(rs.getInt("security"));
            temp.setFruit(rs.getInt("fruit"));
            temp.setRmtAdmin(rs.getInt("rmt_admin"));
        }
        catch (SQLException throwables) {
            System.out.println("Check\nError at: UsersDao.rsToObject()");
        }


        return temp;
    }

    private int boolToInt(boolean bool){

        return (bool ? 1 : 0);
    }

}
