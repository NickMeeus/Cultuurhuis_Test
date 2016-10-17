package be.vdab.dao;

import be.vdab.entities.Genre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAO extends AbstractDAO {
    private final static Logger LOGGER = Logger.getLogger(VoorstellingDAO.class.getName());
    private static final String SELECT_ALL = "select id,naam from genres ";

    public Iterable<Genre> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL + "order by naam asc");) {
            List<Genre> genrelijst = new ArrayList<>();
            while (resultSet.next()) {
                genrelijst.add(resultSetNaarGenre(resultSet));
            }
            return genrelijst;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    public Genre findByID(Long ID) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + "where id=?");) {
            preparedStatement.setLong(1, ID);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return resultSetNaarGenre(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
        return null;
    }

    private Genre resultSetNaarGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
    }
}
