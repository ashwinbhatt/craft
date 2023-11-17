package com.ashwinbhatt.craft.tsheets.services;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.tsheets.exceptions.AppDbServiceException;
import com.ashwinbhatt.craft.tsheets.modules.UserTrack;
import com.ashwinbhatt.craft.tsheets.repository.UserRepository;
import com.ashwinbhatt.craft.tsheets.repository.UserTrackRepository;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppDbServices {

    private final UserRepository userRepository;
    private final UserTrackRepository userTrackRepository;

    public AppDbServices(UserRepository userRepository, UserTrackRepository userTrackRepository) {
        this.userRepository = userRepository;
        this.userTrackRepository = userTrackRepository;
    }

    public boolean userTrackUpdate(@NonNull String userId) throws AppDbServiceException {
        if(!userRepository.existsById(userId)) {
            throw new AppDbServiceException(String.format("User with userId %s doesn't exist!", userId));
        }
        Optional<UserTrack> optionalUserTrack = userTrackRepository.findById(userId);

        if(optionalUserTrack.isEmpty()) {
            userTrackRepository.save(new UserTrack(userId, Boolean.TRUE));
        }
        UserTrack ut = optionalUserTrack.get();
        ut.setIsOnline(Boolean.TRUE);
        userTrackRepository.save(ut);
        return true;
    }

    public Boolean validateForAddressChange(@NonNull AddressChangeRequest addressChangeRequest) {
        return Boolean.TRUE;
    }

    public UserTrack getUserTrackInfo(@NonNull String userId) {
        Optional<UserTrack> optionalUserTrack = userTrackRepository.findById(userId);

        if(optionalUserTrack.isEmpty()) {
            userTrackRepository.save(new UserTrack(userId, Boolean.TRUE));
        }
        return optionalUserTrack.get();
    }
}
