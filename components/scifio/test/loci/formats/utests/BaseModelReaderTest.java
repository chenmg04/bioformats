
package loci.formats.utests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;

import loci.formats.ChannelFiller;
import loci.formats.ChannelSeparator;
import loci.formats.IFormatReader;
import loci.formats.ImageReader;
import loci.formats.MinMaxCalculator;
import loci.formats.meta.IMetadata;
import loci.formats.ome.OMEXMLMetadataImpl;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://trac.openmicroscopy.org.uk/ome/browser/bioformats.git/components/bio-formats/test/loci/formats/utests/BaseModelReaderTest.java">Trac</a>,
 * <a href="http://git.openmicroscopy.org/?p=bioformats.git;a=blob;f=components/bio-formats/test/loci/formats/utests/BaseModelReaderTest.java;hb=HEAD">Gitweb</a></dd></dl>
 *
 * @author Chris Allan <callan at blackcat dot ca>
 */
public class BaseModelReaderTest {

  private BaseModelMock mock;
  
  private File temporaryFile;

  private IFormatReader reader;

  private IMetadata metadata;

  @BeforeClass
  public void setUp() throws Exception {
    mock = new BaseModelMock();
    temporaryFile = File.createTempFile(this.getClass().getName(), ".ome");
    SPWModelReaderTest.writeMockToFile(mock, temporaryFile, true);
  }

  @AfterClass
  public void tearDown() throws Exception {
    temporaryFile.delete();
  }

  @Test
  public void testSetId() throws Exception {
    reader = new MinMaxCalculator(new ChannelSeparator(
        new ChannelFiller(new ImageReader())));
    metadata = new OMEXMLMetadataImpl();
    reader.setMetadataStore(metadata);
    reader.setId(temporaryFile.getAbsolutePath());
  }

  @Test(dependsOnMethods={"testSetId"})
  public void testSeriesCount() {
    assertEquals(1, reader.getSeriesCount());
  }

  @Test(dependsOnMethods={"testSetId"})
  public void testCanReadEveryPlane() throws Exception {
    assertTrue(SPWModelReaderTest.canReadEveryPlane(reader));
  }

}
