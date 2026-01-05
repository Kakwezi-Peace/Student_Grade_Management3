package config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void testDefaultConfigValues() {
        // ✅ Check constants exist
        assertNotNull(Config.DEFAULT_FILE_PATH);
        assertTrue(Config.MAX_STUDENTS > 0);
        assertEquals("./data/students.csv", Config.DEFAULT_FILE_PATH);
    }

    @Test
    public void testConfigOverrides() {
        // ✅ Override file path
        Config.setFilePath("custom_path.csv");
        assertEquals("custom_path.csv", Config.getFilePath());

        // Reset to default after test
        Config.setFilePath(Config.DEFAULT_FILE_PATH);
    }
}
