

package app.coronawarn.server.services.distribution.objectstore.publish;

import app.coronawarn.server.services.distribution.assembly.structure.file.FileOnDiskWithChecksum;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A set of files, which are subject for publishing to S3.
 */
public class PublishFileSet {

  /**
   * how deep the folder structure will be scanned for files.
   */
  private static final int FILE_WALK_MAX_DEPTH = 20;

  /**
   * the root folder from which to read all files.
   */
  private final Path root;

  /**
   * the list of identified files in the root folder.
   */
  private final List<LocalFile> files;

  /**
   * Creates a new PublishFileSet, based on the given root folder. This root folder will be used to scan recursively for
   * available files.
   *
   * @param root the root folder, e.g. ./out/
   * @throws IOException in case there were problems reading the files
   */
  public PublishFileSet(Path root) throws IOException {
    this.root = root;
    this.files = getFilesOnPath(root);
  }

  private List<LocalFile> getFilesOnPath(Path path) throws IOException {
    if (path == null || !path.toFile().isDirectory()) {
      throw new UnsupportedOperationException("Supplied path is not a folder: " + path);
    }

    try (Stream<Path> stream = Files.walk(path, FILE_WALK_MAX_DEPTH)) {
      return stream
          .filter(Files::isRegularFile)
          .filter(this::ignoreChecksumFiles)
          .map(this::constructPublishFile)
          .collect(Collectors.toList());
    }
  }

  private LocalFile constructPublishFile(Path path) {
    if (path.endsWith("index")) {
      return new LocalIndexFile(path, root);
    }

    return new LocalGenericFile(path, root);
  }

  private boolean ignoreChecksumFiles(Path path) {
    return !FileOnDiskWithChecksum.isChecksumFile(path);
  }

  public List<LocalFile> getFiles() {
    return files;
  }
}
