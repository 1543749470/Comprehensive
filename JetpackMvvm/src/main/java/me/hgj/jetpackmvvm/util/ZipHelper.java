package me.hgj.jetpackmvvm.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
public class ZipHelper {
    private ZipHelper() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * zlib decompress 2 String
     *
     * @param bytesToDecompress
     * @return
     */
    public static String decompressToStringForZlib(byte[] bytesToDecompress) {
        return decompressToStringForZlib(bytesToDecompress, "UTF-8");
    }

    /**
     * zlib decompress 2 String
     *
     * @param bytesToDecompress
     * @param charsetName
     * @return
     */
    public static String decompressToStringForZlib(byte[] bytesToDecompress, String charsetName) {
        byte[] bytesDecompressed = decompressForZlib
                (
                        bytesToDecompress
                );

        String returnValue = null;

        try {
            returnValue = new String
                    (
                            bytesDecompressed,
                            0,
                            bytesDecompressed.length,
                            charsetName
                    );
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return returnValue;

    }

    /**
     * zlib decompress 2 byte
     *
     * @param bytesToDecompress
     * @return
     */
    public static byte[] decompressForZlib(byte[] bytesToDecompress) {
        byte[] returnValues = null;

        Inflater inflater = new Inflater();

        int numberOfBytesToDecompress = bytesToDecompress.length;

        inflater.setInput
                (
                        bytesToDecompress,
                        0,
                        numberOfBytesToDecompress
                );

        int bufferSizeInBytes = numberOfBytesToDecompress;

        int numberOfBytesDecompressedSoFar = 0;
        List<Byte> bytesDecompressedSoFar = new ArrayList<Byte>();

        try {
            while (inflater.needsInput() == false) {
                byte[] bytesDecompressedBuffer = new byte[bufferSizeInBytes];

                int numberOfBytesDecompressedThisTime = inflater.inflate
                        (
                                bytesDecompressedBuffer
                        );

                numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime;

                for (int b = 0; b < numberOfBytesDecompressedThisTime; b++) {
                    bytesDecompressedSoFar.add(bytesDecompressedBuffer[b]);
                }
            }

            returnValues = new byte[bytesDecompressedSoFar.size()];
            for (int b = 0; b < returnValues.length; b++) {
                returnValues[b] = (byte) (bytesDecompressedSoFar.get(b));
            }

        } catch (DataFormatException dfe) {
            dfe.printStackTrace();
        }

        inflater.end();

        return returnValues;
    }

    /**
     * zlib compress 2 byte
     *
     * @param bytesToCompress
     * @return
     */
    public static byte[] compressForZlib(byte[] bytesToCompress) {
        Deflater deflater = new Deflater();
        deflater.setInput(bytesToCompress);
        deflater.finish();

        byte[] bytesCompressed = new byte[Short.MAX_VALUE];

        int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);

        byte[] returnValues = new byte[numberOfBytesAfterCompression];

        System.arraycopy
                (
                        bytesCompressed,
                        0,
                        returnValues,
                        0,
                        numberOfBytesAfterCompression
                );

        return returnValues;
    }

    /**
     * zlib compress 2 byte
     *
     * @param stringToCompress
     * @return
     */
    public static byte[] compressForZlib(String stringToCompress) {
        byte[] returnValues = null;

        try {

            returnValues = compressForZlib
                    (
                            stringToCompress.getBytes("UTF-8")
                    );
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return returnValues;
    }

    /**
     * gzip compress 2 byte
     *
     * @param string
     * @return
     * @throws IOException
     */
    public static byte[] compressForGzip(String string) {
        ByteArrayOutputStream os = null;
        GZIPOutputStream gos = null;
        try {
            os = new ByteArrayOutputStream(string.length());
            gos = new GZIPOutputStream(os);
            gos.write(string.getBytes("UTF-8"));
            byte[] compressed = os.toByteArray();
            return compressed;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(gos);
            closeQuietly(os);
        }
        return null;
    }

    /**
     * gzip decompress 2 string
     *
     * @param compressed
     * @return
     * @throws IOException
     */
    public static String decompressForGzip(byte[] compressed) {
        return decompressForGzip(compressed, "UTF-8");
    }

    /**
     * gzip decompress 2 string
     *
     * @param compressed
     * @param charsetName
     * @return
     */
    public static String decompressForGzip(byte[] compressed, String charsetName) {
        final int BUFFER_SIZE = compressed.length;
        GZIPInputStream gis = null;
        ByteArrayInputStream is = null;
        try {
            is = new ByteArrayInputStream(compressed);
            gis = new GZIPInputStream(is, BUFFER_SIZE);
            StringBuilder string = new StringBuilder();
            byte[] data = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = gis.read(data)) != -1) {
                string.append(new String(data, 0, bytesRead, charsetName));
            }
            return string.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(gis);
            closeQuietly(is);
        }
        return null;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void zipFile(File file) throws FileNotFoundException {
        if (file == null) {
            return;
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file), StandardCharsets.UTF_8);
    }

    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 压缩完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
        //创建ZIP
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        //创建文件
        File file = new File(srcFileString);
        //压缩
        ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
        //完成和关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            //文件夹
            String fileList[] = file.list();
            //没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            //子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString + fileString + "/", fileList[i], zipOutputSteam);
            }
        }
    }
}
