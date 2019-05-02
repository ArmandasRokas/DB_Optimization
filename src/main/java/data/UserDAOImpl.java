package data;


import java.sql.*;
import java.util.*;

public class UserDAOImpl implements IUserDAO {

//    private LinkedList<ConnectionWithIndex> connections = new LinkedList<>();

    private LinkedList<Connection> connections = new LinkedList<>();
//    private Connection connection = createConnection();


    private Map<Integer, IUserDTO> cache = new HashMap<>();
    //private Connection connection = createConnection(); // linked list with 20 connections.


    public UserDAOImpl(){
//        for(int i = 0; i < 25; i++){
//            connections.add(new ConnectionWithIndex(i, createConnection()));
//        }
                for(int i = 0; i < 10; i++){
            connections.add(createConnection());
        }
    }

    private Connection createConnection(){
        Connection conn= null;

        try{
            conn = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/db02327?"
                    + "user=db02327&password=db02327");
        } catch (SQLException se){
            se.printStackTrace();
        }
        return  conn;
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {

        /******* Caching ***********/
     /*   IUserDTO user = cache.get(userId);
        if (user != null){
            return user;
        }*/

   //  ConnectionWithIndex connectionWithIndex = connections.removeFirst();
   //  Connection connection = connectionWithIndex.getConnection();
    //    System.out.println("Connection index: " + connectionWithIndex.getIndex() + " for " + userId);

//        Connection connection;
//        if(!connections.isEmpty()){
//            connection = connections.removeFirst();
//        } else{
//            connection = createConnection();
//        }

//        while(connections.isEmpty()){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
        Connection connection = connections.removeFirst();




        try {
//            if(connection == null){
//                connection = createConnection();
//            }

            if (!connection.isValid(100)){
                connection = createConnection();
            }
            Statement statement = connection.createStatement(); // threading
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE userid = " + userId);
            if (resultSet.next()) {
                return makeUserFromResultSet(resultSet);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException(e.getMessage());
        } finally {
            if(connection != null){
              //  connections.addLast(connectionWithIndex);
                connections.addLast(connection);
            }
        }

    }

    private IUserDTO makeUserFromResultSet(ResultSet resultSet) throws SQLException {
        IUserDTO user = new UserDTO();
        user.setUserName(resultSet.getString("userName"));
        user.setIni(resultSet.getString("ini"));
        user.setUserId(resultSet.getInt("userId"));
        List<String> roles = Arrays.asList(resultSet.getString("roles").split(";"));
        user.setRoles(roles);

        putUserToCache(user);

        return user;
    }

    private void putUserToCache(IUserDTO user) {
        cache.put(user.getUserId(), user);
    }

    @Override
    public IUserDTO getUserByIni(String initials) throws DALException {
        try (Connection connection = createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE ini LIKE '" + initials + "'");
            UserDTO user = new UserDTO();
            if(resultSet.next()) {
                user.setUserName(resultSet.getString("userName"));
                user.setIni(resultSet.getString("ini"));
                List<String> roles = Arrays.asList(resultSet.getString("Roles").split(";"));
                user.setRoles(roles);
            }
            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public List<IUserDTO> getUserList() throws DALException {
        try (Connection connection = createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            List<IUserDTO> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(makeUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        // Implement this if you like - Should insert a user into the db using data from UserDTO object.
    }

    @Override
    public void updateUser(IUserDTO user) throws DALException {
        //Implement this if you like - Should update a user in the db using data from UserDTO object.
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        // Implement this if you like - Should insert a user into the db using data from UserDTO object.
        //Note db02327 user can't delete users from db02327 db
    }
}
