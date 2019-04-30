package data;


public class PrintUser implements Runnable {

    private IUserDAO userDAO;
    private int userId;

    public PrintUser(IUserDAO userDAO, int userId){
        this.userDAO = userDAO;
        this.userId = userId;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            IUserDTO user = null;
            try {
                user = userDAO.getUser(userId);
                System.out.println("Hentede bruger: " + user.getUserId());
            } catch (IUserDAO.DALException e) {
                e.printStackTrace();
            }

        }
    }
}
