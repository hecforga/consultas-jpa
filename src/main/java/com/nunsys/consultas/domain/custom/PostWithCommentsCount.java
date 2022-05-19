package com.nunsys.consultas.domain.custom;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "vw_post_with_comments_count")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PostWithCommentsCount implements Serializable {
    @Id
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "comments_count")
    private Integer commentsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostWithCommentsCount)) {
            return false;
        }
        return id != null && id.equals(((PostWithCommentsCount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostWithCommentsCount{" +
            "id=" + getId() +
            ", summary='" + getSummary() + "'" +
            ", commentsCount='" + getCommentsCount() + "'" +
            "}";
    }
}
