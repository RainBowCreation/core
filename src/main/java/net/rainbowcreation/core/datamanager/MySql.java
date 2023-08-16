package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.core;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class MySql {
    private Connection connection;
    FileConfiguration config = core.getInstance().getConfig();

    public MySql() {
        try {
            connection = DriverManager.getConnection("jdbc://"+ config.getString("mySQL.url")+":"+ String.valueOf(config.getInt("mySQL.port"))+"/"+config.getString("mySQL.database") ,config.getString("mySQL.username"), config.getString("mySQL.password"));
        } catch (Exception ignored) {};
        set("server", "ping", "pong", "id", 1);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void set(String table, String key, Object value, String wkey, Object wvalue) {
        String query = "UPDATE " + table + " SET " + key + " = ? WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, value);
            stmt.setObject(2, wvalue);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String table, String key, String wkey, Object wvalue) {
        String query = "SELECT * FROM " + table + " WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, wvalue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getString(key);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean ping() {
        if (get("server", "ping", "id", 1).equals("pong"))
            return true;
        return false;
    }
}
