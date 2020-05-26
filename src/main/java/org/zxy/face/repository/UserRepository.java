package org.zxy.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.face.dataobject.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String> {
}
