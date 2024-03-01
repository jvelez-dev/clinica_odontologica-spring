package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Odontologo;
import com.example.demo.repository.OdontologoRepository;

import org.springframework.ui.Model;


@Controller
@RequestMapping(path="/odontologo")
public class OdontologoController {

	@Autowired
	private OdontologoRepository odontologoRepository;
	
	@PostMapping(path="/new")
	public @ResponseBody String nuevo(@RequestParam String nombres, 
			@RequestParam String apellidos, 
			@RequestParam String especialidad, 
			@RequestParam String telefono, 
			@RequestParam String email) {
		Odontologo o = new Odontologo();
		o.setNombres(nombres);
		o.setApellidos(apellidos);
		o.setEspecialidad(especialidad);
		o.setTelefono(telefono);
		o.setEmail(email);
		
		odontologoRepository.save(o);
		return "Listo";
	}
	
    @GetMapping("/new_frontend")
    public String mostrarFormularioNuevo() {
        return "formularioNuevoOdontologo"; 
    }
    
	@PostMapping("/new_frontend")
    public String nuevo(@RequestParam String nombres,
                        @RequestParam String apellidos,
                        @RequestParam String especialidad,
                        @RequestParam String telefono,
                        @RequestParam String email,
                        Model model) {
        Odontologo o = new Odontologo();
        o.setNombres(nombres);
        o.setApellidos(apellidos);
        o.setEspecialidad(especialidad);
        o.setTelefono(telefono);
        o.setEmail(email);

        odontologoRepository.save(o);

        model.addAttribute("mensaje", "Odontólogo creado correctamente");
        return "respuestaCreacionOdontologo"; 
    }
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable <Odontologo> listarTodos(){
		return odontologoRepository.findAll();
	}

	@GetMapping(path="/all_frontend")
	public String listarTodos_frontend(Model modelo){
		ArrayList<Odontologo> lista = (ArrayList<Odontologo>) odontologoRepository.findAll();
		modelo.addAttribute("odontologos",lista);
		return "listadoOdontologos";
	}
	
	@GetMapping(path="/find")
	public @ResponseBody Odontologo buscarId(@RequestParam int id){
		return odontologoRepository.findById(id).get();
	}
	
	@PostMapping(path="/update/{id}")
	public @ResponseBody String actualizar(@RequestParam int id, 
			@RequestParam String nombres, 
			@RequestParam String apellidos, 
			@RequestParam String especialidad, 
			@RequestParam String telefono, 
			@RequestParam String email) {
		Odontologo o = buscarId(id);
		o.setNombres(nombres);
		o.setApellidos(apellidos);
		o.setEspecialidad(especialidad);
		o.setTelefono(telefono);
		o.setEmail(email);
		odontologoRepository.save(o);
		return "formularioModificarOdontologo";
	}
	
	@GetMapping("/delete/{id}")
    public String mostrarConfirmacionEliminar(@PathVariable int id, Model model) {
        Odontologo odontologo = odontologoRepository.findById(id).get();
        model.addAttribute("odontologo", odontologo);
        return "confirmarEliminarOdontologo"; 
    }

	@PostMapping("/delete/{id}")
    public String eliminar(@PathVariable int id) {
        odontologoRepository.deleteById(id);
        return "redirect:/odontologo/all_frontend"; 
    }
}
