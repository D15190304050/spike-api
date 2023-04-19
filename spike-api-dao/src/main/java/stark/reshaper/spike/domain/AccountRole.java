package stark.reshaper.spike.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRole
{
    private long id;

    private long accountId;

    private long roleId;

    private int state;

    private Date creationTime;

    private long creatorId;

    private Date updateTime;

    private long updaterId;
}