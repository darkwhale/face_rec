package org.zxy.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.face.dataobject.PersonInfo;

public interface PersonRepository extends JpaRepository<PersonInfo, String> {

    PersonInfo findByApiAndPersonId(String api, String personId);

    PersonInfo findByApiAndPersonIdAndPersonName(String api, String PersonId, String personName);
}
