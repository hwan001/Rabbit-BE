package team.avgmax.rabbit.global.util;

import com.github.f4b6a3.ulid.UlidCreator;

public class UlidGenerator {

    public static String generate() {
        return UlidCreator.getUlid().toString();
    }

    public static String generateMonotonic() {
        return UlidCreator.getMonotonicUlid().toString();
    }
}
