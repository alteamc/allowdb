package land.altea.allowdb.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import land.altea.allowdb.storage.util.UuidPersister;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@DatabaseTable(tableName = "allowed_users")
public final class AllowRecord {
    @DatabaseField(id = true, persisterClass = UuidPersister.class, columnDefinition = "binary(16)")
    private UUID uuid;

    @DatabaseField(uniqueIndex = true, canBeNull = false)
    private String username;

    public AllowRecord() {

    }

    public AllowRecord(@NotNull UUID uuid, @NotNull String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
