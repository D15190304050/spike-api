package stark.reshaper.spike.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RolePermission
{
    private long id;

    private long roleId;

    private long permissionId;

    private int state;

    private Date creationTime;

    private long creatorId;

    private Date updateTime;

    private long updaterId;
}