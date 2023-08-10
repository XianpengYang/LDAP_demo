package org.example;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;
import javax.naming.ldap.LdapName;

public class LdapConnect {

    public void connect() {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.247.129:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=yang,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "0");

// 获取初始上下文
        try {
            DirContext ctx = new InitialDirContext(env);
            // 创建数据属性

            for (int i = 0; i < 100; i++) {
                BasicAttributes attrs = new BasicAttributes();
                attrs.put("objectClass", "inetOrgPerson");
                attrs.put("ou", "auto");
                attrs.put("cn", "lee");
                attrs.put("sn", String.valueOf(i));
                attrs.put("uid", "001002003" + i);
                attrs.put("telephoneNumber","1389828765"+i);
// 创建目标DN
                LdapName dn = new LdapName("uid=" + "001002003" + i + ",dc=yang,dc=com");

// 将数据绑定到目标DN
                ctx.bind(dn, null, attrs);
            }


        } catch (NamingException ne) {
            System.out.println(ne);

        }


    }
}