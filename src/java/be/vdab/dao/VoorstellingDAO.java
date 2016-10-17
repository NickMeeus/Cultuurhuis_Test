package be.vdab.dao;

import be.vdab.entities.Voorstelling;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoorstellingDAO extends AbstractDAO {

    private final static Logger LOGGER = Logger.getLogger(VoorstellingDAO.class.getName());
    private static final String SELECT_ALL = "select id, titel, uitvoerders, datum, genreid, prijs, vrijeplaatsen from voorstellingen";
    private static final String UPDATE_VRIJEPLAATSEN = "update voorstellingen set vrijeplaatsen=? where id=?";

    public List<Voorstelling> findAllByGenre(long genreId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + " where genreid=?")) {
            preparedStatement.setLong(1, genreId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Voorstelling> voorstellingen = new ArrayList<>();
                while (resultSet.next()) {
                    voorstellingen.add(resultSetRijNaarVoorstelling(resultSet));
                }
                return voorstellingen;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    public Voorstelling findByVoorstellingId(long voorstellingId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + " where id=?")) {
            preparedStatement.setLong(1, voorstellingId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetRijNaarVoorstelling(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
        return null;
    }

    public List<Voorstelling> findByVoorstellingenIds(Iterable<Long> ids) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + " where id=?")) {
            List<Voorstelling> voorstellingen = new ArrayList<>();
            for (Long id : ids) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        voorstellingen.add(resultSetRijNaarVoorstelling(resultSet));
                    }
                }
            }
            return voorstellingen;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    public void UpdateVrijePlaatsen(long id, int vrijeplaatsen) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VRIJEPLAATSEN)) {
            preparedStatement.setInt(1, vrijeplaatsen);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    private Voorstelling resultSetRijNaarVoorstelling(ResultSet resultSet) throws SQLException {
        return new Voorstelling(resultSet.getLong("id"),
                resultSet.getString("titel"),
                resultSet.getString("uitvoerders"),
                resultSet.getTimestamp("datum"),
                resultSet.getLong("genreid"),
                resultSet.getBigDecimal("prijs"),
                resultSet.getInt("vrijeplaatsen"));
    }
}
