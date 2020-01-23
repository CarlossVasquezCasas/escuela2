package com.everis.escuela.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.everis.escuela.DTO.DetalleOrdenReducidoDTO;
import com.everis.escuela.DTO.OrdenDTO;
import com.everis.escuela.DTO.OrdenReducidoDTO;
import com.everis.escuela.DTO.ProductoReducidoDTO;

@Controller
public class OrdenController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/agregarorden2")
	public String guardarOrden(OrdenReducidoDTO ordenReducidoDto, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            return "add-orden";
        }
		
//		userRepository.save(user);
		
		String url = "http://trj-mxl52126qw.mshome.net:9090/orden";
		
		  //setting up the request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
      //request entity is created with request body and headers
        HttpEntity<OrdenReducidoDTO> requestEntity = new HttpEntity<>(ordenReducidoDto, requestHeaders);
        
        
        ResponseEntity<OrdenDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                OrdenDTO.class
        );
        
		

		return null;

	}
	
	
//	@GetMapping("/addorden")
//	public String addorden(Model model) {
//	
//		String url = "http://trj-mxl52126qw.mshome.net:9090/producto/productos";
//	
//        
//		OrdenReducidoDTO ordenReducidoDTO = new OrdenReducidoDTO();
//        List<DetalleOrdenReducidoDTO> detalleOrdenReducidoDTO =  new ArrayList<DetalleOrdenReducidoDTO>();
//        for (int i = 1; i <= 3; i++) {
//        	detalleOrdenReducidoDTO.add(new DetalleOrdenReducidoDTO());
//        }
//       
//        ordenReducidoDTO.setDetalle(detalleOrdenReducidoDTO);
//        
//        model.addAttribute("orden", ordenReducidoDTO);		
//
//		return "/crearorden";
//
//	}
	

}
