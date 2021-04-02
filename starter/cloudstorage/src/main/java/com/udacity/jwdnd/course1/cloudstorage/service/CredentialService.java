package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;

    }

    public boolean credentialExists(Credential credential, Integer userId){
        return credentialMapper.getCredentialByUsername(credential.getUsername(), userId)!=null;
    }

    public void createOrUpdateCredential(Credential credential) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(),encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        if(credential.getCredentialId()<1)
            credentialMapper.insertCredential(credential);
        else
            credentialMapper.updateCredential(credential);
    }

    public Credential getCredential(Integer credentialId, Integer userId) {
        return credentialMapper.getCredentialById(credentialId,userId);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

}
