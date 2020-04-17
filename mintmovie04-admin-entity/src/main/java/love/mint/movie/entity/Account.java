package love.mint.movie.entity;

public class Account {
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String icon;

    private Integer admin;

    private String sign;

    public Account() {
    }

    public Account(Integer id, String username, String password, String nickname, String icon, Integer admin, String sign) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.icon = icon;
        this.admin = admin;
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "MAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", icon='" + icon + '\'' +
                ", admin=" + admin +
                ", sign='" + sign + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }
}