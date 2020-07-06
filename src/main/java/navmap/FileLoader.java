package navmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileLoader implements DataLoader {
    @Override
    public Stream<String> loadFrom(Path source) {
        try {
            var lines = Files.lines(source);
            return lines;
        } catch (IOException e) {
            throw new Error("Could not read from source.");
        }
    }
}
