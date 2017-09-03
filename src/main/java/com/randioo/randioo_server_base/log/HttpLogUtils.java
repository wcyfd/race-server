package com.randioo.randioo_server_base.log;

import java.text.MessageFormat;

import com.randioo.randioo_server_base.entity.RoleInterface;
import com.randioo.randioo_server_base.utils.TimeUtils;

public class HttpLogUtils {

    protected static String projectName = null;

    public static void setProjectName(String proj) {
        projectName = proj;
    }

    protected static boolean debug = false;

    /** 内网模式 */
    public static void setDebug(boolean value) {
        debug = value;
    }

    public static String role(RoleInterface role, Object message) {
        String roleId = role != null ? role.getRoleId() + "" : null;
        String name = role != null ? role.getName() : null;
        String account = role != null ? role.getAccount() : null;

        // StringBuilder sb = new StringBuilder();
        // sb.append("<ROLE>[roleId:").append(roleId).append(",account:").append(account).append(",name:").append(name)
        // .append("] * ").append(TimeUtils.getDetailTimeStr()).append(" *
        // ").append(message);
        // String output = sb.toString();
        // if (output.length() < 120)
        // output = output.replaceAll("\n", " ").replace("\t", " ").replace(" ",
        // "");

        String output = MessageFormat.format("<ROLE>[roleId:{0},account:{1},name:{2}] * {3} * {4}", roleId, account,
                name, TimeUtils.getDetailTimeStr(), message);
        if (output.length() < 120)
            output = output.replaceAll("\n", " ").replace("\t", " ").replace("  ", "");
        return output;
    }

    public static String sys(Object message) {
        // StringBuilder sb = new StringBuilder();
        // sb.append("<SYS> * ").append(TimeUtils.getDetailTimeStr()).append(" *
        // ").append(message);

        String sys = MessageFormat.format("<SYS> * {0} * {1}", TimeUtils.getDetailTimeStr(), message);
        return sys;
    }
}
