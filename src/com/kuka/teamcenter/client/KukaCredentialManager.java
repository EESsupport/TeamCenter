package com.kuka.teamcenter.client;

import java.util.UUID;

import com.kuka.teamcenter.exceptions.InvalidLoginException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidUserException;
import com.teamcenter.services.loose.core._2011_06.Session.Credentials;
import com.teamcenter.soa.client.CredentialManager;
import com.teamcenter.soa.exceptions.CanceledOperationException;
@SuppressWarnings("UnnecessaryLocalVariable")
public class KukaCredentialManager implements CredentialManager{

    private static String mDiscriminator;

    public String getDiscriminator(){
        if(mDiscriminator.isEmpty()){
            mDiscriminator=UUID.randomUUID().toString();
        }
        return mDiscriminator;
    }

    private static ILoginInfo mloginInfo;

    public KukaCredentialManager(){

    }

    public KukaCredentialManager(ILoginInfo loginInfo){
        mloginInfo=loginInfo;
    }

    public KukaCredentialManager(String name,String password,String group,String role){
        mloginInfo = new LoginInfo();
        mloginInfo.setName(name);
        mloginInfo.setPassword(password);
    }

    /**
     * Return the type of credentials this implementation provides,
     * standard (user/password) or Single-Sign-On. In this case
     * Standard credentials are returned.
     *
     * @see CredentialManager#getCredentialType()
     */
    public int getCredentialType() {
        return CredentialManager.CLIENT_CREDENTIAL_TYPE_STD;
    }


    public String[] getCredentials(InvalidCredentialsException arg0) throws CanceledOperationException {
        String[] result = ((LoginInfo)mloginInfo).getInfo();
        return result;
    }


    public String[] getCredentials(InvalidUserException arg0) throws CanceledOperationException {
        return promptForCredentials();
    }

    /**
     * Cache the group and role
     * This is called after the SessionService.setSessionGroupMember service
     * operation is called.
     *
     * @see CredentialManager#setGroupRole(String,
     *      String)
     */

    public void setGroupRole(String group, String role) {

        mloginInfo.setGroup(group);
        mloginInfo.setRole(role);

    }


    public void setUserPassword(String user, String password, String discriminator) {

        mloginInfo.setName(user);
        mloginInfo.setPassword(password);
        mloginInfo.setDiscriminator(discriminator);
    }

    public String[] promptForCredentials(){
        try {
            return getLoginCredentials();
        } catch (InvalidLoginException e) {

            e.printStackTrace();
        }
        return null;
    }

    public ILoginInfo getLoginInfo(){
        return mloginInfo;
    }

    public Credentials getCredentials(){
        Credentials result = new Credentials();
        result.group=mloginInfo.getGroup();
        result.role=mloginInfo.getRole();
        result.user=mloginInfo.getName();
        result.password=mloginInfo.getPassword();
        result.descrimator=mloginInfo.getDiscriminator();
        return result;
    }

    private String[] getLoginCredentials()
            throws InvalidLoginException{
        return ((LoginInfo)mloginInfo).getInfo();
    }
}