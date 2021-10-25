package com.matrixmm.chakrabackend.controller;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.dto.response.RestResponse;
import com.matrixmm.chakrabackend.model.CuidadoSanitario;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.service.FamiliaCuidadoSanitarioService;
import com.matrixmm.chakrabackend.utils.MyUtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/familia_cuidado_sanitario")
public class FamiliaCuidadoSanitarioController {
    @Autowired
    FamiliaCuidadoSanitarioService familiaCuidadoSanitarioService;

    @RequestMapping(value="listar/{periodo}/{tipo}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaAlimentacionHorario(@PathVariable String periodo,
                                                              @PathVariable String tipo,
                                                              @RequestParam(required=true, name="per_page") Integer perPage,
                                                              @RequestParam(required=true, name="page") Integer page){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaCuidadoSanitarioDTO> familias = familiaCuidadoSanitarioService.listar(periodo, tipo, perPage, page);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Listado de familias alimentación");
            response.setPayload(familias);
        }
        catch (Exception e){
            response.setMessage("Error al listar");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value="crear", method=RequestMethod.POST)
    public ResponseEntity<?> crearFamiliaCuidadoSanitario(@Valid @RequestBody FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaCuidadoSanitarioService.existeFamilia(familiaCuidadoSanitarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaCuidadoSanitarioService.existeFamiliaCuidadoSanitario(familiaCuidadoSanitarioDTO);

                if (!existe) {
                    familiaCuidadoSanitarioService.crear(familiaCuidadoSanitarioDTO);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Registro existoso");
                }
                else {
                    response.setStatus(HttpStatus.CONFLICT);
                    response.setMessage("Ya existe un cuidado sanitario con esos atributos");
                }
            }
        }
        catch (Exception e){
            response.setMessage("Error al crear");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value="actualizar", method=RequestMethod.PUT)
    public ResponseEntity<?> actualizarFamiliaCuidadoSanitario(@Valid @RequestBody FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaCuidadoSanitarioService.existeFamilia(familiaCuidadoSanitarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaCuidadoSanitarioService.existeCuidadoSanitario(familiaCuidadoSanitarioDTO.getIdCuidadoSanitario());

                if (!existe) {
                    response.setStatus(HttpStatus.NOT_FOUND);
                    response.setMessage("No se encuentra un cuidado sanitario asociada al id");
                }
                else {
                    familiaCuidadoSanitarioService.actualizar(familiaCuidadoSanitarioDTO);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Registro existoso");
                }
            }
        }
        catch (Exception e){
            response.setMessage("Error al crear");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value="validar/{id_familia}", method=RequestMethod.GET)
    public ResponseEntity<?> validarFamiliaCuidadoSanitario(@PathVariable Long id_familia){
        RestResponse response = new RestResponse();

        try {
            CuidadoSanitario elemento = familiaCuidadoSanitarioService.validar(id_familia);
            if (elemento == null) {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                response.setStatus(HttpStatus.OK);
                response.setMessage("Existe una familia asociada al id");
                response.setPayload(elemento);
            }
        }
        catch (Exception e){
            response.setMessage("Error al validar");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
