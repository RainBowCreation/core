package net.rainbowcreation.core.core.common.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.rainbowcreation.core.core.common.config.Settings
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.SQLException
import java.util.UUID

/**
 * A production-ready implementation of the [DataSource] interface using HikariCP
 * for efficient MySQL connection pooling.
 *
 * @param settings The loaded plugin configuration.
 */
class MySqlDataSource(private val settings: Settings) : DataSource {
    private lateinit var hikari: HikariDataSource

    /**
     * Establishes a connection pool and ensures the database schema is initialized.
     */
    override fun connect() {
        val config =
            HikariConfig().apply {
                jdbcUrl = "jdbc:mysql://${settings.dbHost}:${settings.dbPort}/${settings.dbName}?useSSL=false"
                username = settings.dbUser
                password = settings.dbPass
                addDataSourceProperty("cachePrepStmts", "true")
                addDataSourceProperty("prepStmtCacheSize", "250")
                addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
                maximumPoolSize = 10
            }
        hikari = HikariDataSource(config)

        // Initialize the database schema
        initializeTables()
    }

    override fun appendTestResult(
        pluginversion: String,
        isFolia: Boolean,
        isMultipaper: Boolean,
        commonStatus: Boolean,
        finalStatus: Boolean,
    ) {
        // This query will insert a new row or update the existing one if the UUID already exists.
        val sql =
            """
            INSERT INTO serverVersionCheck (
                serverVersion,
                isFolia,
                isMultipaper,
                pluginVersion,
                common,
                final
            ) VALUES (?, ?, ?, ?, ?, ?);
            """.trimIndent()
        try {
            getConnection().use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, Bukkit.getBukkitVersion())
                    stmt.setBoolean(2, isFolia)
                    stmt.setBoolean(3, isMultipaper)
                    stmt.setString(4, pluginversion)
                    stmt.setBoolean(5, commonStatus)
                    stmt.setBoolean(6, finalStatus)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * Creates the necessary database tables if they do not already exist.
     */
    private fun initializeTables() {
        // This SQL statement creates a 'players' table if one doesn't exist.
        // `uuid` is the primary key to uniquely identify players.
        // `name` stores the player's last known Minecraft name.
        // `balance` is an example column for storing player data.
        val createPlayersTableSQL =
            """
            CREATE TABLE IF NOT EXISTS players (
                uuid VARCHAR(36) NOT NULL PRIMARY KEY,
                name VARCHAR(16) NOT NULL,
                balance DOUBLE NOT NULL DEFAULT 0.0
            );
            """.trimIndent()

        try {
            getConnection().use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(createPlayersTableSQL)
                }
            }
        } catch (e: SQLException) {
            System.err.println("Database table initialization failed!")
            e.printStackTrace()
        }

        // This SQL statement creates a 'serverVersionCheck' table if one doesn't exist.
        // `id` is the primary key to numbers of test operations or run.
        // `timestamp` stores the timestamp when operation or run occur
        // `serverversion` stores plain text of the server version.
        // `pluginversion` stores plain text of a plugin version
        // `status` show that all test operations success or not
        val createServerVersionCheckTableSQL =
            """
            CREATE TABLE IF NOT EXISTS serverVersionCheck (
                 id INT AUTO_INCREMENT PRIMARY KEY,
                 timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                 serverVersion VARCHAR(50) NOT NULL,
                 isFolia BOOLEAN NOT NULL,
                 isMultipaper BOOLEAN NOT NULL,
                 pluginVersion VARCHAR(50) NOT NULL,
                 common BOOLEAN NOT NULL,
                 final BOOLEAN NOT NULL
             ); 
            """.trimIndent()

        try {
            getConnection().use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(createServerVersionCheckTableSQL)
                }
            }
        } catch (e: SQLException) {
            System.err.println("Database table initialization failed!")
            e.printStackTrace()
        }
    }

    /**
     * Closes the connection pool and releases all resources.
     */
    override fun disconnect() {
        if (::hikari.isInitialized && !hikari.isClosed) {
            hikari.close()
        }
    }

    /**
     * Gets a connection from the pool.
     *
     * @return A live [Connection] to the database.
     * @throws SQLException if a database access error occurs.
     */
    private fun getConnection(): Connection = hikari.connection

    /**
     * Retrieves player data from the database.
     */
    override fun getPlayerData(uuid: UUID): Any? {
        val sql = "SELECT * FROM players WHERE uuid = ? LIMIT 1;"
        try {
            getConnection().use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, uuid.toString())
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        // Map the result set to your PlayerData object here
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Saves player data to the database.
     */
    override fun savePlayerData(
        player: Player,
        playerData: Any,
    ) {
        // This query will insert a new row or update the existing one if the UUID already exists.
        val sql = "INSERT INTO players (uuid, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?;"
        try {
            getConnection().use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, player.uniqueId.toString())
                    stmt.setString(2, player.name)
                    stmt.setString(3, player.name)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
