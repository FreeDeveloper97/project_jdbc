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
//drop table director;
//create table director
//	(directorID		int not null,
//	directorName	varchar(20) not null,
//	dateOfBirth		varchar(10) not null,
//	dateOfDeath		varchar(10),
//	primary key(directorID));
//
//insert into director values (1, 'Tim Burton', '1958.8.25', null);
//insert into director values (2, 'David Fincher', '1962.8.28', null);
//insert into director values (3, 'Christopher Nolan', '1970.7.30', null);
//
//select *
//from director;
//
//
//
//
//
//drop table actor;
//create table actor
//	(actorID		int not null,
//	actorName		varchar(20) not null,
//	dateOfBirth		varchar(10) not null,
//	dateOfDeath		varchar(10),
//	gender			varchar(6) not null,
//	primary key(actorID));
//	
//insert into actor values (1, 'Johnny Depp', '1963.6.9', null, 'Male');
//insert into actor values (2, 'Winona Ryder', '1971.10.29', null, 'Female');
//insert into actor values (3, 'Mia Wasikowska', '1989.10.14', null, 'Female');
//insert into actor values (4, 'Christian Bale', '1974.1.30', null, 'Male');
//insert into actor values (5, 'Heath Ledger', '1979.4.4', '2008.1.22', 'Male');
//insert into actor values (6, 'Jesse Eisenberg', '1983.10.5', null, 'Male');
//insert into actor values (7, 'Justin Timberlake', '1981.1.31', null, 'Male');
//insert into actor values (8, 'Fionn Whitehead', '1997.7.18', null, 'Male');
//insert into actor values (9, 'Tom Hardy', '1977.9.15', null, 'Male');
//
//select *
//from actor;
//
//
//
//
//
//drop table customer;
//create table customer
//	(customerID		int not null,
//	customerName	varchar(20) not null,
//	dateOfBirth		varchar(20) not null,
//	gender			varchar(10) not null,
//	primary key(customerID));
//
//insert into customer values (1, 'Ethan', '1997.11.14', 'Male');
//insert into customer values (2, 'John', '1978.01.23', 'Male');
//insert into customer values (3, 'Hayden', '1980.05.04', 'Female');
//insert into customer values (4, 'Jill', '1981.04.17', 'Female');
//insert into customer values (5, 'Bell', '1990.05.14', 'Female');
//
//select *
//from customer;
//
//
//
//
//
//drop table movie;
//create table movie
//	(movieID		int not null,
//	movieName		varchar(30) not null,
//	releaseYear		varchar(4) not null,
//	releaseMonth	varchar(2) not null,
//	releaseDate		varchar(2) not null,
//	publisherName	varchar(30) not null,
//	avgRate			float default 0,
//	primary key(movieID));
//	
//select *
//from movie;
//	
//drop table award;
//create table award
//	(awardID		int not null,
//	awardName		varchar(20) not null,
//	primary key(awardID));
//	
//select *
//from award;
//	
//drop table genre;
//create table genre
//	(genreName		varchar(20) not null,
//	primary key(genreName));
//
//select *
//from genre;
//
//-- drop table movieGenre;
//-- create table movieGenre
//-- 	(movieID		int not null,
//-- 	genreName		varchar(20) not null,
//-- 	primary key(movieID, genreName),
//-- 	foreign key(movieID) references movie (movieID) on delete cascade
//-- 	foreign key(genreName) references genre (genreName) on delete cascade);
//
//-- select *
//-- from movieGenre;