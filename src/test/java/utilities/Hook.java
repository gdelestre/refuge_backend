package utilities;

import io.cucumber.java.Before;
import java.sql.Connection;
import java.sql.Statement;

public class Hook {

    @Before
    public void deleteDataFromDB(){
        try {
            Connection con = ConnectionDB.give_connection();
            System.out.println("Connection with DB");
            Statement statement = con.createStatement();

            String sql = "DELETE FROM veterinary_care";
            statement.executeUpdate(sql);
            System.out.println("Delete all data from veterinary_care...");

            sql = "DELETE FROM animal";
            statement.executeUpdate(sql);
            System.out.println("Delete all data from animal...");

            sql = "DELETE FROM veterinary";
            statement.executeUpdate(sql);
            System.out.println("Delete all data from veterinary...");

            sql = "DELETE FROM host_family";
            statement.executeUpdate(sql);
            System.out.println("Delete all data from host family...");

            sql = "DELETE FROM adoptive_family";
            statement.executeUpdate(sql);
            System.out.println("Delete all data from adoptive family...");

            con.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
