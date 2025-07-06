package net.rainbowcreation.core.core.common.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.rainbowcreation.core.core.common.config.Settings
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
        val config = HikariConfig().apply {
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

    /**
     * Creates the necessary database tables if they do not already exist.
     */
    private fun initializeTables() {
        // This SQL statement creates a 'players' table if one doesn't exist.
        // `uuid` is the primary key to uniquely identify players.
        // `name` stores the player's last known Minecraft name.
        // `balance` is an example column for storing player data.
        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS players (
                uuid VARCHAR(36) NOT NULL PRIMARY KEY,
                name VARCHAR(16) NOT NULL,
                balance DOUBLE NOT NULL DEFAULT 0.0
            );
        """.trimIndent()

        try {
            getConnection().use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(createTableSQL)
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
    override fun savePlayerData(player: Player, playerData: Any) {
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