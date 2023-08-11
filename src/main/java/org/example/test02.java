package org.example;

import com.novell.ldap.*;
import com.novell.ldap.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;

public class test02 {
    public void connect() {

        LDAPConnection lc = new LDAPConnection();
        try {


            lc.connect("192.168.247.129", 389);


            lc.bind(3, "cn=admin,dc=yang,dc=com", "0");

            LDAPSearchResults searchResults = lc.search("dc=yang,dc=com", LDAPConnection.SCOPE_SUB, "objectClass=*", null, false);

            System.out.println();


            while (searchResults.hasMore()) {
                LDAPEntry nextEntry = null;
                try {
                    nextEntry = searchResults.next();
                } catch (LDAPException e) {
                    System.out.println("Error: " + e.toString());
                    if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
                            || e.getResultCode() == LDAPException.CONNECT_ERROR) {
                        break;
                    } else {
                        continue;
                    }
                }
                System.out.println("DN =: " + nextEntry.getDN());
                System.out.println("|---- Attributes list: ");
                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator<LDAPAttribute> allAttributes = attributeSet.iterator();
                while (allAttributes.hasNext()) {
                    LDAPAttribute attribute = allAttributes.next();
                    String attributeName = attribute.getName();

                    Enumeration<String> allValues = attribute.getStringValues();
                    if (null == allValues) {
                        continue;
                    }
                    while (allValues.hasMoreElements()) {
                        String value = allValues.nextElement();
                        if (!Base64.isLDIFSafe(value)) {
                            // base64 encode and then print out
                            value = Base64.encode(value.getBytes());
                        }
                        System.out.println("|---- ---- " + attributeName
                                + " = " + value);
                    }
                }
            }

        } catch (LDAPException e) {
            System.out.println("Error: " + e.toString());
        } finally {
            try {
                if (lc.isConnected()) {
                    lc.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
