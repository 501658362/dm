package org.cuiyang.dm;

import com.jacob.com.Dispatch;

import java.util.Collections;
import java.util.List;

/**
 * 大漠加强版
 *
 * @author cuiyang
 */
public class DmSoftPlus extends DmSoft {

    /** 往左走按键码 */
    protected int goLeftKey = 37;
    /** 往上走按键码 */
    protected int goUpKey = 38;
    /** 往右走按键码 */
    protected int goRightKey = 39;
    /** 往下走按键码 */
    protected int goDownKey = 40;

    /**
     * 设置走位按键码
     * @param goLeftKey 左
     * @param goUpKey 上
     * @param goRightKey 右
     * @param goDownKey 下
     */
    public void setGoKey(int goLeftKey, int goUpKey, int goRightKey, int goDownKey) {
        this.goLeftKey = goLeftKey;
        this.goUpKey = goUpKey;
        this.goRightKey = goRightKey;
        this.goDownKey = goDownKey;
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标.
     *
     * @param x1          区域的左上X坐标
     * @param y1          区域的左上Y坐标
     * @param x2          区域的右下X坐标
     * @param y2          区域的右下Y坐标
     * @param picName    图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim         相似度,取值范围0.1-1.0
     * @param dir         查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回找到的图片序号(从0开始索引)以及X和Y坐标 形式如"index|x|y", 比如"3|100|200"
     */
    public Position findPic(int x1, int y1, int x2, int y2, String picName, String deltaColor, double sim, int dir) {
        String ret = Dispatch.call(dm, "FindPicE", x1, y1, x2, y2, picName, deltaColor, sim, dir).getString();
        return Position.parse(ret);
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标.
     *
     * @param x1          区域的左上X坐标
     * @param y1          区域的左上Y坐标
     * @param x2          区域的右下X坐标
     * @param y2          区域的右下Y坐标
     * @param picName    图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim         相似度,取值范围0.1-1.0
     * @param dir         查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回的是所有找到的坐标格式如下:"id,x,y|id,x,y..|id,x,y" (图片左上角的坐标)<br/>
     * 比如"0,100,20|2,30,40" 表示找到了两个,第一个,对应的图片是图像序号为0的图片,坐标是(100,20),第二个是序号为2的图片,坐标(30,40)(由于内存限制,返回的图片数量最多为1500个左右)
     */
    public List<Position> findPics(int x1, int y1, int x2, int y2, String picName, String deltaColor, double sim, int dir) {
        String ret = Dispatch.call(dm, "FindPicEx", x1, y1, x2, y2, picName, deltaColor, sim, dir).getString();
        return Position.parseList(ret);
    }

    /**
     * 全局找图片，参考findPic
     *
     * @param picName 图片名
     */
    public Position findPic(String picName) {
        return findPic(0, 0, 2000, 2000, picName, "000000", 0.9, 0);
    }

    /**
     * 在指定时间内全局找图片
     *
     * @param picName 图片名
     * @param num 重试次数
     * @param delay 每次延迟
     */
    public Position findPic(String picName, int num, long delay) {
        for (int i = 0; i <= num; i++) {
            Position position = findPic(picName);
            if (position != null) {
                return position;
            } else {
                delay(delay);
            }
        }
        return null;
    }

    /**
     * 全局找图片，参考findPics
     *
     * @param picName 图片名
     */
    public List<Position> findPics(String picName) {
        return findPics(0, 0, 2000, 2000, picName, "000000", 0.9, 0);
    }

    /**
     * 在指定时间内全局找图片
     *
     * @param picName 图片名
     * @param num 重试次数
     * @param delay 每次延迟
     */
    public List<Position> findPics(String picName, int num, long delay) {
        for (int i = 0; i <= num; i++) {
            List<Position> list = findPics(picName);
            if (!list.isEmpty()) {
                return list;
            } else {
                delay(delay);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 按压多个键
     */
    public void keyPress(long delay, int... codes) {
        for (int code : codes) {
            keyPress(code);
            delay(delay);
        }
    }

    /**
     * 重复按压
     */
    public void keyPressRepeat(int num, long delay, int code) {
        for (int i = 0; i < num; i++) {
            keyPress(code);
            delay(delay);
        }
    }

    /**
     * 持续按压
     */
    public void keyDownUp(long delay, int code) {
        keyDown(code);
        delay(delay);
        keyUp(code);
    }

    /**
     * 持续按压多个键
     */
    public void keyDownUp(long delay, int... codes) {
        for (int code : codes) {
            keyDown(code);
        }
        delay(delay);
        for (int code : codes) {
            keyUp(code);
        }
    }

    /**
     * 按压多个键
     */
    public void keyPressChar(long delay, String... codes) {
        for (String code : codes) {
            keyPressChar(code);
            delay(delay);
        }
    }

    /**
     * 按压多次
     */
    public void keyPressCharRepeat(int num, long delay, String code) {
        for (int i = 0; i < num; i++) {
            keyPressChar(code);
            delay(delay);
        }
    }

    /**
     * 键盘按压
     */
    public void keyDownUpChar(long delay, String code) {
        keyDownChar(code);
        delay(delay);
        keyUpChar(code);
    }

    /**
     * 键盘按压直到发现图片
     */
    protected Position keyPressUtilFindPic(int code, String picName, int num, long delay) {
        keyDown(code);
        try {
            Position position = findPic(picName, num, delay);
            if (position == null) {
                throw new DmException("超时，未找到图片 - " + picName);
            }
            return position;
        } finally {
            keyUp(code);
        }
    }

    /**
     * 左走 直到发现图片
     */
    protected Position goLeftUtilFindPic(String picName, int num, long delay) {
        return keyPressUtilFindPic(goLeftKey, picName, num, delay);
    }

    /**
     * 上走 直到发现图片
     */
    protected Position goUpUtilFindPic(String picName, int num, long delay) {
        return keyPressUtilFindPic(goUpKey, picName, num, delay);
    }

    /**
     * 右走 直到发现图片
     */
    protected Position goRightUtilFindPic(String picName, int num, long delay) {
        return keyPressUtilFindPic(goRightKey, picName, num, delay);
    }

    /**
     * 下走 直到发现图片
     */
    protected Position goDownUtilFindPic(String picName, int num, long delay) {
        return keyPressUtilFindPic(goDownKey, picName, num, delay);
    }

    /**
     * 左走
     * @param millis 走的时间，单位毫秒
     */
    public void goLeft(long millis) {
        keyDownUp(millis, goLeftKey);
    }

    /**
     * 上走
     * @param millis 走的时间，单位毫秒
     */
    public void goUp(long millis) {
        keyDownUp(millis, goUpKey);
    }

    /**
     * 右走
     * @param millis 走的时间，单位毫秒
     */
    public void goRight(long millis) {
        keyDownUp(millis, goRightKey);
    }

    /**
     * 下走
     * @param millis 走的时间，单位毫秒
     */
    public void goDown(long millis) {
        keyDownUp(millis, goDownKey);
    }

    /**
     * 左上走
     */
    public void goLeftUp(int millis) {
        keyPress(millis, goLeftKey, goUpKey);
    }

    /**
     * 右上走
     */
    public void rightUp(int millis) {
        keyPress(millis, goRightKey, goUpKey);
    }

    /**
     * 左下走
     */
    public void leftDown(int millis) {
        keyPress(millis, goLeftKey, goDownKey);
    }

    /**
     * 右下走
     */
    public void rightDown(int millis) {
        keyPress(millis, goRightKey, goDownKey);
    }

    /**
     * esc
     */
    public void esc() {
        keyPress(27);
    }

    /**
     * enter
     */
    public void enter() {
        keyPress(13);
    }

    /**
     * space
     */
    public void space() {
        keyPress(32);
    }

    /**
     * backspace
     */
    public void backspace() {
        keyPress(8);
    }

    /**
     * 左击图片
     */
    public int leftClickPic(String picName, int num, long delay) {
        Position pic = findPic(picName, num, delay);
        if (pic == null) {
            throw new DmException("点击图片失败, 未找到要点击的图片 - " + picName);
        }
        return leftClick(pic.getX() + 2, pic.getY() + 2);
    }

    /**
     * 左击图片
     */
    public int leftClickPic(String picName) {
        Position pic = findPic(picName);
        if (pic == null) {
            throw new DmException("点击图片失败, 未找到要点击的图片 - " + picName);
        }
        return leftClick(pic.getX() + 2, pic.getY() + 2);
    }

    /**
     * 左击坐标
     */
    public int leftClick(Position position) {
        return leftClick(position.getX(), position.getY());
    }

    /**
     * 点击坐标
     */
    public int leftClick(int x, int y) {
        moveTo(x, y);
        return leftClick();
    }

    /**
     * 双击坐标
     */
    public int leftDoubleClick(int x, int y) {
        moveTo(x, y);
        return leftDoubleClick();
    }

}
