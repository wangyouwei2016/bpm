package com.dstz.component.mq.msg.engine.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.dstz.base.common.property.PropertyEnum;


/**
 * 发邮件工具类
 *
 * @author lightning
 */
public class EmailUtil {

    private static MailAccount account;

    /**
     * <pre>
     * 用ab的邮箱发邮件
     * </pre>
     *
     * @param email   目标邮件 eg:aschs@qq.com
     * @param subject 主题
     * @param content 内容（内容支持html）
     */
    public static void send(String email, String subject, String content) {
            MailUtil.send(account(), CollUtil.newArrayList(email), subject, content, true);
    }

    private static MailAccount account() {
        if (account != null) {
            return account;
        }

        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(PropertyEnum.EMAIL_HOST.getPropertyValue(String.class));
        mailAccount.setPort(PropertyEnum.EMAIL_PORT.getPropertyValue(Integer.class));
        mailAccount.setFrom(PropertyEnum.EMAIL_ADDRESS.getPropertyValue(String.class));
        mailAccount.setUser(PropertyEnum.EMAIL_NICKNAME.getPropertyValue(String.class));
        mailAccount.setPass(PropertyEnum.EMAIL_PASSWORD.getPropertyValue(String.class));
        mailAccount.setSslEnable(PropertyEnum.EMAIL_SSL.getPropertyValue(Boolean.class));
        setAccount(mailAccount);
        return mailAccount;
    }

    /**
     * spring boot项目启动的时候设置参数
     *
     * @param account
     */
    public static void setAccount(MailAccount account) {
        EmailUtil.account = account;
    }

    public static void main(String[] args) {
        MailAccount mailAccount = new MailAccount();
        String host = "smtp.qq.com";
        int port = 465;
        boolean isSSL = true;
        String user = "956699386@qq.com";
        String from = "956699386@qq.com";
        String pass = "wubmeeilxudybajh";
        mailAccount.setHost(host);
        mailAccount.setPort(port);
        mailAccount.setFrom(from);
        mailAccount.setUser(user);
        mailAccount.setPass(pass);
        mailAccount.setSslEnable(isSSL);
        mailAccount.setDebug(true);
        setAccount(mailAccount);

        String subject = "任务待办通知";
        String content = "<p>您有新的待办需要审批:管工发起poc流程</p><p></p>";
        MailUtil.send(mailAccount, "511322311@qq.com", subject, content, true);
    }
}
