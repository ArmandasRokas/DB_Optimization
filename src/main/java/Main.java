import data.IUserDAO;
import data.IUserDTO;
import data.PrintUser;
import data.UserDAOImpl;

public class Main {
    static IUserDAO userDAO = new UserDAOImpl();

    public static void main(String[] args) throws IUserDAO.DALException {
        long start = System.currentTimeMillis();
     /*   IUserDTO user = userDAO.getUser(1);


        for (int i = 0; i < 20; i++) {
            System.out.println("Hentede bruger: " + user.getUserId());
            userDAO.getUser(1);
        }*/

        PrintUser printUser1 = new PrintUser(userDAO,1);
        (new Thread(printUser1)).start();
        PrintUser printUser2 = new PrintUser(userDAO,2);
        (new Thread(printUser2)).start();


        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (double)(end-start)/1000 + "seconds");


    }
}
