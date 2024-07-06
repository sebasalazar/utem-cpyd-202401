package cl.utem.ws.util;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    // Esto es con fines explicativos, en el mundo real, se debe proteger.
    public static final String UTEM_KEY = "S4aJGbW$N7Ta#cP2";
    private static final String ALGORITHM = "AES";
    private static final String OPERATION_MODE = "AES/GCM/NoPadding";
    private static final int IV_SIZE = 16;
    private static final int PARAM_SIZE = 128;
    private static final Logger LOGGER = LoggerFactory.getLogger(AesUtils.class);

    public static String encrypt(final String message) {
        return encrypt(UTEM_KEY, message);
    }

    public static String encrypt(final String key, final String message) {
        String result = StringUtils.EMPTY;
        try {
            if (!StringUtils.isAnyBlank(key, message)) {
                byte[] biv = new byte[IV_SIZE];
                SecureRandom.getInstanceStrong().nextBytes(biv);
                final GCMParameterSpec parameter = new GCMParameterSpec(PARAM_SIZE, biv);

                final byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                final SecretKeySpec ks = new SecretKeySpec(keyBytes, ALGORITHM);

                Cipher cipher = Cipher.getInstance(OPERATION_MODE);
                cipher.init(Cipher.ENCRYPT_MODE, ks, parameter);
                final byte[] out = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

                final String head = Hex.encodeHexString(biv);
                final String tail = Hex.encodeHexString(out);
                result = head + tail;
            }
        } catch (Exception e) {
            LOGGER.error("Error al cifrar: {}", e.getLocalizedMessage());
            LOGGER.debug("Error al cifrar: {}", e.getMessage(), e);
        }
        return result;
    }

    public static String decrypt(final String message) {
        return decrypt(UTEM_KEY, message);
    }

    public static String decrypt(final String key, final String message) {
        String result = StringUtils.EMPTY;
        try {
            if (!StringUtils.isAnyBlank(key, message)) {
                final int keySize = IV_SIZE * 2;
                final String head = message.substring(0, keySize);
                final String tail = message.substring(keySize);
                final byte[] biv = Hex.decodeHex(head);
                final byte[] data = Hex.decodeHex(tail);

                final GCMParameterSpec parameter = new GCMParameterSpec(PARAM_SIZE, biv);

                final byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                final SecretKeySpec ks = new SecretKeySpec(keyBytes, ALGORITHM);

                Cipher cipher = Cipher.getInstance(OPERATION_MODE);
                cipher.init(Cipher.DECRYPT_MODE, ks, parameter);
                byte[] decripted = cipher.doFinal(data);
                result = new String(decripted, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            LOGGER.error("Error al descifrar: {}", e.getLocalizedMessage());
            LOGGER.debug("Error al descifrar: {}", e.getMessage(), e);
        }
        return result;
    }
}
