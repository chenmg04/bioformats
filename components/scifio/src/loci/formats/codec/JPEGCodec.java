/*
 * #%L
 * OME SCIFIO package for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2005 - 2012 Board of Regents of the University of
 * Wisconsin-Madison, Glencoe Software, Inc., and University of Dundee.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package loci.formats.codec;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import javax.imageio.ImageIO;

import loci.common.RandomAccessInputStream;
import loci.formats.FormatException;
import loci.formats.gui.AWTImageTools;

/**
 * This class implements JPEG compression and decompression.
 *
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://trac.openmicroscopy.org.uk/ome/browser/bioformats.git/components/bio-formats/src/loci/formats/codec/JPEGCodec.java">Trac</a>,
 * <a href="http://git.openmicroscopy.org/?p=bioformats.git;a=blob;f=components/bio-formats/src/loci/formats/codec/JPEGCodec.java;hb=HEAD">Gitweb</a></dd></dl>
 */
public class JPEGCodec extends BaseCodec {

  /**
   * The CodecOptions parameter should have the following fields set:
   *  {@link CodecOptions#width width}
   *  {@link CodecOptions#height height}
   *  {@link CodecOptions#channels channels}
   *  {@link CodecOptions#bitsPerSample bitsPerSample}
   *  {@link CodecOptions#interleaved interleaved}
   *  {@link CodecOptions#littleEndian littleEndian}
   *  {@link CodecOptions#signed signed}
   *
   * @see Codec#compress(byte[], CodecOptions)
   */
  public byte[] compress(byte[] data, CodecOptions options)
    throws FormatException
  {
    if (data == null || data.length == 0) return data;
    if (options == null) options = CodecOptions.getDefaultOptions();

    if (options.bitsPerSample > 8) {
      throw new FormatException("> 8 bit data cannot be compressed with JPEG.");
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BufferedImage img = AWTImageTools.makeImage(data, options.width,
      options.height, options.channels, options.interleaved,
      options.bitsPerSample / 8, false, options.littleEndian, options.signed);

    try {
      ImageIO.write(img, "jpeg", out);
    }
    catch (IOException e) {
      throw new FormatException("Could not write JPEG data", e);
    }
    return out.toByteArray();
  }

  /**
   * The CodecOptions parameter should have the following fields set:
   *  {@link CodecOptions#interleaved interleaved}
   *  {@link CodecOptions#littleEndian littleEndian}
   *
   * @see Codec#decompress(RandomAccessInputStream, CodecOptions)
   */
  public byte[] decompress(RandomAccessInputStream in, CodecOptions options)
    throws FormatException, IOException
  {
    BufferedImage b;
    long fp = in.getFilePointer();
    try {
      try {
        while (in.read() != (byte) 0xff || in.read() != (byte) 0xd8);
        in.seek(in.getFilePointer() - 2);
      }
      catch (EOFException e) {
        in.seek(fp);
      }

      b = ImageIO.read(new BufferedInputStream(new DataInputStream(in), 8192));
    }
    catch (IOException exc) {
      // probably a lossless JPEG; delegate to LosslessJPEGCodec
      in.seek(fp);
      return new LosslessJPEGCodec().decompress(in, options);
    }

    if (options == null) options = CodecOptions.getDefaultOptions();

    byte[][] buf = AWTImageTools.getPixelBytes(b, options.littleEndian);
    byte[] rtn = new byte[buf.length * buf[0].length];
    if (buf.length == 1) rtn = buf[0];
    else {
      if (options.interleaved) {
        int next = 0;
        for (int i=0; i<buf[0].length; i++) {
          for (int j=0; j<buf.length; j++) {
            rtn[next++] = buf[j][i];
          }
        }
      }
      else {
        for (int i=0; i<buf.length; i++) {
          System.arraycopy(buf[i], 0, rtn, i*buf[0].length, buf[i].length);
        }
      }
    }
    return rtn;
  }
}
