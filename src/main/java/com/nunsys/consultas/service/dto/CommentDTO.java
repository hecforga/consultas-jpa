package com.nunsys.consultas.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nunsys.consultas.domain.Comment} entity.
 */
@ApiModel(description = "The Comment entity.\n@author hector.fornes")
public class CommentDTO implements Serializable {
    private Long id;

    /**
     * year
     */
    @NotNull
    @ApiModelProperty(value = "year", required = true)
    private Integer year;

    /**
     * approved
     */
    @NotNull
    @ApiModelProperty(value = "approved", required = true)
    private Boolean approved;

    /**
     * content
     */
    @NotNull
    @Size(max = 3500)
    @ApiModelProperty(value = "content", required = true)
    private String content;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        return id != null && id.equals(((CommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", approved='" + isApproved() + "'" +
            ", content='" + getContent() + "'" +
            ", postId=" + getPostId() +
            "}";
    }
}
