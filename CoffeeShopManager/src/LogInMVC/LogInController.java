package LogInMVC;
import JDBCConnector.ConnectorMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>{@code LogInController} contains all methods represent for
 * all action of the log in</p>
 */
public class LogInController {

    /**
     * <p>This method will check input data is the same with the data was stored
     * in database or not.</p>
     * <p><strong>USES: </strong>check account when log in</p>
     *
     * @param username a {@code String} object represents for the input username data which was enter by user
     * @param password a {@code String} object represents for the inout password data which was enter by user
     * @return a {@code boolean} value. if correct will return true and not will return false
     */
    public boolean isCorrect(String username, String password) {
        boolean flag = false;
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            ResultSet resultSet =
                    statement.executeQuery(String.format("SELECT * FROM account WHERE user='%s' AND password='%s'",
                            username, password.hashCode()));
            if (resultSet.next())
                flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return flag;
    }

    /**
     * <p>This method uses to create a new account and store in database</p>
     *
     * @param code     a {@code String} object represents for the code of the staff
     * @param username a {@code String} object represents for the username of the staff
     * @param password a {@code String} object represents for thr password of the staff
     * @param url      a {@code String} object represents for the url of the avatar of the staff
     * @throws Exception thrown an exception when create an invalid username
     */
    public void createNewAccount(String code,
                                 String username,
                                 String password,
                                 String url) throws Exception {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM account ");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                if (username.equals(resultSet.getString("user")))
                    throw new Exception("Invalid username");
            }
            url = url.substring(url.lastIndexOf("src")).replaceAll("\\\\", "\\\\\\\\");
            if (code.substring(0, code.indexOf("0")).equals("MANAGER"))
                statement.executeUpdate(String.format("INSERT INTO account (code, user, password, status, url) " +
                                "VALUES ('%s', '%s', '%s', %d, '%s')",
                        code, username, password.hashCode(), 0, url));
            else
                statement.executeUpdate(String.format("INSERT INTO account (code, user, password, status, url) " +
                                "VALUES ('%s', '%s', '%s', %d, '%s')",
                        code, username, password.hashCode(), 1, url));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Update url of avatar of staff whose code equals to code parameter in database
     *
     * @param code a {@code String} object represents code of staff was stored in database
     * @param url  a {@code String} object represents new url of avatar of staff was stored in database
     */
    public void updateURL(String code, String url) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE account SET url='%s' WHERE code='%s'", url, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update account of that staff in database
     *
     * @param code        original {@code String} object -> Code of staff was stored in database
     * @param newPassword original {@code String} object-> New password will replace old password in database
     */
    public void updateAccount(String code, String newPassword) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE account SET password='%s' WHERE code='%s'",
                    newPassword.hashCode(), code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method will check input data is the same with the data was stored
     * in database or not.</p>
     * <p><strong>USES: </strong>check account when create ne account</p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @param username a {@code String} object represents for the username of the account
     *
     * @param password a {@code String} object represents for the password of the account
     *
     * @return a {@code boolean} value
     */
    public boolean checkAccount(String code, String username, String password) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        String oriUsername;
        String oriPassword;
        boolean flag = false;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM account WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                oriUsername = resultSet.getString("user");
                oriPassword = resultSet.getString("password");
                if (oriUsername.equals(username) && oriPassword.equals(String.valueOf(password.hashCode()))) {
                    flag = true;
                    break;
                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return flag;
    }

    /**
     * <p>This method uses to get URL of user's avatar was stored in database</p>
     *
     * @param username a {@code String} object represents for the code of the username
     *
     * @return a {@code String} object represents for the URL of the avatar of the account
     */
    public String getURLFromDB(String username) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        String url = null;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM account WHERE user='%s'", username));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                url = resultSet.getString("url").replaceAll("\\\\", "\\\\\\\\");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return url;
    }

    /**
     * <p>This method uses to get code of the staff by passing username argument was stored in database</p>
     *
     * @param username a {@code String} object represents for the code of the username
     *
     * @return a {@code String} object represents for the code of the staff
     */
    public String getCodeFromDB(String username) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        String code = null;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM account WHERE user='%s'", username));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                code = resultSet.getString("code");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return code;
    }
}
