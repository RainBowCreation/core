package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.chat.Console;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class MySql {
    private static final Core plugin = Core.getInstance();
    private Connection connection;
    FileConfiguration config = plugin.getConfig();

    public MySql() {
        try {
            String url = config.getString("mySQL.url");
            if (!url.startsWith("jdbc:")) {
                url = "jdbc:mysql://" + url;
            }
            Console.info(url+":"+config.getInt("mySQL.port")+"/"+config.getString("mySQL.database")+";"+config.getString("mySQL.username")+";"+config.getString("mySQL.password"));
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url+":"+ config.getInt("mySQL.port")+"/"+config.getString("mySQL.database"), config.getString("mySQL.username"), config.getString("mySQL.password"));
        } catch (Exception e) {
            Console.info(e.toString());
        };
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
        String query = "UPDATE " + config.getString("mySQL.table_prefix")+table + " SET " + key + " = ? WHERE " + wkey + " = ?;";
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
        String query = "SELECT * FROM " + config.getString("mySQL.table_prefix") + table + " WHERE " + wkey + " = ?;";
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
        return get("heartbeat", "ping", "ping", "pong").equals("pong");
    }
}
