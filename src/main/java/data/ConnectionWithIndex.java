package data;

import java.sql.Connection;

public class ConnectionWithIndex {
    private int index;
    private Connection connection;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ConnectionWithIndex(int index, Connection connection) {
        this.index = index;
        this.connection = connection;
    }
}
