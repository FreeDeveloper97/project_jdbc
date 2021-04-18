import java.sql.*;
import java.util.ArrayList;

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
        // drop table
        ArrayList<String> dropTables = getDropTables();
        try {
        	for(int i=0; i<dropTables.size(); i++) {
            	st.executeUpdate(dropTables.get(i));
            }
            System.out.println("drop table complete");
        } catch (SQLException e) {
        	System.out.println("dtop table error : " + e);
        }
        
        //1. create table
        ArrayList<String> createTables = getCreateTables();
        try {
        	for(int i=0; i<createTables.size(); i++) {
            	st.executeUpdate(createTables.get(i));
            }
            System.out.println("create table complete");
        } catch (SQLException e) {
        	System.out.println("create table error : " + e);
        }
//        //2. insert data
//        st.executeUpdate("insert into department values ('Biology', 'Watson', '90000')");
//        st.executeUpdate("insert into department values ('Comp. Sci.', 'Taylor', '100000')");
//        
//        //3. delete
//        st.executeUpdate("delete from department where dept_name = 'Biology'");
//        
//        //4. selection
//        ResultSet rs = st.executeQuery("select * from department");
//        String dept_name;
//        String building;
//        Float budget;
//        while(rs.next()) {
//        	dept_name = rs.getString(1);
//        	building = rs.getString(2);
//        	budget = rs.getFloat(3);
//        	System.out.println(dept_name+", "+building+", "+budget);
//        }
        
        
        /////////////////////////////////////////////////////

        connection.close();
    }
    
    static public ArrayList<String> getCreateTables() {
    	ArrayList<String> tables = new ArrayList<String>();
    	//1. director
    	tables.add("create table director\n"
    			+ "	(directorID		int not null,\n"
    			+ "	directorName	varchar(20) not null,\n"
    			+ "	dateOfBirth		varchar(10) not null,\n"
    			+ "	dateOfDeath		varchar(10),\n"
    			+ "	primary key(directorID))");
    	//2. actor
    	tables.add("create table actor\n"
    			+ "	(actorID		int not null,\n"
    			+ "	actorName		varchar(20) not null,\n"
    			+ "	dateOfBirth		varchar(10) not null,\n"
    			+ "	dateOfDeath		varchar(10),\n"
    			+ "	gender			varchar(6) not null,\n"
    			+ "	primary key(actorID))");
    	//3. customer
    	tables.add("create table customer\n"
    			+ "	(customerID		int not null,\n"
    			+ "	customerName	varchar(20) not null,\n"
    			+ "	dateOfBirth		varchar(20) not null,\n"
    			+ "	gender			varchar(10) not null,\n"
    			+ "	primary key(customerID))");
    	//4. movie
    	tables.add("create table movie\n"
    			+ "	(movieID		int not null,\n"
    			+ "	movieName		varchar(30) not null,\n"
    			+ "	releaseYear		varchar(4) not null,\n"
    			+ "	releaseMonth	varchar(2) not null,\n"
    			+ "	releaseDate		varchar(2) not null,\n"
    			+ "	publisherName	varchar(30) not null,\n"
    			+ "	avgRate			numeric(2,1),\n"
    			+ "	primary key(movieID))");
    	//5. award
    	tables.add("create table award\n"
    			+ "	(awardID		int not null,\n"
    			+ "	awardName		varchar(30) not null,\n"
    			+ "	primary key(awardID))");
    	//6. genre
    	tables.add("create table genre\n"
    			+ "	(genreName		varchar(20) not null,\n"
    			+ "	primary key(genreName))");
    	
    	//7. movieGenre
    	tables.add("create table movieGenre\n"
    			+ "	(movieID		int,\n"
    			+ "	genreName		varchar(20),\n"
    			+ "	primary key(movieID, genreName),\n"
    			+ "	foreign key(movieID) references movie (movieID) on delete set null,\n"
    			+ "	foreign key(genreName) references genre (genreName) on delete set null)");
    	//8. movieObtain
    	tables.add("create table movieObtain\n"
    			+ "	(movieID		int,\n"
    			+ "	awardID			int,\n"
    			+ "	year			varchar(4),\n"
    			+ "	primary key(movieID, awardID),\n"
    			+ "	foreign key(movieID) references movie (movieID) on delete set null,\n"
    			+ "	foreign key(awardID) references award (awardID) on delete set null)");
    	//9. actorObtain
    	tables.add("create table actorObtain\n"
    			+ "	(actorID		int,\n"
    			+ "	awardID			int,\n"
    			+ "	year			varchar(4),\n"
    			+ "	primary key(actorID, awardID),\n"
    			+ "	foreign key(actorID) references actor (actorID) on delete set null,\n"
    			+ "	foreign key(awardID) references award (awardID) on delete set null)");
    	//10. directorObtain
    	tables.add("create table directorObtain\n"
    			+ "	(directorID		int,\n"
    			+ "	awardID			int,\n"
    			+ "	year			varchar(4),\n"
    			+ "	primary key(directorID, awardID),\n"
    			+ "	foreign key(directorID) references director (directorID) on delete set null,\n"
    			+ "	foreign key(awardID) references award (awardID) on delete set null)");
    	//11. casting
    	tables.add("create table casting\n"
    			+ "	(movieID		int,\n"
    			+ "	actorID			int,\n"
    			+ "	role			varchar(20),\n"
    			+ "	primary key(movieID, actorID),\n"
    			+ "	foreign key(movieID) references movie (movieID) on delete set null,\n"
    			+ "	foreign key(actorID) references actor (actorID) on delete set null)");
    	//12. make
    	tables.add("create table make\n"
    			+ "	(movieID		int,\n"
    			+ "	directorID		int,\n"
    			+ "	primary key(movieID, directorID),\n"
    			+ "	foreign key(movieID) references movie (movieID) on delete set null,\n"
    			+ "	foreign key(directorID) references director (directorID) on delete set null)");
    	//13. customerRate
    	tables.add("create table customerRate\n"
    			+ "	(customerID		int,\n"
    			+ "	movieID			int,\n"
    			+ "	rate			int,\n"
    			+ "	primary key(customerID, movieID),\n"
    			+ "	foreign key(customerID) references customer (customerID) on delete set null,\n"
    			+ "	foreign key(movieID) references movie (movieID) on delete set null)");
    	
    	return tables;
    }
    
    static public ArrayList<String> getDropTables() {
    	ArrayList<String> tables = new ArrayList<String>();
    	//13. customerRate
    	tables.add("drop table customerRate");
    	//12. make
    	tables.add("drop table make");
    	//11. casting
    	tables.add("drop table casting");
    	//10. directorObtain
    	tables.add("drop table directorObtain");
    	//9. actorObtain
    	tables.add("drop table actorObtain");
    	//8. movieObtain
    	tables.add("drop table movieObtain");
    	//7. movieGenre
    	tables.add("drop table movieGenre");
    	//6. genre
    	tables.add("drop table genre");
    	//5. award
    	tables.add("drop table award");
    	//4. movie
    	tables.add("drop table movie");
    	//3. customer
    	tables.add("drop table customer");
    	//2. actor
    	tables.add("drop table actor");
    	//1. director
    	tables.add("drop table director");

    	return tables;
    }
}


