package by.tut.shershnev_s.repository.enums;

public enum CreateActionEnum {

    CREATE_USER_TABLE("CREATE TABLE user(\n" +
            "    id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    username      VARCHAR(60) NOT NULL,\n" +
            "    password      VARCHAR(60) NOT NULL,\n" +
            "    age           int         ,\n" +
            "    is_active     BOOLEAN     NOT NULL DEFAULT TRUE\n" +
            ");"),
    CREATE_USER_INFORMATION_TABLE("CREATE TABLE user_information(\n" +
            "    user_id   INT PRIMARY KEY NOT NULL,\n" +
            "    address   VARCHAR(60) NOT NULL,\n" +
            "    telephone VARCHAR(60) NOT NULL,\n" +
            "    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE\n" +
            ");");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
