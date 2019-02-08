package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.repositories.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    private Logger logger = Logger.getLogger(AvatarService.class.getName());

    public void addAvatar(Avatar newAvatar){
        avatarRepository.insert(newAvatar);
    }

    public Optional<Avatar> getAvatarByUsername(final String username){
        return avatarRepository.findByUsername(username);
    }

    public void removeAvatar(final Avatar avatar){
        avatarRepository.delete(avatar);
    }

    public void removeAvatarById(final String username){
        avatarRepository.deleteById(username);
    }

    public void updateAvatar(final Avatar avatar){
        avatarRepository.save(avatar);
    }

    public void removeAllAvatars(){
        avatarRepository.deleteAll();
    }
}
