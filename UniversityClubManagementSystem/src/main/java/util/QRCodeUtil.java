package util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

    public static void generate(String text, Path outputPath) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, 300, 300, hints);

        MatrixToImageWriter.writeToPath(matrix, "PNG", outputPath);
    }
}
