package org.bambrikii.examples.spark3.ibm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

@Slf4j
public class IbmTextFormatTest {

    public static final String IBM_1140 = "IBM1147";

    @Test
    public void shouldReadCustomFormat() throws IOException {
//        ByteBuffer rowBuf = ByteBuffer.allocate(141);

        var prefixBuf = ByteBuffer.allocate(3);
        var surnameBuf = ByteBuffer.allocate(15);
        var firstnameBuf = ByteBuffer.allocate(9);
        var addressBuf = ByteBuffer.allocate(76);
        var commentsBuf = ByteBuffer.allocate(40);
        var newLineBuffer = ByteBuffer.allocate(1);
        var buffers = new ByteBuffer[]{
                prefixBuf,
                surnameBuf,
                firstnameBuf,
                addressBuf,
                commentsBuf,
                newLineBuffer
        };

        try (var is = IbmTextFormatTest.class.getResourceAsStream("/sample-customer-data.ebcdic");
             var bis = new BufferedInputStream(is);
             var channel = Channels.newChannel(bis);
        ) {
            w:
            while (true) {
                for (var buffer : buffers) {
                    int read = channel.read(buffer);
                    if (read <= 0) {
                        break w;
                    }
                    var prefix = new String(buffer.array(), 0, buffer.position(), IBM_1140).trim();
                    buffer.flip();
                    System.out.printf(prefix + "|");
                }
                System.out.println();
            }
        }
    }
}
