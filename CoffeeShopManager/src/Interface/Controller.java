package Interface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This interface contains all basic actions of the manager to manage
 */
public interface Controller {
    void add(Object objModel);

    void remove(String code);

    Vector<Vector<String>> searchKey(String key);

    Vector<Vector<String>> searchKey(double begin, double end);

    Vector<Vector<String>> searchKey(int keyword);

    Vector<Vector<String>> getAll(String order);

    Vector<Vector<String>> getModelData(ResultSet resultSet) throws SQLException;
}
