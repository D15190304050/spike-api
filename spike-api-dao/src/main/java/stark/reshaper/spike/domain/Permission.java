package stark.reshaper.spike.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Permission
{
    private long id;

    private long parentId;

    private String name;

    private String description;

    private int resourceTypeId;

    private String resourcePath;

    private String componentPath;

    private String iconImageUrl;

    private int state;

    private int level;

    private Date creationTime;

    private long creatorId;

    private Date updateTime;

    private long updaterId;
}