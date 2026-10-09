package starwu.lite.util;

public class DesensitizeUtil {

    /**
     * 手机号脱敏
     * 规则：13812345678 → 138****5678
     * @param phone 原始手机号
     * @return 脱敏后，null/长度不对原样返回
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 身份证号脱敏
     * 规则：220104199001011234 → 220104********1234
     * 支持18位；15位老身份证也兼容
     * @param idCard 身份证号
     * @return 脱敏后
     */
    public static String maskIdCard(String idCard) {
        if (idCard == null) {
            return idCard;
        }
        int len = idCard.length();
        if (len == 18) {
            return idCard.substring(0, 6) + "********" + idCard.substring(14);
        } else if (len == 15) {
            return idCard.substring(0, 6) + "******" + idCard.substring(12);
        }
        return idCard;
    }

    /**
     * 中文姓名脱敏
     * 张三 → 张*
     * 张三丰 → 张*丰
     * 司马相如 → 司**如
     * @param name 姓名
     * @return 脱敏后
     */
    public static String maskName(String name) {
        if (name == null || name.length() < 2) {
            return name;
        }
        int len = name.length();
        if (len == 2) {
            return name.charAt(0) + "*";
        } else if (len == 3) {
            return name.charAt(0) + "*" + name.charAt(2);
        } else {
            // >=4个字
            StringBuilder sb = new StringBuilder();
            sb.append(name.charAt(0));
            for (int i = 0; i < len - 2; i++) {
                sb.append("*");
            }
            sb.append(name.charAt(len - 1));
            return sb.toString();
        }
    }
}
