package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.chat.Console;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySql {
    private static final Core plugin = Core.getInstance();
    private Connection connection;
    FileConfiguration config = plugin.getConfig();
    private String prefix = config.getString("mySQL.table_prefix");

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
            throw new RuntimeException(e);
        };
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean set(String table, String key, Object value, String wkey, Object wvalue) {
        String query = "UPDATE " +  prefix + table + " SET " + key + " = ? WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, value);
            stmt.setObject(2, wvalue);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String table, String key, String wkey, Object wvalue) {
        String query = "SELECT * FROM " + prefix + table + " WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, wvalue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getString(key);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean execute(String statement) {
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            return stmt.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean execute(String statement, Object param) {
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setObject(1, param);
            return stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean add(String table, String key, String value) {
        return execute("INSERT INTO " + prefix + table + "(" + key + ") VALUES (?);", value);
    }

    public boolean add(String table, List<String> keys, List<String> values) {
        if (keys.size() != values.size()) {
            Console.info("size of keys and values not match");
            return false;
        }
        boolean result = true;
        for (int i=0; i<keys.size(); i++) {
            result = result && add(table, keys.get(i), values.get(i));
        }
        return result;
    }

    public boolean createTable(String table, String colum, String type) {
        return execute("CREATE TABLE " + prefix + table + " ("+ colum + " " + type.toUpperCase() + ");");
    }

    public void setup() {
        Console.info("initializing");
        if (!ping()) {
            Console.info("create table -> " + createTable("heartbeat", "ping", "text"));
            Console.info("insert -> " + execute("INSERT INTO " + prefix + "heartbeat(ping) VALUES (?);", "pong"));
        }
        //do first time setup thing
        //case1 fresh start setup will create table and store data init
        //case2 not fresh start setup will create table and upload all data to the database
    }

    public boolean ping() {
        String query = "SELECT * FROM " + prefix + "heartbeat WHERE ping = 'pong';";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getString("ping").equals("pong");
            }
        } catch(SQLException e){
            Console.info(e.toString());
        }
        return false;
    }
}
