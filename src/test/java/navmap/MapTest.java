package navmap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class MapTest {

  @Test
  void launcherPrintsUsage() {
    var outputStream = new ByteArrayOutputStream();
    var launcher =
        new Launcher(new String[0], new PrintStream(outputStream), new ErrorDataLoader());
    launcher.launch();

    assertEquals(
        String.format(
            "Usage : Graph data.txt [-show] [-directions startIntersection endIntersection] [-meridianmap]%n"),
        outputStream.toString());
  }
}
