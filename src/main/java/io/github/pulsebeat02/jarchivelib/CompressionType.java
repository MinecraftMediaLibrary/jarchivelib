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

import org.apache.commons.compress.compressors.CompressorStreamFactory;

/** Denotes a compression algorithm such as gzip or bzip2 */
public enum CompressionType {

  /** Constant used to identify the BZIP2 compression algorithm. */
  BZIP2(CompressorStreamFactory.BZIP2, ".bz2"),
  /** Constant used to identify the GZIP compression algorithm. */
  GZIP(CompressorStreamFactory.GZIP, ".gz"),
  /** Constant used to identify the XZ compression algorithm. */
  XZ(CompressorStreamFactory.XZ, ".xz"),
  /** Constant used to identify the PACK200 compression algorithm. */
  PACK200(CompressorStreamFactory.PACK200, ".pack");

  /** The name by which the compression algorithm is identified */
  private final String name;

  /** The default file extension the compression type is mapped to */
  private final String defaultFileExtension;

  private CompressionType(final String name, final String defaultFileExtension) {
    this.name = name;
    this.defaultFileExtension = defaultFileExtension;
  }

  /**
   * Checks if the given compression type name is valid and known format.
   *
   * @param compression the compression algorithm name
   * @return true true if the given compression type is known to the factory, false otherwise
   */
  public static boolean isValidCompressionType(final String compression) {
    for (final CompressionType type : values()) {
      if (compression.equalsIgnoreCase(type.getName())) {
        return true;
      }
    }

    return false;
  }

  /**
   * Attempts to return the {@link CompressionType} instance from a possible given string
   * representation. Ignores case.
   *
   * @param compression string representation of the compression type. E.g. "GZIP".
   * @return the compression type enum
   * @throws IllegalArgumentException if the given compression type is unknown.
   */
  public static CompressionType fromString(final String compression) {
    for (final CompressionType type : values()) {
      if (compression.equalsIgnoreCase(type.getName())) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unknown compression type " + compression);
  }

  /**
   * Returns the name by which the compression algorithm is identified.
   *
   * @return the compression algorithm name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the default file extension for this compression type. E.g. ".gz" for gzip.
   *
   * @return the default file extension preceded by a dot
   */
  public String getDefaultFileExtension() {
    return this.defaultFileExtension;
  }
}
