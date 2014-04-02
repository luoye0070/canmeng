/**
 *Title:        数字处理函数库
 *Description:  其它类型转换到数字等功能
 *@author:      杨飞
 *@create:      2011-12-2
 *@edit:
 */

package lj

class Number {

    // 任意类型转整形
    public static Integer toInteger(def n) {
        if (n) {
            String x = "${n}".trim()
            return x.isInteger() ? x.toInteger() : 0
        } else
            return 0
    }

    // 任意类型转长整形
    public static Long toLong(def n) {
        if (n) {
            String x = "${n}".trim()
            return x.isLong() ? x.toLong() : 0
        } else
            return 0
    }

}

