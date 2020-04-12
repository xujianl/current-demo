package com.xj.thread;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: VolatileByte
 * @Description:
 * @date 2020/2/29 21:49
 */
public class VolatileByte {
    volatile  int n = 0;
    public void add(){
        n++;
    }
}
