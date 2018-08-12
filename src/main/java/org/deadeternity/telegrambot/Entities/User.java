package org.deadeternity.telegrambot.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    private String uid;
    private String username;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "readed_list",
            joinColumns = {
                @JoinColumn(name = "uid")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "link")
            }
    )
    private List<CreepyPaste> readedPaste;

    public User() {
        readedPaste = new ArrayList<CreepyPaste>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CreepyPaste> getReadedPaste() {
        return readedPaste;
    }

    public void setReadedPaste(List<CreepyPaste> readedPaste) {
        this.readedPaste = readedPaste;
    }

    public void addReadedPaste(CreepyPaste paste) {
        this.readedPaste.add(paste);
    }
}
