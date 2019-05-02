import data.IUserDAO;
import data.IUserDTO;
import data.PrintUser;
import data.UserDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static IUserDAO userDAO = new UserDAOImpl();

    public static void main(String[] args) throws IUserDAO.DALException {
        long start = System.currentTimeMillis();
       IUserDTO user = userDAO.getUser(1);


//        for (int i = 0; i < 10; i++) {
//            System.out.println("Hentede bruger: " + user.getUserId());
//            userDAO.getUser(1);
//        }

        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
            PrintUser printUser1 = new PrintUser(userDAO,1);
            Thread thread = new Thread(printUser1);
            thread.start();
            threadList.add(thread);
        }
        for(Thread t : threadList) {
            // waits for this thread to die
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (double)(end-start)/1000 + "seconds");


    }
}
