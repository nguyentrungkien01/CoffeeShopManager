package LogInMVC;

/**
 * <p>{@code LogInModel} is the model of the log in object</p>
 * <p>{@code LogInModel} contains all of attributes of the log in</p>
 */
public class LogInModel {

    /**
     * this attribute is the username of the account
     */
    private String username;

    /**
     * this attribute is the password of the account
     */
    private String password;


    /**
     * this attribute is the status of the account
     * <ul>
     *     <li>value 1 is the normal staff</li>
     *     <li>value 0 is the manager</li>
     * </ul>
     */
    private int status;

    /**
     * this attribute is the url of the avatar of the account
     */
    private String url;

    /**
     * <p>This method uses to create a new log in model</p>
     *
     * @param username a {@code String} object represents for the username of the account
     * @param password a {@code String} object represents for the password of the account
     * @param status   a {@code String} object represents for the status of the account
     */
    public LogInModel(String username, String password, int status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    /**
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUser() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param user the username
     */
    public void setUser(String user) {
        this.username = user;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
