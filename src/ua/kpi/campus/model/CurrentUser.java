package ua.kpi.campus.model;

/**
 * Session state
 *
 * @author Artur Dzidzoiev
 * @version 12/23/13
 */
public final class CurrentUser {
    private final int id;
    private final String sessionId;
    private final String login;
    private final String password;
    private final boolean isEmployee;

    public CurrentUser(int id, String sessionId, String login, String password, int isEmployee) {

        this.id = id;
        this.sessionId = sessionId;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee == 1;
    }

    public CurrentUser(int id, String sessionId, String login, String password, boolean isEmployee) {

        this.id = id;
        this.sessionId = sessionId;
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public int getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentUser user = (CurrentUser) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}