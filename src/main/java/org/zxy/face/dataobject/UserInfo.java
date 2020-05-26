package org.zxy.face.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String api;

    private Date createTime;
}
