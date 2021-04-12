import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        /// if you have a error in this part, check jdbc driver(.jar file)

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/project_movie", "postgres", "cse3207");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        /// if you have a error in this part, check DB information (db_name, user name, password)

        if (connection != null) {
            System.out.println(connection);
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }


        /////////////////////////////////////////////////////
        ////////// write your code on this ////////////
        
        Statement st = connection.createStatement();
        //1. create table
        st.executeUpdate("create table department\n"
        		+ "	(dept_name		varchar(20), \n"
        		+ "	 building		varchar(15), \n"
        		+ "	 budget		        numeric(12,2) check (budget > 0),\n"
        		+ "	 primary key (dept_name)\n"
        		+ "	)");
        
        //2. insert data
        st.executeUpdate("insert into department values ('Biology', 'Watson', '90000')");
        st.executeUpdate("insert into department values ('Comp. Sci.', 'Taylor', '100000')");
        
        //3. delete
        st.executeUpdate("delete from department where dept_name = 'Biology'");
        
        //4. selection
        ResultSet rs = st.executeQuery("select * from department");
        String dept_name;
        String building;
        Float budget;
        while(rs.next()) {
        	dept_name = rs.getString(1);
        	building = rs.getString(2);
        	budget = rs.getFloat(3);
        	System.out.println(dept_name+", "+building+", "+budget);
        }
        
        
        /////////////////////////////////////////////////////

        connection.close();
    }
}


//쿼리문 저장
//create table director
//(directorID		int not null,
//directorName	varchar(20) not null,
//dateOfBirth		varchar(10) not null,
//dateOfDeath		varchar(10),
//primary key(directorName));
//
//select *
//from director;
//
//create table actor
//(actorID		int not null,
//actorName		varchar(20) not null,
//dateOfBirth		varchar(10) not null,
//dateOfDeath		varchar(10),
//gender			varchar(6) not null,
//primary key(actorName));
//
//select *
//from actor;
//
//create table customer
//(customerID		int not null,
//customerName	varchar(20) not null,
//dateOfBirth		varchar(20) not null,
//gender			varchar(10) not null,
//primary key(customerName));
//
//select *
//from customer;