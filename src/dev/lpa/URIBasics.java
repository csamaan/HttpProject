package dev.lpa;

import java.net.URI;

public class URIBasics {
    public static void main(String[] args) {

    }

    private static void print(URI uri) {
        System.out.printf("""
                
                --------------------------------------------
                [scheme:]scheme-specific-part[#fragment]
                --------------------------------------------
                Scheme: %s
                Scheme-specific-part: %s
                Authority: %s
                    UserInfo: %s
                    Host: %s
                    Port: %s
                    Path: %s
                    Query: %s
                Fragment: %s
                """,
                uri.getScheme(),
                uri.getSchemeSpecificPart(),
                uri.getAuthority(),
                uri.getUserInfo(),
                uri.getHost(),
                uri.getPort(),
                uri.getQuery(),
                uri.getFragment());
    }
}
