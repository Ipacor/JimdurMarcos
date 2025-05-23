package com.diedari.jimdur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.diedari.jimdur.model.Usuario;
import com.diedari.jimdur.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/usuarios") // Mapeo de la URL base para el controlador
public class UsuarioController {

    @Autowired
    private UsuarioService service; // Inyección de dependencia del servicio de usuario

    // Método para mostrar la lista de usuarios
    @GetMapping("/listar")
    public String listarUsuariosForm(Model model) {
        model.addAttribute("usuarios", service.listarTodosLosUsuarios());
        return "admin/usuarios/listar"; // Retorna la vista de listar usuarios
    }

    // Método para mostrar el formulario de agregar un nuevo usuario
    @GetMapping("/nuevo")
    public String nuevoUsuarioForm(Model model) {
        Usuario usuario = new Usuario(); // Crea un nuevo objeto Usuario
        model.addAttribute("usuario", new Usuario()); // Agrega un nuevo objeto Usuario al modelo
        return "admin/usuarios/nuevo"; // Retorna la vista de agregar usuario
    }

    // Guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        service.guardarUsuario(usuario); // Llama al servicio para guardar el usuario
        return "redirect:/admin/usuarios/listar"; // Redirige a la lista de usuarios después de guardar

    }

    // editar usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.obtenerUsuarioPorId(id)); // Agrega el usuario a editar al modelo
        return "admin/usuarios/editar"; // Retorna la vista de editar usuario
    }

    // Actualizar usuario
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("usuario") Usuario usuario, Model model) {
        Usuario usuarioExistente = service.obtenerUsuarioPorId(id); // Obtiene el usuario existente
        usuarioExistente.setId(id); // Establece el ID del usuario existente
        usuarioExistente.setNombre(usuario.getNombre()); // Actualiza el nombre
        usuarioExistente.setApellido(usuario.getApellido()); // Actualiza el apellido
        usuarioExistente.setCorreo(usuario.getCorreo()); // Actualiza el correo
        usuarioExistente.setContraseña(usuario.getContraseña()); // Actualiza la contraseña

        service.actualizarUsuario(usuarioExistente); // Llama al servicio para actualizar el usuario
        return "redirect:/admin/usuarios/listar"; // Redirige a la lista de usuarios después de actualizar
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        service.eliminarUsuario(id); // Llama al servicio para eliminar el usuario
        return "redirect:/admin/usuarios/listar"; // Redirige a la lista de usuarios después de eliminar
    }
}