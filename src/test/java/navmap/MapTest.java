package navmap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    private final PrintStream stdOut;

    MapTest() {
        this.stdOut = System.out;
    }

    @Test
    void main() {
        var testStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testStdOut));

        Map.main(new String[0]);

        assertEquals("Usage: Graph data.txt [-show] [-directions startIntersection endIntersection] [-meridianmap]%n", testStdOut.toString());
    }

    @AfterEach
    void tearDown() {
        System.setOut(this.stdOut);
    }
}
