/**
 * @author ex_kjkfb_xupiao 2016年4月22日
 */
package nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 定长前缀协议编码器，用来兼用架构中定长前缀的协议
 * @author ex_kjkfb_xupiao 2016年4月22日
 *
 */
public class LengthPrefixProtocolEncoder extends MessageToMessageEncoder<ByteBuf> {
	
	private final int frameLength ;
	private final String encoding ;
	private final String padding ;

	/**
	 * @param frameLength
	 * @param encoding
	 */
	public LengthPrefixProtocolEncoder(int frameLength, String encoding, String padding) {
		if (frameLength <= 0) {
			throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
		}
		this.frameLength = frameLength;
		this.encoding = encoding;
		this.padding = padding;
	}



	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToMessageEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, java.util.List)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		String length = pad(msg.readableBytes()+"", this.padding,this.frameLength,true) ; //报文总长度
		System.out.println("发送长度："+length);
		byte [] b = length.getBytes(this.encoding) ;
		ByteBuf bb = ctx.alloc().buffer(b.length) ;//Unpooled.buffer(this.frameLength);
		bb.writeBytes(b);
		out.add(bb.retain());
		out.add(msg.retain()) ;
	}
	
	protected static String pad(String s, String pad, int len, boolean left) {
		if (s.length() > len)
			throw new IllegalArgumentException("Exceed to max message size: " + len + " -> " + s.length());
		StringBuffer ret = new StringBuffer();
		if (!left) ret.append(s);
		for (int i = 0; i < len - s.length(); i++) {
			ret.append(pad);
		}
		if (left) ret.append(s);
		return ret.toString();
	}
}
