package fabflix.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchInsert {

    public BatchInsert(){

    }


    public static void insertBatch(ArrayList<Movie> movies) {





        PreparedStatement ps=null;


        String query =
                "INSERT INTO moviedb.movies(id,title,year,director, backdrop_path,budget,overview,poster_path,revenue) " +
                "VALUES(?,?,?,?,null,null,null,null,null);";

        int[] iNoRows=null;

        try {
            Core.getCon().setAutoCommit(false);
            ps=  Core.getCon().prepareStatement(query);


            int len = movies.size();
            for(int i=1;i<=len;i++)
            {
                ps.setString(1,movies.get(i).getId());
                ps.setString(2,movies.get(i).getTitle());
                ps.setInt(3,movies.get(i).getYear());
                ps.setString(4,movies.get(i).getDirector());
                ps.addBatch();
            }

            iNoRows=ps.executeBatch();
            Core.getCon().commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
          if(ps!=null) ps.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}


