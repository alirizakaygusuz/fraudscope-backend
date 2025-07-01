package com.finscope.fraudscope.authorization.permission.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authorization.permission.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
