package repositories;

import mapper.RowMapper;
import models.Auth;
import models.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthRepository {

    private Connection connection;
    private final String SQL_FIND_BY_COOKIE_VALUE = "SELECT *, auth.id as auth_id, userdata.id as user_id FROM auth INNER JOIN userdata ON auth.user_id=userdata.id WHERE auth.cookie_value=?";
    private final String SQL_INSERT_AUTH = "INSERT INTO auth (user_id, cookie_value) VALUES (?, ?)";

    public AuthRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Auth findByCookieValue(String cookieValue) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_COOKIE_VALUE);
            preparedStatement.setString(1, cookieValue);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            Auth auth = authRowMapper.rowMap(resultSet);
            return auth;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Auth> findAll() {
        return null;
    }

    @Override
    public Optional<Auth> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Auth save(Auth auth) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_AUTH, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, auth.getUser().getId());
            preparedStatement.setString(2, auth.getCookieValue());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auth;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }

    private final RowMapper<Auth> authRowMapper = (resultSet) -> {
        if (resultSet.next()) {
            Auth auth = new Auth();
            auth.setId(resultSet.getLong("auth_id"));
            auth.setCookieValue(resultSet.getString("cookie_value"));

            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPasswordHash(resultSet.getString("password_hash"));

            auth.setUser(user);
            return auth;
        } else {
            return null;
        }
    };
}
