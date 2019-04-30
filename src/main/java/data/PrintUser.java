package data;


public class PrintUser implements Runnable {

    private IUserDAO userDAO;

    public PrintUser(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            IUserDTO user = null;
            try {
                user = userDAO.getUser(1);
                System.out.println("Hentede bruger: " + user.getUserId());
            } catch (IUserDAO.DALException e) {
                e.printStackTrace();
            }

        }
    }
}
