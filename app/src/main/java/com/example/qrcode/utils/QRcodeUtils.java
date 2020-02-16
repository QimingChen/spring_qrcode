package com.example.qrcode.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class QRcodeUtils {

//  private final String ext = ".png";
//  private final String LOGO = "logo_url";
  private final String logoFilename = "Twitter_Logo_Blue.png";
  private final int WIDTH = 300;
  private final int HEIGHT = 300;

  public byte[] generate(String content) throws WriterException, IOException {
    // Create new configuration that specifies the error correction
    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //set error correction high?

    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {

      // Create a qr code with the url as content and a size of WxH px
      bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

      // Load QR image
      BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig());

      // Load logo image
      BufferedImage overly = getOverly(logoFilename);

      // Calculate the delta height and width between QR code and logo
      int deltaHeight = qrImage.getHeight() - overly.getHeight();
      int deltaWidth = qrImage.getWidth() - overly.getWidth();

      // Initialize combined image
      BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(),
          BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) combined.getGraphics();

      // Write QR code to new image at position 0/0
      g.drawImage(qrImage, 0, 0, null);
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
// Write logo into combine image at position (deltaWidth / 2) and
      // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
      // the same space for the logo to be centered
      g.drawImage(overly, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2),
          null);

      // Write combined image as PNG to OutputStream
      ImageIO.write(combined, "png", os);
      // Store Image
    } catch (WriterException e) {
      e.printStackTrace();
      throw e;
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
      //LOG.error("IOException occured", e);
    }
    return os.toByteArray();
  }

  private BufferedImage getOverly(String logoFilename) throws IOException {
    Resource resource = new ClassPathResource(logoFilename);
    return ImageIO.read(resource.getURL());
  }

//  private void initDirectory(String DIR) throws IOException {
//    Files.createDirectories(Paths.get(DIR));
//  }
//
//  private void cleanDirectory(String DIR) {
//    try {
//      Files.walk(Paths.get(DIR), FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder())
//          .map(Path::toFile).forEach(File::delete);
//    } catch (IOException e) {
//      // Directory does not exist, Do nothing
//    }
//  }

  private MatrixToImageConfig getMatrixConfig() {
    int qrCodeBodyArgb = QRcodeUtils.Colors.WHITE.getArgb();
    int qrCodeBackgroundArgb = QRcodeUtils.Colors.ORANGE.getArgb();
    return new MatrixToImageConfig(qrCodeBodyArgb, qrCodeBackgroundArgb);
  }

  private String generateRandoTitle(Random random, int length) {
    return random.ints(48, 122).filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
        .mapToObj(i -> (char) i).limit(length)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
  }

  public enum Colors {

    BLUE(0xFF40BAD0), RED(0xFFE91C43), PURPLE(0xFF8A4F9E), ORANGE(0xFFF4B13D), WHITE(
        0xFFFFFFFF), BLACK(0xFF000000);

    private final int argb;

    Colors(final int argb) {
      this.argb = argb;
    }

    public int getArgb() {
      return argb;
    }
  }
}
