//package org.fasttrackit.dev.todolist.db;
//import org.fasttrackit.dev.todolist.ToDoBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 *
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 *
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DbOperations {


    public static void main(String[] a) {
        try {
//
            DbOperations.addFollowers(1,2);
            DbOperations.readTweetFeed();
            DbOperations.readUserTweets();
//            int f;
//            f=DBOperations.login("adrian","123");
//            System.out.println(f);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

    }


    public static void addFollowers(int fku, int fkr ) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("insert into relatii (fku,fkr) values (?,?)");
        pSt.setInt(1,fku);
        pSt.setInt(2,fkr);
        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    private static void readTweetFeed() throws ClassNotFoundException, SQLException {
        // 1. load driver
      //  List<FeedTweets> tweets = new ArrayList<FeedTweets>();
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT * from tweet where PKU in (select fkr from relatii where fku=2) order by data asc ");
        // 6. iterate the result set and print the values


        while (rs.next()) {

            System.out.print(rs.getInt("K"));
            System.out.print("---");
            System.out.print(rs.getInt("PKU"));
            System.out.print("---");
            System.out.print(rs.getString("Valoare"));
            System.out.print("---");
            System.out.println(rs.getString("data"));

        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

    }

    private static void readUserTweets() throws ClassNotFoundException, SQLException {
        // 1. load driver
        List<FeedTweets> tweets = new ArrayList<FeedTweets>();
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("select * from Users, tweet where tweet.pku = Users.k and Users.k=2");
        // 6. iterate the result set and print the values


        while (rs.next()) {

            System.out.print(rs.getInt("K"));
            System.out.print("---");
            System.out.print(rs.getInt("PKU"));
            System.out.print("---");
            System.out.print(rs.getString("Valoare"));
            System.out.print("---");
            System.out.println(rs.getString("data"));

        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

    }

}

 class FeedTweets{

    int k;
    int PKU;
    String valoare;
    String data;

     public String getUsername() {
         return username;
     }

     public void setUsername(String username) {
         this.username = username;
     }

     String username;

     public int getK() {
         return k;
     }

     public void setK(int k) {
         this.k = k;
     }

     public int getPKU() {
         return PKU;
     }

     public void setPKU(int PKU) {
         this.PKU = PKU;
     }

     public String getValoare() {
         return valoare;
     }

     public void setValoare(String valoare) {
         this.valoare = valoare;
     }

     public String getData() {
         return data;
     }

     public void setData(String data) {
         this.data = data;
     }
 }
