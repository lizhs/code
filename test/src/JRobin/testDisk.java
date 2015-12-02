package JRobin;

import org.jrobin.core.*;
import java.io.*;
import java.util.Date;
import org.jrobin.graph.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class testDisk {
    public static void main(String[] args) {
        testDisk test = new testDisk();
        String rootPath = "d:/";
        String imgFileName = "testDisk.png";
        try {
            RrdDef rrdDef = new RrdDef("d:\\testDisk.rrd");
            //获得2012年9月24日的时间戳
            Long startTime = Util.getTimestamp(2012, 9 - 1, 24);
            System.out.println("startTime:" + startTime);
            rrdDef.setStartTime(startTime);
            //数据源：speed  数据源类型：counter 如果超过600s没有数据，显示UNKNOW 最小值Double.NaN,最大值。。
            rrdDef.addDatasource("disk", "GAUGE", 600, Double.NaN, Double.NaN);
            //RRA:AVERAGE:0.5:12:24 (1天) 
            //rrdDef.addArchive("AVERAGE", 0.5, 12, 24);
            rrdDef.addArchive("AVERAGE", 0.5, 6, 48);
            RrdDb rrdDb = new RrdDb(rrdDef);
            Sample sample = rrdDb.createSample();
            sample.setAndUpdate("1348416300:12.3");
            sample.setAndUpdate("1348416600:14.56");
            sample.setAndUpdate("1348416900:14.67");
            sample.setAndUpdate("1348417200:12.21");
            sample.setAndUpdate("1348417500:37.6");
            sample.setAndUpdate("1348417800:43.22");
            sample.setAndUpdate("1348418100:17.12");
            sample.setAndUpdate("1348418400:16.54");
            sample.setAndUpdate("1348418700:42.33");
            sample.setAndUpdate("1348419000:12.21");
            sample.setAndUpdate("1348419300:12.43");
            sample.setAndUpdate("1348419600:14.43");
            rrdDb.close();
            RrdGraphDef graphDef = new RrdGraphDef();
            graphDef.datasource("diskUsage", "d:\\testDisk.rrd", "disk", "AVERAGE");
            graphDef.line("diskUsage", new Color(0xFF, 0, 0), null, 2);
            graphDef.setImageFormat("png");
            RrdGraph graph = new RrdGraph(graphDef);
            System.out.println("[rrd graph info:]"
                    + graph.getRrdGraphInfo().dump());
            test.createImgFile(graph, "d:\\disk.png");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 生成图片文件
     */
    private void createImgFile(RrdGraph graph, String imgFileFullName) {

        try {
            BufferedImage image = new BufferedImage(graph.getRrdGraphInfo()
                    .getWidth(), graph.getRrdGraphInfo().getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            graph.render(image.getGraphics());
            File imgFile = new File(imgFileFullName);
            ImageIO.write(image, "png", imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}