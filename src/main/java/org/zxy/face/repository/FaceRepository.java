package org.zxy.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.face.dataobject.FaceInfo;

import java.util.List;

public interface FaceRepository extends JpaRepository<FaceInfo, String> {

    FaceInfo findByIdAndApi(String id, String api);

    Integer countByApiAndPersonId(String api, String personId);

    List<FaceInfo> findAllByApiAndPersonId(String api, String personId);

    List<FaceInfo> findAllByApiAndPersonIdIn(String api, List<String> personIdList);
}
