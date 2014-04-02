/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sm

/**
 *
 * @author think
 */
class IpUtil {
    public static String getIpAddr(def request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf("0:") != -1) {
            ip = "本地";
        }
        return ip;
    }
}

