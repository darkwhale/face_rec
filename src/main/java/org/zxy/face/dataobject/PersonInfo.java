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
public class PersonInfo {

    @Id
    private String id;

    private String api;

    private String personId;

    private String personName;

    private Date createTime;
}
