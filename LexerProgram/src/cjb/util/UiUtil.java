
package cjb.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class UiUtil {
    private UiUtil(){
    }
    //修改窗体图标
    public static void setFrameImage(JFrame jf){
        //获取工具类对象
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image i=tk.getImage("src\\cjb\\resource\\cat2.jpg");
        jf.setIconImage(i);
    }
    //设置窗体居中
    public static void setFrameCenter(JFrame jf) {
        /*
         思路：
         A:获取屏幕的宽和高
         B:获取窗体的宽和高
         C:(用屏幕的宽-窗体的宽)/2，(用屏幕的高-窗体的高)/2作为窗体的新坐标。
         */
        //获取工具对象
        Toolkit tk = Toolkit.getDefaultToolkit();

        //获取屏幕的宽和高
        Dimension d = tk.getScreenSize();
        double srceenWidth = d.getWidth();
        double srceenHeigth = d.getHeight();

        //获取窗体的宽和高
        int frameWidth = jf.getWidth();
        int frameHeight = jf.getHeight();

        //获取新的宽和高
        int width = (int) (srceenWidth - frameWidth) / 2;
        int height = (int) (srceenHeigth - frameHeight) / 2;

        //设置窗体坐标
        jf.setLocation(width, height);
    }
}
