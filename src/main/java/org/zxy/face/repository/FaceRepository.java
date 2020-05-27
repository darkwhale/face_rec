package org.zxy.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.face.dataobject.FaceInfo;

public interface FaceRepository extends JpaRepository<FaceInfo, String> {

}
