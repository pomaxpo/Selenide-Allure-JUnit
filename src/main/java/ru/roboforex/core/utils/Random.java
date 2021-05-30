package ru.roboforex.core.utils;

import com.mifmif.common.regex.Generex;
import org.springframework.stereotype.Component;
import ru.roboforex.core.exceptions.CoreException;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Random {

    private static final String enStr = "([A-Za-z]{%d})";
    private static final String ruStr = "([А-Яа-я]{%d})";
    private static final String intStr = "(\\d{%d})";
    private static final String numberStr = "(\\d{%d}\\.\\d{%d})";
    private static final String booleanStr = "(true|false)";
    private static final String dateStr = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)[7-9][0-9])"; // DD.MM.YYYY
    private static final String timeStr = "(0[0-9]|1[0-9]|2[0-3])(0[0-9]|[1-5][0-9])(0[0-9]|[1-5][0-9])";
    private Map<String, String> regexpMap = new HashMap<>();

    @PostConstruct
    private void init() {
        regexpMap.put("9", "([0-9])");
        regexpMap.put("L", "([A-ZЁА-Я])");
        regexpMap.put("l", "([a-zёа-я])");
        regexpMap.put("A", "[а-яА-ЯёЁa-zA-Z0-9]");
        regexpMap.put("X", "");
    }

    public String getMaskedString(String mask) {
        return new Generex(getRegexpByMask(mask)).random();
    }

    public String getEnString(int length) {
        return new Generex(String.format(enStr, length)).random();
    }

    public String getRuString(int length) {
        return new Generex(String.format(ruStr, length)).random();
    }

    public Integer getIntegerWithLength(int length) {
        return Integer.valueOf(new Generex(String.format(intStr, length)).random());
    }

    public Integer getIntegerWithBound(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public Double getDouble(int length, int digitsAfterPoint) {
        if (digitsAfterPoint > length) {
            throw new CoreException("Неверно введены значения. Количество цифр после запятой не должно превышать" +
                    " общей длинны генеринуемго числа");
        }
        return Double.valueOf(new Generex(String.format(numberStr, length - digitsAfterPoint - 1, digitsAfterPoint)).random());
    }

    public Double getDouble() {
        return ThreadLocalRandom.current().nextDouble(Integer.MAX_VALUE);
    }

    public Boolean getBoolean() {
        return Boolean.getBoolean(new Generex(booleanStr).random());
    }

    public String getDate() {
        return new Generex(dateStr).random();
    }

    public String getTime() {
        return new Generex(timeStr).random();
    }

    public String getClob() {
        return getClob(getIntegerWithLength(2));
    }

    public String getClob(int linesCount) {
        String[] buf = new String[linesCount];
        for (int i = 0; i < linesCount; i++) {
            buf[i] = getRuString(getIntegerWithLength(2));
        }
        return createTempTextFile(buf).toAbsolutePath().toString();
    }

    public String getBlob() {
        return getBlob(getEnString(5), getEnString(3), getIntegerWithLength(3));
    }

    public String getBlob(String prefix, String suffix, int size) {
        return createTempBinaryFile(prefix, suffix, new byte[size]).toAbsolutePath().toString();
    }

    private String getRegexpByMask(String mask) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < mask.length()) {
            String ch = String.valueOf(mask.charAt(i));
            if (regexpMap.containsKey(ch)) {
                if (regexpMap.get(ch).isEmpty()) {
                    builder.append("(");
                    do {
                        i++;
                        if (regexpMap.get(String.valueOf(mask.charAt(i))) == null) {
                            builder.append(regexpMap.get(String.valueOf(mask.charAt(i))));
                        }
                    } while (regexpMap.get(String.valueOf(mask.charAt(i))) != null);
                    builder.append(")");
                } else {
                    builder.append(regexpMap.get(ch));
                }
            } else {
                builder.append(mask.charAt(i));
            }
            i++;
        }
        return builder.toString();
    }

    private Path createTempBinaryFile(String prefix, String suffix, byte[] data) {
        Path filePath = null;
        try {
            filePath = Files.createTempFile(prefix, suffix);
            Files.write(filePath, data);
        } catch (IOException e) {
            throw new CoreException("Невозможно создать файл во временной дирректории.", e.getCause());
        }
        return filePath;
    }

    private Path createTempTextFile(String[] data) {
        Path filePath = null;
        try {
            filePath = Files.createTempFile("clob", ".txt");
        } catch (IOException e) {
            throw new CoreException("Невозможно создать текстовый файл во временной дирректории.", e.getCause());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, UTF_8)) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CoreException("Невозможно создать текстовый файл во временной дирректории.", e.getCause());
        }
        return filePath;
    }

}
