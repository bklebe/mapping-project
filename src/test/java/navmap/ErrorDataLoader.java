package navmap;

import java.nio.file.Path;
import java.util.stream.Stream;

public class ErrorDataLoader implements DataLoader {
  @Override
  public Stream<String> loadFrom(Path source) {
    throw new Error("This should not have been called.");
  }
}
