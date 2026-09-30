package com.company;

import com.company.BackUp.Backup;
import com.company.BackUp.BackupType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBaseManager {

    private static DataBaseManager instance = null;
    private final InventoryBackupper inventoryBackupper;
    private Connection connection;


    private DataBaseManager(InventoryBackupper inventoryBackupper) {
        this.inventoryBackupper = inventoryBackupper;
    }

    public void connect() throws SQLException {

        if(!inventoryBackupper.getDataFolder().exists()){
            inventoryBackupper.getDataFolder().mkdir();
        }

        File dataBase = new File(
                inventoryBackupper.getDataFolder(),
                "backups.db"
        );

        connection = DriverManager.getConnection(
                "jdbc:sqlite:" + dataBase.getAbsolutePath()
        );


    }

    public void close() throws SQLException{

        if(connection != null && !connection.isClosed()){
            connection.close();
        }

    }

    public static DataBaseManager init(InventoryBackupper inventoryBackupper){
        if(instance == null)instance = new DataBaseManager(inventoryBackupper);
        return instance;
    }


    public void createTables() throws SQLException {

        String sql = """
                        CREATE TABLE IF NOT EXISTS backups (
                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                             uuid TEXT NOT NULL,
                             type TEXT NOT NULL,
                             timeCreation INTEGER NOT NULL,
                             inventory BLOB NOT NULL
                         )
                       """;

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);

            statement.executeUpdate("""
                CREATE INDEX IF NOT EXISTS idx_backups_uuid
                ON backups(uuid)
                """);

            statement.executeUpdate("""
                CREATE INDEX IF NOT EXISTS idx_backups_uuid_type_time
                ON backups(uuid, type, timeCreation)
                """);

        }

    }

    public void saveBackup(Backup backup,int maxBackups) throws SQLException,IOException {

        String sql = """
        INSERT INTO backups
        (uuid, type,timeCreation, inventory)
        VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, backup.getUuid().toString());
            statement.setString(2, backup.getBackupType().name());
            statement.setLong(3, backup.getTime());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ItemStack[] inventory = backup.getContents();

            try(BukkitObjectOutputStream dataOutput =
                        new BukkitObjectOutputStream(outputStream)){

                dataOutput.writeObject(inventory);
            }

            byte[] inventoryData = outputStream.toByteArray();

            statement.setBytes(4,inventoryData);

            statement.executeUpdate();

            if (maxBackups > 0) {
                String deleteSql = """
        DELETE FROM backups
        WHERE uuid = ?
          AND type = ?
          AND id NOT IN (
              SELECT id
              FROM backups
              WHERE uuid = ?
                AND type = ?
              ORDER BY timeCreation DESC, id DESC
              LIMIT ?
          )
        """;

                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {

                    deleteStatement.setString(1, backup.getUuid().toString());
                    deleteStatement.setString(2, backup.getBackupType().name());
                    deleteStatement.setString(3, backup.getUuid().toString());
                    deleteStatement.setString(4, backup.getBackupType().name());
                    deleteStatement.setInt(5, maxBackups);

                    deleteStatement.executeUpdate();
                }
            }


        }
    }

    public int getNBackups(UUID uuid) throws SQLException {

        String sql = """
        SELECT COUNT(*)
        FROM backups
        WHERE uuid = ?
        """;

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,uuid.toString());

            try(ResultSet result = statement.executeQuery()){

                if(result.next()){
                    return result.getInt(1);
                }

            }

        }

        return 0;

    }

    public int getNBackups(UUID uuid, BackupType backupType) throws SQLException {

        String sql = """
        SELECT COUNT(*)
        FROM backups
        WHERE uuid = ?
          AND type = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid.toString());
            statement.setString(2, backupType.name());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt(1);
                }
            }
        }

        return 0;
    }


    public List<Backup> getBackups(UUID uuid) throws SQLException, IOException {

        String sql = """
             SELECT id,uuid,type,timeCreation,inventory
             FROM backups
             WHERE uuid = ?
             ORDER BY timeCreation DESC
        """;

        List<Backup> backups = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,uuid.toString());

            try(ResultSet result = statement.executeQuery()){

                while(result.next()){

                    BackupType backupType = BackupType.valueOf(result.getString("type"));
                    Long timeCreation = result.getLong("timeCreation");
                    byte[] inventory = result.getBytes("inventory");

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(inventory);

                    ItemStack[] contents;

                    try (BukkitObjectInputStream dataInput =
                                 new BukkitObjectInputStream(inputStream)) {

                        contents = (ItemStack[]) dataInput.readObject();

                    } catch (ClassNotFoundException e) {
                        throw new IOException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Backup backup = new Backup(
                            uuid,
                            backupType,
                            contents,
                            timeCreation
                    );

                    backups.add(backup);
                }

            }

        }

        return backups;

    }

    public boolean hasABackup(UUID uuid) throws SQLException{

            String sql = """
        SELECT EXISTS(
            SELECT 1
            FROM backups
            WHERE uuid = ?
        )
        """;

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, uuid.toString());

                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        return result.getBoolean(1);
                    }
                }
            }

        return false;
    }

    public void delLastBackup(UUID uuid, BackupType backupType) throws SQLException {

        String sql = """
        DELETE FROM backups
        WHERE id = (
            SELECT id
            FROM backups
            WHERE uuid = ?
            AND type = ?
            ORDER BY timeCreation ASC, id ASC
            LIMIT 1
        )
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid.toString());
            statement.setString(2, backupType.name());

            statement.executeUpdate();
        }
    }

    public static DataBaseManager getInstance(){
        return instance;
    }





}
