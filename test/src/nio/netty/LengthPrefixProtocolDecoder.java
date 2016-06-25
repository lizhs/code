/**
 * @author ex_kjkfb_xupiao 2016年4月22日
 */
package nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.base.Objects;

/**
 * 定长前缀协议解码器，用来兼用架构中定长前缀的协议
 * 
 * @author ex_kjkfb_xupiao 2016年4月22日
 *
 */
public class LengthPrefixProtocolDecoder extends ByteToMessageDecoder {

    private final int headLen;
    private final String encoding;

    /**
     * @param frameLength
     */
    public LengthPrefixProtocolDecoder(int frameLength, String encoding) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
        }
        this.headLen = frameLength;
        this.encoding = encoding;
//        setSingleDecode(true);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            if ((in.readableBytes() < this.headLen)) {
                return;
            }
            int bodyLen = 0;
            byte[] b = new byte[this.headLen];
            String kdnr =getReadableString(in);
            System.out.println("处理前：" + in + "  " + ObjectUtils.identityToString(in));
            System.out.println("处理前"+kdnr.length() + "可读内容：" + kdnr);
            in.getBytes(in.readerIndex(), b);
            //			System.out.println(bytes);
            //			in.readBytes(b) ;
            //			in.resetReaderIndex() ;

            String length = new String(b, this.encoding);
            try {
                bodyLen = Integer.parseInt(length.trim());
            } catch (Exception e) {
                throw e;
            }
            System.out.println("处理中：" + bodyLen);
            if (in.readableBytes() < bodyLen + this.headLen)
                return;

            in.skipBytes(this.headLen);
            ByteBuf frame = ctx.alloc().buffer(bodyLen);
            int readerIndex = in.readerIndex();
            frame.writeBytes(in, readerIndex, bodyLen);
            in.readBytes(bodyLen);
            //			in.readBytes(length);
            //			frame.writeBytes(in, 0, lengthPrefix) ;
            //			in.clear() ;
            System.out.println("处理后：" + in + "  " + ObjectUtils.identityToString(in));
            System.out.println("处理后"+kdnr.length() + "可读内容：" + kdnr);

            out.add(frame);
        } catch (Exception e) {
            throw new RuntimeException("报文前缀获取失败", e);
        }
    }

    private String getReadableString(ByteBuf in) {
        int readableBytes = in.readableBytes();
        byte[] kdnr = new byte[readableBytes];
        in.getBytes(in.readerIndex(), kdnr);
        return new String(kdnr);
    }
}
