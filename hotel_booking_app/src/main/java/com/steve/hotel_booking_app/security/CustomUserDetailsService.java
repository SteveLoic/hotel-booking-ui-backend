package com.steve.hotel_booking_app.security;


import com.steve.hotel_booking_app.exceptions.RessourceNotFoundException;
import com.steve.hotel_booking_app.user.User;
import com.steve.hotel_booking_app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(username).orElseThrow(
                 () -> new RessourceNotFoundException(username)
         );
         return  AuthUser.builder().user(user).build();
    }
}
