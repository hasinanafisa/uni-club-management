package util;

import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.*;

public class UploadUtil {

    // Base upload directory (Documents)
    public static final String BASE_DIR = System.getProperty("user.home") + "/uni-club-uploads";

    /**
     * Upload file and return relative DB path
     * @param part
     * @param subFolder
     * @param defaultFile
     * @return 
     * @throws java.lang.Exception 
     */
    public static String upload(
            Part part,
            String subFolder,
            String defaultFile
    ) throws Exception {

        if (part == null || part.getSize() == 0) {
            return subFolder + "/" + defaultFile;
        }

        String fileName = Paths.get(part.getSubmittedFileName())
                               .getFileName()
                               .toString();

        Path uploadDir = Paths.get(BASE_DIR, subFolder);
        Files.createDirectories(uploadDir);

        Path target = uploadDir.resolve(fileName);

        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // return RELATIVE path for DB
        return subFolder + "/" + fileName;
    }
}
