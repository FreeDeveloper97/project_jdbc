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
//        System.out.println("PostgreSQL JDBC Driver Registered!");
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
//            System.out.println(connection);
//            System.out.println("You made it, take control your database now!");
            System.out.println("연결 성공");
        } else {
            System.out.println("Failed to make connection!");
        }


        /////////////////////////////////////////////////////
        ////////// write your code on this ////////////
        
        Statement st = connection.createStatement();
//        //drop table
        dropTable(st);
        
        //1-1 create table
        createTable(st);
        //1-2 insert data
        insertFirstData(st, connection);
        
        //2-1 insert award, actorObtain
        awardActor(st, "Winona Ryder", "Best supporting actor", "1994");
        //2-2 insert award, actorObtain
        awardActor(st, "Tom Hardy", "Best supporting actor", "2018");
        //2-3 insert award, actorObtain
        awardActor(st, "Heath Ledger", "Best villain actor", "2009");
        //2-4 insert award, actorObtain
        awardActor(st, "Johnny Depp", "Best main actor", "2011");
        //2-5 insert award, movieObtain
        awardMovie(st, "Edward Scissorhands", "Best fantasy movie", "1991");
        //2-6 insert award, movieObtain
        awardMovie(st, "Alice In Wonderland", "Best fantasy movie", "2011");
        //2-7 insert award, movieObtain
        awardMovie(st, "The Dark Knight", "Best picture", "2009");
        //2-8 insert award, directorObtain
        awardDirector(st, "Christopher Nolan", "Best director", "2018");
        //3-1 insert customer, update movie
        customerRate(st, "Ethan", 5, "Dunkirk");
        //3-1 insert customer, update movie
        customerRateWithDirector(st, "Bell", 5, "Tim Burton");
        
        
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
    			+ "	movieName		varchar(20) not null,\n"
    			+ "	releaseYear		varchar(4) not null,\n"
    			+ "	releaseMonth	varchar(2) not null,\n"
    			+ "	releaseDate		varchar(2) not null,\n"
    			+ "	publisherName	varchar(25) not null,\n"
    			+ "	avgRate			numeric(2,1),\n"
    			+ "	primary key(movieID))");
    	//5. award
    	tables.add("create table award\n"
    			+ "	(awardID		int not null,\n"
    			+ "	awardName		varchar(23) not null,\n"
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
    	//4. movie
    	datas.add(new String[]{"1", "Edward Scissorhands", "1991", "06", "29", "20th Century Fox Presents", "0.0"});
    	datas.add(new String[]{"2", "Alice In Wonderland", "2010", "03", "04", "Korea Sony Pictures", "0.0"});
    	datas.add(new String[]{"3", "The Social Network", "2010", "11", "18", "Korea Sony Pictures", "0.0"});
    	datas.add(new String[]{"4", "The Dark Knight", "2008", "08", "06", "Warner Brothers Korea", "0.0"});
    	datas.add(new String[]{"5", "Dunkirk", "2017", "07", "13", "Warner Brothers Korea", "0.0"});
    	
    	return datas;
    }
    
    static public ArrayList<String> getInsertDatas3() {
    	ArrayList<String> datas = new ArrayList<String>();
    	//5. genre
    	datas.add("insert into genre values ('Fantasy')");
    	datas.add("insert into genre values ('Romance')");
    	datas.add("insert into genre values ('Adventure')");
    	datas.add("insert into genre values ('Family')");
    	datas.add("insert into genre values ('Drama')");
    	datas.add("insert into genre values ('Action')");
    	datas.add("insert into genre values ('Mystery')");
    	datas.add("insert into genre values ('Thriller')");
    	datas.add("insert into genre values ('War')");
    	//6. make
    	datas.add("insert into make values (1, 1)");
    	datas.add("insert into make values (2, 1)");
    	datas.add("insert into make values (3, 2)");
    	datas.add("insert into make values (4, 3)");
    	datas.add("insert into make values (5, 3)");
    	
    	return datas;
    }
    
    static public ArrayList<String[]> getInsertDatas4() {
    	ArrayList<String[]> datas = new ArrayList<String[]>();
    	//7. movieGenre
    	datas.add(new String[]{"Fantasy", "Romance"});
    	datas.add(new String[]{"Fantasy", "Adventure", "Family"});
    	datas.add(new String[]{"Drama"});
    	datas.add(new String[]{"Action", "Drama", "Mystery", "Thriller"});
    	datas.add(new String[]{"Action", "Drama", "Thriller", "War"});
    	
    	return datas;
    }
    
    static public ArrayList<String> getInsertDatas5() {
    	ArrayList<String> datas = new ArrayList<String>();
    	//8. casting
    	ArrayList<String[]> actor_role = new ArrayList<String[]>();
    	actor_role.add(new String[]{"Edward Scissorhands", "Johnny Depp", "Main actor"});
    	actor_role.add(new String[]{"Edward Scissorhands", "Winona Ryder", "Main actor"});
    	actor_role.add(new String[]{"Alice In Wonderland", "Johnny Depp", "Main actor"});
    	actor_role.add(new String[]{"Alice In Wonderland", "Mia Wasikowska", "Main actor"});
    	actor_role.add(new String[]{"The Social Network", "Jesse Eisenberg", "Main actor"});
    	actor_role.add(new String[]{"The Social Network", "Justin Timberlake", "Supporting Actor"});
    	actor_role.add(new String[]{"The Dark Knight", "Christian Bale", "Main actor"});
    	actor_role.add(new String[]{"The Dark Knight", "Heath Ledger", "Main actor"});
    	actor_role.add(new String[]{"Dunkirk", "Fionn Whitehead", "Main actor"});
    	actor_role.add(new String[]{"Dunkirk", "Tom Hardy", "Supporting Actor"});
    	
    	for(int i=0; i<actor_role.size(); i++) {
    		String castingQuery = "insert into casting values((select movieID from movie where movieName = '" + actor_role.get(i)[0] + "'),\n"
        			+ "			(select actorID from actor where actorName = '" + actor_role.get(i)[1] + "'), '" + actor_role.get(i)[2] + "')";
    		datas.add(castingQuery);
    	}
    	
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
            System.out.println("Table created!");
        } catch (SQLException e) {
        	System.out.println("Table create error : " + e);
        }
    }
    
    static void insertFirstData(Statement st, Connection connection) {
    	//director, actor, customer
    	ArrayList<String> insertDatas = getInsertDatas1();
        try {
        	for(int i=0; i<insertDatas.size(); i++) {
            	st.executeUpdate(insertDatas.get(i));
            }
//            System.out.println("insert1 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert datas error : " + e);
        }
        //movie
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
//        	System.out.println("insert2 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert2 datas error : " + e);
        }
        //genre, make
    	ArrayList<String> insertDatas3 = getInsertDatas3();
        try {
        	for(int i=0; i<insertDatas3.size(); i++) {
            	st.executeUpdate(insertDatas3.get(i));
            }
//            System.out.println("insert3 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert3 datas error : " + e);
        }
        //movieGenre
        ArrayList<String[]> insertDatas4 = getInsertDatas4();
        try {
        	PreparedStatement pStmt = connection.prepareStatement("insert into movieGenre values(?,?)");
        	for(int i=0; i<insertDatas4.size(); i++) {
        		for(int j=0; j<insertDatas4.get(i).length; j++) {
        			pStmt.setInt(1, i+1);
        			pStmt.setString(2, insertDatas4.get(i)[j]);
        			pStmt.executeUpdate();
        		}
        	}
//        	System.out.println("insert4 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert4 datas error : " + e);
        }
        //casting
        ArrayList<String> insertDatas5 = getInsertDatas5();
        try {
        	for(int i=0; i<insertDatas5.size(); i++) {
            	st.executeUpdate(insertDatas5.get(i));
            }
//            System.out.println("insert5 datas complete");
        } catch (SQLException e) {
        	System.out.println("insert datas error : " + e);
        }
        System.out.println("Initial data inserted!\n");
    }
    
    static void awardActor(Statement st, String actorName, String awardName, String year) {
    	System.out.println("Statement : "+actorName+" won the \""+awardName+"\" award in "+year);
    	//1. award 입력
    	int awardID = addAward(st, awardName);
    	//2. actorObtain 입력
    	addActorObtain(st, awardID, actorName, year);
    }
    
    static void awardMovie(Statement st, String movieName, String awardName, String year) {
    	System.out.println("Statement : "+movieName+" won the \""+awardName+"\" award in "+year);
    	//1. award 입력
    	int awardID = addAward(st, awardName);
    	//2. movieObtain 입력
    	addMovieObtain(st, awardID, movieName, year);
    }
    
    static void awardDirector(Statement st, String directorName, String awardName, String year) {
    	System.out.println("Statement : "+directorName+" won the \""+awardName+"\" award in "+year);
    	//1. award 입력
    	int awardID = addAward(st, awardName);
    	//2. directorObtain 입력
    	addDirectorObtain(st, awardID, directorName, year);
    }
    
    static void customerRate(Statement st, String customerName, int rate, String movieName) {
    	System.out.println("Statement : " + customerName + " rates " + rate + " to \"" + movieName + "\"");
    	//1. movieID 값 구하기
    	int movieID = getMovieID(st, movieName);
    	//2. customerRate 추가하기
    	addCustomerRate(st, customerName, movieID, rate);
    	//3. movie update 하기
    	updateMovieRate(st, movieID);
    }
    
    static void customerRateWithDirector(Statement st, String customerName, int rate, String directorName) {
    	System.out.println("Statement : " + customerName + " rates " + rate + " to the movies whose director is \"" + directorName + "\"");
    	//1. directorId 값 구하기
    	int directorID = getDirectorID(st, directorName);
    	//2. make 에서 movieId 값들 구하기
    	ArrayList<Integer> movieIds = getMovieIDFromMake(st, directorID);
    	//3. customerRate 추가, movie update 하기
    	for(int i=0; i<movieIds.size(); i++) {
    		addCustomerRate(st, customerName, movieIds.get(i), rate);
    		updateMovieRate(st, movieIds.get(i));
    	}
    }
    
    static int addAward(Statement st, String awardName) {
    	int awardID = 0;
    	try {
    		//동일데이터가 있는지 여부 확인
    		ResultSet rs = st.executeQuery("select count(awardID) from award where awardName = '" + awardName + "'");
    		int count = 0;
    		while(rs.next()) { count = rs.getInt(1); }
    		//동일데이터가 없는 경우 -> ID값 생성, 추가
			if(count == 0) {
				rs = st.executeQuery("select distinct count(*) from award");
				while(rs.next()) { awardID = rs.getInt(1)+1; }
	    		String query = "INSERT into award values (" + awardID + ", '" + awardName + "')";
	    		//수행
	    		System.out.println("Translated SQL: " + query);
	    		st.executeUpdate(query);
	    		System.out.println("updated Tables");
			} 
			//동일데이터가 있는경우 -> 해당 ID값 저장
			else {
				rs = st.executeQuery("select awardID from award where awardName = '" + awardName + "'");
				while(rs.next()) { awardID = rs.getInt(1); }
			}
			//table 출력
    		printAwardTable(st);
        } catch (SQLException e) {
        	System.out.println("INSERT award error : " + e);
        }
    	return awardID;
    }
    
    static void addActorObtain(Statement st, int awardID, String actorName, String year) {
    	try {
    		int actorID = 0;
    		ResultSet rs = st.executeQuery("SELECT actorID from actor where actorName = '" + actorName + "'");
    		while(rs.next()) { actorID = rs.getInt(1); }
            String query = "INSERT into actorObtain values (" + actorID + ", " + awardID + ", '" + year + "')";
            //수행
            System.out.println("Translated SQL: " + query);
            st.executeUpdate(query);
            System.out.println("updated Tables");
          //table 출력
            printActorObtainTable(st);
    	} catch (SQLException e) {
        	System.out.println("INSERT actorObtain error : " + e);
        }
    }
    
    static void addMovieObtain(Statement st, int awardID, String movieName, String year) {
    	try {
    		int movieID = 0;
    		ResultSet rs = st.executeQuery("SELECT movieID from movie where movieName = '" + movieName + "'");
    		while(rs.next()) { movieID = rs.getInt(1); }
            String query = "INSERT into movieObtain values (" + movieID + ", " + awardID + ", '" + year + "')";
            //수행
            System.out.println("Translated SQL: " + query);
            st.executeUpdate(query);
            System.out.println("updated Tables");
            //table 출력
            printMovieObtainTable(st);
    	} catch (SQLException e) {
        	System.out.println("INSERT movieObtain error : " + e);
        }
    }
    
    static void addDirectorObtain(Statement st, int awardID, String directorName, String year) {
    	try {
    		int directorID = 0;
    		ResultSet rs = st.executeQuery("SELECT directorID from director where directorName = '" + directorName + "'");
    		while(rs.next()) { directorID = rs.getInt(1); }
            String query = "INSERT into directorObtain values (" + directorID + ", " + awardID + ", '" + year + "')";
            //수행
            System.out.println("Translated SQL: " + query);
            st.executeUpdate(query);
            System.out.println("updated Tables");
            //table 출력
            printDirectorObtainTable(st);
    	} catch (SQLException e) {
        	System.out.println("INSERT directorObtain error : " + e);
        }
    }
    
    static int addCustomerRate(Statement st, String customerName, int movieID, int rate) {
    	try {
    		//1. consumer : consumerID 가져오기
    		int customerID = 0;
    		ResultSet rs = st.executeQuery("SELECT customerID from customer where customerName = '" + customerName + "'");
    		while(rs.next()) { customerID = rs.getInt(1); }
    		
    		//3. consumerRate 입력하기
    		String query = "INSERT into customerRate values (" + customerID + ", " + movieID + ", " + rate + ")";
    		//수행
    		System.out.println("Translated SQL: " + query);
    		st.executeUpdate(query);
    		System.out.println("updated Tables");
    		//table 출력
    		printCustomerRateTable(st);
    	} catch (SQLException e) {
        	System.out.println("INSERT customerRate error : " + e);
        }
    	return movieID;
    }
    
    static void updateMovieRate(Statement st, int movieID) {
    	try {
    		//1. movieID 값의 avg(rate)값 구하기
    		float avgRate = 0;
    		ResultSet rs = st.executeQuery("SELECT avg(rate) from customerRate where movieID = " + movieID);
    		while(rs.next()) { avgRate = rs.getFloat(1); }
    		//2. movieID 값의 avgRate update 하기
    		String query = "UPDATE movie set avgRate = " + avgRate + " where movieID = " + movieID;
    		//수행
    		System.out.println("Translated SQL: " + query);
    		st.executeUpdate(query);
    		System.out.println("updated Tables");
    		//table 출력
    		printMovieTable(st);
    	} catch (SQLException e) {
        	System.out.println("UPDATE movie error : " + e);
        }
    }
    
    static int getMovieID(Statement st, String movieName) {
    	int movieID = 0;
    	try {
    		ResultSet rs = st.executeQuery("SELECT movieID from movie where movieName = '" + movieName + "'");
    		while(rs.next()) { movieID = rs.getInt(1); }
    	} catch (SQLException e) {
        	System.out.println("SELECT movie error : " + e);
        }
    	return movieID;
    }
    
    static int getDirectorID(Statement st, String directorName) {
    	int directorID = 0;
    	try {
    		ResultSet rs = st.executeQuery("SELECT directorID from director where directorName = '" + directorName + "'");
    		while(rs.next()) { directorID = rs.getInt(1); }
    	} catch (SQLException e) {
        	System.out.println("SELECT director error : " + e);
        }
    	return directorID;
    }
    
    static ArrayList<Integer> getMovieIDFromMake(Statement st, int directorID) {
    	ArrayList<Integer> movieIds = new ArrayList<Integer>();
    	try {
    		ResultSet rs = st.executeQuery("SELECT movieID from make where directorID = " + directorID);
    		while(rs.next()) { movieIds.add(rs.getInt(1)); }
    	} catch (SQLException e) {
        	System.out.println("SELECT make error : " + e);
        }
    	return movieIds;
    }
    
    static void printAwardTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from award");
    		int awardID;
    		String awardName;
    		System.out.println("award table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|awardID   |awardName");
    		while(rs.next()) {
    			awardID = rs.getInt(1);
    			awardName = rs.getString(2);
    			System.out.printf("|"+"%-10d"+"|"+"%s\n", awardID, awardName);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT award error : " + e);
        }
    }
    
    static void printActorObtainTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from actorObtain");
    		int actorID;
    		int awardID;
    		String year;
    		System.out.println("actorObtain table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|actorID   |awardID   |year");
    		while(rs.next()) {
    			actorID = rs.getInt(1);
    			awardID = rs.getInt(2);
    			year = rs.getString(3);
    			System.out.printf("|"+"%-10d"+"|"+"%-10d"+"|"+"%s\n", actorID, awardID, year);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT actorObtain error : " + e);
        }
    }
    
    static void printMovieObtainTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from movieObtain");
    		int movieID;
    		int awardID;
    		String year;
    		System.out.println("movieObtain table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|movieID   |awardID   |year");
    		while(rs.next()) {
    			movieID = rs.getInt(1);
    			awardID = rs.getInt(2);
    			year = rs.getString(3);
    			System.out.printf("|"+"%-10d"+"|"+"%-10d"+"|"+"%s\n", movieID, awardID, year);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT movieObtain error : " + e);
        }
    }
    
    static void printDirectorObtainTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from directorObtain");
    		int directorID;
    		int awardID;
    		String year;
    		System.out.println("directorObtain table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|directorID  |awardID  |year");
    		while(rs.next()) {
    			directorID = rs.getInt(1);
    			awardID = rs.getInt(2);
    			year = rs.getString(3);
    			System.out.printf("|"+"%-12d"+"|"+"%-9d"+"|"+"%s\n", directorID, awardID, year);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT directorObtain error : " + e);
        }
    }
    
    static void printCustomerRateTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from customerRate");
    		int customerID;
    		int movieID;
    		int rate;
    		System.out.println("customerRate table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|customerID  |movieID  |rate");
    		while(rs.next()) {
    			customerID = rs.getInt(1);
    			movieID = rs.getInt(2);
    			rate = rs.getInt(3);
    			System.out.printf("|"+"%-12d"+"|"+"%-9d"+"|"+"%s\n", customerID, movieID, rate);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT customerRate error : " + e);
        }
    }
    
    static void printMovieTable(Statement st) {
    	try {
    		ResultSet rs = st.executeQuery("SELECT * from movie");
    		int movieID;
    		String movieName;
    		String year;
    		String month;
    		String date;
    		String publisherName;
    		Float avgRate;
    		System.out.println("movie table");
    		System.out.println("+-----------------------------------");
    		System.out.println("|movieID |movieName           |releaseYear |releaseMonth |releaseDate |publisherName            |avgRate");
    		while(rs.next()) {
    			movieID = rs.getInt(1);
    			movieName = rs.getString(2);
    			year = rs.getString(3);
    			month = rs.getString(4);
    			date = rs.getString(5);
    			publisherName = rs.getString(6);
    			avgRate = rs.getFloat(7);
    			System.out.printf("|"+"%-8d"+"|"+"%-20s"+"|"+"%-12s"+"|"+"%-13s"+"|"+"%-12s"+"|"+"%-25s"+"|"+"%f\n", 
    					movieID, movieName, year, month, date, publisherName, avgRate);
    		}
    		System.out.println();
    	} catch (SQLException e) {
        	System.out.println("SELECT movie error : " + e);
        }
    }
}



