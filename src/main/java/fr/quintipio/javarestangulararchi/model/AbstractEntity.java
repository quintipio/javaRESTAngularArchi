package fr.quintipio.javarestangulararchi.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Date lastModifiedDate;

    @Version
    @Column(name = "version",nullable = false)
    @JsonIgnore
    private Integer version;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
