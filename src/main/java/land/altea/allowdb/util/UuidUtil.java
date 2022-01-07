package land.altea.allowdb.util;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

public final class UuidUtil {
    private static final @NotNull Pattern DASHLESS_UUID_RE = Pattern.compile("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)");

    private UuidUtil() {

    }

    public static @NotNull UUID parseLeniently(@NotNull String raw) {
        return UUID.fromString(DASHLESS_UUID_RE.matcher(raw).replaceFirst("$1-$2-$3-$4-$5"));
    }
}
