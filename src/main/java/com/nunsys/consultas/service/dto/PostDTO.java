package com.nunsys.consultas.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nunsys.consultas.domain.Post} entity.
 */
@ApiModel(description = "The Post entity.\n@author hector.fornes")
public class PostDTO implements Serializable {
    private Long id;

    /**
     * title
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "title", required = true)
    private String title;

    /**
     * content
     */
    @NotNull
    @Size(max = 3500)
    @ApiModelProperty(value = "content", required = true)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
