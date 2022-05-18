package com.nunsys.consultas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Comment entity.\n@author hector.fornes
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * year
     */
    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    /**
     * approved
     */
    @NotNull
    @Column(name = "approved", nullable = false)
    private Boolean approved;

    /**
     * content
     */
    @NotNull
    @Size(max = 3500)
    @Column(name = "content", length = 3500, nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public Comment year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isApproved() {
        return approved;
    }

    public Comment approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", approved='" + isApproved() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
