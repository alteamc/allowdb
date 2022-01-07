package land.altea.allowdb.storage.util;


import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.UUID;

public final class UuidPersister extends BaseDataType {
    private static final @NotNull UuidPersister I = new UuidPersister();

    private UuidPersister() {
        super(SqlType.BYTE_ARRAY, new Class[]{UUID.class});
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return UUID.fromString(defaultStr);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getBytes(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        return UUID.nameUUIDFromBytes((byte[]) sqlArg);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        ByteBuffer b = ByteBuffer.allocate(16);
        b.putLong(((UUID) javaObject).getMostSignificantBits());
        b.putLong(((UUID) javaObject).getLeastSignificantBits());
        return b.array();
    }

    public static @NotNull UuidPersister getSingleton() {
        return I;
    }
}
