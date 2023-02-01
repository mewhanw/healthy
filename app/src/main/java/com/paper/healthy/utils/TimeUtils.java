package com.paper.healthy.utils;

public class TimeUtils {
    /**
     * 将秒转为时分秒格式【01:01:01】
     * @param second 需要转化的秒数
     * @return
     */
    public static String secondConvertHourMinSecond(Long second) {
        String str = "00:00:00";
        if (second == null || second < 0) {
            return str;
        }

        // 得到小时
        long h = second / 3600;
        str = h > 0 ? ((h < 10 ? ("0" + h) : h) + ":") : "00:";

        // 得到分钟
        long m = (second % 3600) / 60;
        str += (m < 10 ? ("0" + m) : m) + ":";

        //得到剩余秒
        long s = second % 60;
        str += (s < 10 ? ("0" + s) : s);
        return str;
    }
}
