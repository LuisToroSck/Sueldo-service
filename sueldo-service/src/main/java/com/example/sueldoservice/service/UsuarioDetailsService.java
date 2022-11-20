/*package com.example.sueldoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.sueldoservice.entity.UsuarioEntity;
import com.example.sueldoservice.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UsuarioDetailsService implements UserDetailsService{
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username);

        if (usuario != null) {
            User.UserBuilder userBuilder = User.withUsername(username);
            String encryptedPassword = usuario.getClave();
            var rol = usuario.getRol();
            userBuilder.password(encryptedPassword);
            userBuilder.roles(rol);
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException(username);
        }

    }
}*/
