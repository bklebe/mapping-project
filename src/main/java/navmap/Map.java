package navmap;

final class Map {
  private Map() {}

  public static void main(final String[] args) {
    var launcher = new Launcher(args, System.out, new FileLoader());
    launcher.launch();
  }
}
