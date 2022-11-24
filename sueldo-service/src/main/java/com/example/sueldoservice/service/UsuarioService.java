package com.example.sueldoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sueldoservice.entity.UsuarioEntity;
import com.example.sueldoservice.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioEntity getUserById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioEntity save(UsuarioEntity usuario){
        return usuarioRepository.save(usuario);
    }

}
