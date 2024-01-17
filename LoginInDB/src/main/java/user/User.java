package user;

public class User {
    private  final  int ID;
    private final String NAME;
    private final String LOGIN;
    private final String PASSWORD;

    public User(int ID, String NAME, String LOGIN, String PASSWORD) {
        this.ID = ID;
        this.NAME = NAME;
        this.LOGIN = LOGIN;
        this.PASSWORD = PASSWORD;
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