//쿼리문 저장
//create table director
//(directorID		int not null,
//directorName	varchar(20) not null,
//dateOfBirth		varchar(10) not null,
//dateOfDeath		varchar(10),
//primary key(directorID));
//
//insert into director values (1, 'Tim Burton', '1958.8.25', null);
//insert into director values (2, 'David Fincher', '1962.8.28', null);
//insert into director values (3, 'Christopher Nolan', '1970.7.30', null);
//
//
//create table actor
//(actorID		int not null,
//actorName		varchar(20) not null,
//dateOfBirth		varchar(10) not null,
//dateOfDeath		varchar(10),
//gender			varchar(6) not null,
//primary key(actorID));
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
//
//create table customer
//(customerID		int not null,
//customerName	varchar(20) not null,
//dateOfBirth		varchar(20) not null,
//gender			varchar(10) not null,
//primary key(customerID));
//
//insert into customer values (1, 'Ethan', '1997.11.14', 'Male');
//insert into customer values (2, 'John', '1978.01.23', 'Male');
//insert into customer values (3, 'Hayden', '1980.05.04', 'Female');
//insert into customer values (4, 'Jill', '1981.04.17', 'Female');
//insert into customer values (5, 'Bell', '1990.05.14', 'Female');
//
//
//
//
//
//
//
//create table movie
//(movieID		int not null,
//movieName		varchar(30) not null,
//releaseYear		varchar(4) not null,
//releaseMonth	varchar(2) not null,
//releaseDate		varchar(2) not null,
//publisherName	varchar(30) not null,
//avgRate			numeric(2,1),
//primary key(movieID));
//
//
//create table award
//(awardID		int not null,
//awardName		varchar(30) not null,
//primary key(awardID));
//
//
//create table genre
//(genreName		varchar(20) not null,
//primary key(genreName));
//
//
//create table movieGenre
//(movieID		int,
//genreName		varchar(20),
//primary key(movieID, genreName),
//foreign key(movieID) references movie (movieID) on delete set null,
//foreign key(genreName) references genre (genreName) on delete set null);
//
//
//create table movieObtain
//(movieID		int,
//awardID			int,
//year			varchar(4),
//primary key(movieID, awardID),
//foreign key(movieID) references movie (movieID) on delete set null,
//foreign key(awardID) references award (awardID) on delete set null);
//
//
//create table actorObtain
//(actorID		int,
//awardID			int,
//year			varchar(4),
//primary key(actorID, awardID),
//foreign key(actorID) references actor (actorID) on delete set null,
//foreign key(awardID) references award (awardID) on delete set null);
//
//
//create table directorObtain
//(directorID		int,
//awardID			int,
//year			varchar(4),
//primary key(directorID, awardID),
//foreign key(directorID) references director (directorID) on delete set null,
//foreign key(awardID) references award (awardID) on delete set null);
//
//
//create table cast
//(movieID		int,
//actorID			int,
//role			varchar(20),
//primary key(movieID, actorID),
//foreign key(movieID) references movie (movieID) on delete set null,
//foreign key(actorID) references actor (actorID) on delete set null);
//
//
//create table make
//(movieID		int,
//directorID		int,
//primary key(movieID, directorID),
//foreign key(movieID) references movie (movieID) on delete set null,
//foreign key(directorID) references director (directorID) on delete set null);
//
//
//create table customerRate
//(customerID		int,
//movieID			int,
//rate			int,
//primary key(customerID, movieID),
//foreign key(customerID) references customer (customerID) on delete set null,
//foreign key(movieID) references movie (movieID) on delete set null);
//
//
//
//
//
//
//insert into award values((select count(*) from award)+1, 'Best suportting actor');
//select * from award;
//
//insert into actorObtain values(
//(select actorID
//from actor
//where actorName = 'Winona Ryder'),
//(select awardID
//from award
//where awardName = 'Best suportting actor'),
//'1994');
//
//select * from actorObtain;