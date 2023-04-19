package stark.reshaper.spike.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role
{
    long id;
    long parentId;
    String name;
    String description;
    int resourceTypeId;
    String resourcePath;
    int state;
    int level;
    Date creationTime;
    long creatorId;
    Date updateTime;
    long updaterId;
}