package be.vdab.dao;

import be.vdab.entities.Klant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KlantDAO extends AbstractDAO {

    private final static Logger LOGGER = Logger.getLogger(VoorstellingDAO.class.getName());
    private static final String SELECT_ALL = "select id, voornaam, familienaam, straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord from klanten";
    private static final String SELECT_LOGIN = "select paswoord from klanten where gebruikersnaam=?";
    private static final String CREATE_SQL = "insert into klanten(voornaam, familienaam, straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public Klant findByGebruikersnaam(String gebruikersnaam) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + " where gebruikersnaam=?")) {
            preparedStatement.setString(1, gebruikersnaam);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetRijNaarKlant(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
        return null;
    }

    public boolean BestaatGebruikersnaam(String gebruikersnaam) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL + " where gebruikersnaam=?")) {
            preparedStatement.setString(1, gebruikersnaam);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    public boolean KanGebruikerInloggen(String gebruikersnaam, String paswoord) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGIN)) {
            preparedStatement.setString(1, gebruikersnaam);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && paswoord.equals(resultSet.getString("paswoord"))) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    public void create(Klant klant) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, klant.getVoornaam());
            statement.setString(2, klant.getFamilienaam());
            statement.setString(3, klant.getStraat());
            statement.setString(4, klant.getHuisnr());
            statement.setString(5, klant.getPostcode());
            statement.setString(6, klant.getGemeente());
            statement.setString(7, klant.getGebruikersnaam());
            statement.setString(8, klant.getPaswoord());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                klant.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
            throw new DAOException(ex);
        }
    }

    private Klant resultSetRijNaarKlant(ResultSet resultSet) throws SQLException {
        return new Klant(resultSet.getLong("id"),
                resultSet.getString("voornaam"),
                resultSet.getString("familienaam"),
                resultSet.getString("straat"),
                resultSet.getString("huisnr"),
                resultSet.getString("postcode"),
                resultSet.getString("gemeente"),
                resultSet.getString("gebruikersnaam"),
                resultSet.getString("paswoord"));
    }
}
