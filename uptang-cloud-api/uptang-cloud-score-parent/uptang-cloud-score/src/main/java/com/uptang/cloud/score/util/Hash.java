package com.uptang.cloud.score.util;

import com.uptang.cloud.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 22:12
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class Hash {

    /**
     * 计算文件MD5值
     */
    public static class MD5 {

        /**
         * MD5
         */
        public static final String ALGORITHM = "md5";

        /**
         * @return
         */
        public static String smallFile() {
            final String[] hash = new String[1];
            Http.Request.getCurrent().ifPresent(request -> {
                try (ServletInputStream inputStream = request.getInputStream();
                     ReadableByteChannel channel = Channels.newChannel(inputStream)) {
                    final MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                    final ByteBuffer buffer = ByteBuffer.allocate(512);
                    while (channel.read(buffer) != -1) {
                        buffer.flip();
                        messageDigest.update(buffer);
                        buffer.clear();
                    }
                    hash[0] = Hex.encodeHexString(messageDigest.digest());
                } catch (IOException e) {
                    throw new BusinessException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new BusinessException(e);
                }
            });
            return hash[0];
        }

        /**
         * 几十兆的文件
         *
         * @return
         */
        public static String middleFile() {
            final String[] hash = new String[1];
            Http.Request.getCurrent().ifPresent(request -> {
                try (ServletInputStream inputStream = request.getInputStream()) {
                    final MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

                    int length;
                    byte[] block = new byte[4096];
                    while ((length = inputStream.read(block)) > 0) {
                        messageDigest.update(block, 0, length);
                    }

                    hash[0] = Hex.encodeHexString(messageDigest.digest());
                } catch (IOException e) {
                    throw new BusinessException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new BusinessException(e);
                }
            });
            return hash[0];
        }
    }
}
