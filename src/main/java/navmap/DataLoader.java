package navmap;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface DataLoader {
    Stream<String> loadFrom(Path source);
}
