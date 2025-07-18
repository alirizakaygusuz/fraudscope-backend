package com.finscope.fraudscope.authorization.permission.enums;

import com.finscope.fraudscope.common.enums.BaseEnum;

public enum PredefinedPermisson implements BaseEnum {
	
	//End User Permisison
	E_USER_PROFILE_COMPLETE,
    E_USER_PROFILE_UPDATE,
    E_USER_PROFILE_VIEW,
    E_USER_PROFILE_DELETE,
   

    E_ACCOUNT_CREATE,
    E_ACCOUNT_UPDATE,
    E_ACCOUNT_VIEW,
    E_ACCOUNT_DELETE,

    E_TRANSACTION_CREATE,
    E_TRANSACTION_VIEW,
   
 
    
    //Super Admin Permisisons
    S_ADMIN_PROFILE_COMPLETE,
	S_ADMIN_PROFILE_UPDATE,
    S_ADMIN_PROFILE_VIEW,
    
    S_USER_PROFILE_VIEW,
	S_USER_PROFILES_VIEW,
	S_USER_PROFILE_CREATE,
    S_USER_PROFILE_DELETE,
    S_USER_PROFILE_UPDATE,
    
    
    S_ACCOUNT_CREATE,
    S_ACCOUNT_UPDATE,
    S_ACCOUNT_VIEW,
    S_ACCOUNT_DELETE,

    
    S_TRANSACTION_CANCEL,
    S_TRANSACTION_DELETE,

    S_ROLE_ASSIGN,
    S_PERMISSION_MANAGE;

    @Override
    public String getCode() {
        return name(); 
    }

}
