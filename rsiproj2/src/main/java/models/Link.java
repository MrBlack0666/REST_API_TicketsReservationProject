/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Mateusz
 */
public class Link {
    private String link;
    private String rel;

    public Link() {}
    
    public Link(String UriPath, String rel) {
        this.link = UriPath;
        this.rel = rel;
    }
    
    public String getUriPath() {
        return link;
    }

    public void setUriPath(String UriPath) {
        this.link = UriPath;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}