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
        //drop table
        dropTable(st);
        
        //1. create table
        createTable(st);
        //2. insert data
        insertFirstData(st, connection);
        
        
        
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
    
    static public ArrayList<String> getInsertDatas1() {
    	ArrayList<String> datas = new ArrayList<String>();
    	//1. director
    	datas.add("insert into director values (1, 'Tim Burton', '1958.8.25', null)");
    	datas.add("insert into director values (2, 'David Fincher', '1962.8.28', null)");
    	datas.add("insert into director values (3, 'Christopher Nolan', '1970.7.30', null)");
    	//2. actor
    	datas.add("insert into actor values (1, 'Johnny Depp', '1963.6.9', null, 'Male')");
    	datas.add("insert into actor values (2, 'Winona Ryder', '1971.10.29', null, 'Female')");
    	datas.add("insert into actor values (3, 'Mia Wasikowska', '1989.10.14', null, 'Female')");
    	datas.add("insert into actor values (4, 'Christian Bale', '1974.1.30', null, 'Male')");
    	datas.add("insert into actor values (5, 'Heath Ledger', '1979.4.4', '2008.1.22', 'Male')");
    	datas.add("insert into actor values (6, 'Jesse Eisenberg', '1983.10.5', null, 'Male')");
    	datas.add("insert into actor values (7, 'Justin Timberlake', '1981.1.31', null, 'Male')");
    	datas.add("insert into actor values (8, 'Fionn Whitehead', '1997.7.18', null, 'Male')");
    	datas.add("insert into actor values (9, 'Tom Hardy', '1977.9.15', null, 'Male')");
    	//3. customer
    	datas.add("insert into customer values (1, 'Ethan', '1997.11.14', 'Male')");
    	datas.add("insert into customer values (2, 'John', '1978.01.23', 'Male')");
    	datas.add("insert into customer values (3, 'Hayden', '1980.05.04', 'Female')");
    	datas.add("insert into customer values (4, 'Jill', '1981.04.17', 'Female')");
    	datas.add("insert into customer values (5, 'Bell', '1990.05.14', 'Female')");
    	
    	return datas;
    }
    
    static public ArrayList<String[]> getInsertDatas2() {
    	ArrayList<String[]> datas = new ArrayList<String[]>();
    	datas.add(new String[]{"1", "Edward Scissorhands", "1991", "06", "29", "20th Century Fox Presents", "0.0"});
    	datas.add(new String[]{"2", "Alice In Wonderland", "2010", "03", "04", "Korea Sony Pictures", "0.0"});
    	datas.add(new String[]{"3", "The Social Network", "2010", "11", "18", "Korea Sony Pictures", "0.0"});
    	datas.add(new String[]{"4", "The Dark Knight", "2008", "08", "06", "Warner Brothers Korea", "0.0"});
    	datas.add(new String[]{"5", "Dunkirk", "2017", "07", "13", "Warner Brothers Korea", "0.0"});
    	return datas;
    }
    
    static void dropTable(Statement st) {
    	ArrayList<String> dropTables = getDropTables();
        try {
        	for(int i=0; i<dropTables.size(); i++) {
            	st.executeUpdate(dropTables.get(i));
            }
            System.out.println("drop table complete");
        } catch (SQLException e) {
        	System.out.println("dtop table error : " + e);
        }
    }
    
    static void createTable(Statement st) {
    	ArrayList<String> createTables = getCreateTables();
        try {
        	for(int i=0; i<createTables.size(); i++) {
            	st.executeUpdate(createTables.get(i));
            }
            System.out.println("create table complete");
        } catch (SQLException e) {
        	System.out.println("create table error : " + e);
        }
    }
    
    static void insertFirstData(Statement st, Connection connection) {
    	ArrayList<String> insertDatas = getInsertDatas1();
        try {
        	for(int i=0; i<insertDatas.size(); i++) {
            	st.executeUpdate(insertDatas.get(i));
            }
            System.out.println("insert1 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert datas error : " + e);
        }
        
        ArrayList<String[]> insertDatas2 = getInsertDatas2();
        try {
        	PreparedStatement pStmt = connection.prepareStatement("insert into movie values(?,?,?,?,?,?,?)");
        	for(int i=0; i<insertDatas2.size(); i++) {
        		pStmt.setInt(1, Integer.parseInt(insertDatas2.get(i)[0]));
        		for(int j=2; j<7; j++) {
        			pStmt.setString(j, insertDatas2.get(i)[j-1]);
        		}
        		pStmt.setFloat(7, Float.parseFloat(insertDatas2.get(i)[6]));
        		pStmt.executeUpdate();
        	}
        } catch (SQLException e) {
        	System.out.println("insert2 datas error : " + e);
        }
    }
}