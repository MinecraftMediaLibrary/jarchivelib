/**
 * Copyright 2013 Thomas Rausch
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pulsebeat02.jarchivelib;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Utility class for I/O operations. */
public final class IOUtils {

  /** Default buffer size used for {@code copy} operations. */
  private static final int DEFAULT_BUFFER_SIZE = 8024;

  private IOUtils() {}

  /**
   * Copies the content of an InputStream into a destination File.
   *
   * @param source the InputStream to copy
   * @param destination the target File
   * @throws IOException if an error occurs
   */
  public static void copy(final InputStream source, final File destination) throws IOException {
    OutputStream output = null;

    try {
      output = new FileOutputStream(destination);
      copy(source, output);
    } finally {
      closeQuietly(output);
    }
  }

  /**
   * Copies the content of a InputStream into an OutputStream. Uses a default buffer size of 8024
   * bytes.
   *
   * @param input the InputStream to copy
   * @param output the target Stream
   * @return the amount of bytes written
   * @throws IOException if an error occurs
   */
  public static long copy(final InputStream input, final OutputStream output) throws IOException {
    return copy(input, output, DEFAULT_BUFFER_SIZE);
  }

  /**
   * Copies the entire content of the given InputStream into the given OutputStream.
   *
   * @param input the InputStream to copy
   * @param output the target Stream
   * @param buffersize the buffer size to use
   * @return the amount of bytes written
   * @throws IOException if an error occurs
   */
  public static long copy(final InputStream input, final OutputStream output, final int buffersize)
      throws IOException {
    final byte[] buffer = new byte[buffersize];
    int n;
    long count = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * Computes the path name of a file node relative to a given root node. <br>
   * If the root is {@code /home/cdlflex/custom-ahy} and the given node is {@code
   * /home/cdlflex/custom-ahy/assembly/pom.xml}, the returned path name will be {@code
   * assembly/pom.xml}.
   *
   * @param root the parent node
   * @param node the file node to compute the relative path for
   * @return the path of {@code node} relative to {@code root}
   * @throws IOException when an I/O error occurs during resolving the canonical path of the files
   */
  public static String relativePath(final File root, final File node) throws IOException {
    final String rootPath = root.getCanonicalPath();
    final String nodePath = node.getCanonicalPath();

    return nodePath.substring(rootPath.length() + 1);
  }

  /**
   * Makes sure that the given {@link File} is either a writable directory, or that it does not
   * exist and a directory can be created at its path. <br>
   * Will throw an exception if the given {@link File} is actually an existing file, or the
   * directory is not writable
   *
   * @param destination the directory which to ensure its existence for
   * @throws IOException if an I/O error occurs e.g. when attempting to create the destination
   *     directory
   * @throws IllegalArgumentException if the destination is an existing file, or the directory is
   *     not writable
   */
  public static void requireDirectory(final File destination)
      throws IOException, IllegalArgumentException {
    if (destination.isFile()) {
      throw new IllegalArgumentException(
          destination + " exists and is a file, directory or path expected.");
    } else if (!destination.exists()) {
      destination.mkdirs();
    }
    if (!destination.canWrite()) {
      throw new IllegalArgumentException("Can not write to destination " + destination);
    }
  }

  /**
   * Null-safe method that calls {@link java.io.Closeable#close()} and chokes the IOException.
   *
   * @param closeable the object to close
   */
  public static void closeQuietly(final Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (final IOException e) {
      }
    }
  }

  /**
   * Given a source File, return its direct descendants if the File is a directory. Otherwise return
   * the File itself.
   *
   * @param source File or folder to be examined
   * @return a File[] array containing the files inside this folder, or a size-1 array containing
   *     the file itself.
   */
  public static File[] filesContainedIn(final File source) {
    if (source.isDirectory()) {
      return source.listFiles();
    } else {
      return new File[] {source};
    }
  }
}
