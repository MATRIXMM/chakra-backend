package com.matrixmm.chakrabackend.controller;

import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.response.RestResponse;
import com.matrixmm.chakrabackend.model.CuidadoSanitario;
import com.matrixmm.chakrabackend.service.FamiliaIncidenciaCuidadoSanitarioService;
import com.matrixmm.chakrabackend.utils.MyUtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/familia_incidencia_cuidado_sanitario")
public class FamiliaIncidenciaCuidadoSanitarioController {
    @Autowired
    FamiliaIncidenciaCuidadoSanitarioService familiaIncidenciaCuidadoSanitarioService;

    @RequestMapping(value="listar/{periodo}/{tipo}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaIncidenciaCuidadoSanitario(@PathVariable String periodo,
                                                                     @PathVariable String tipo,
                                                                     @RequestParam(required=true, name="per_page") Integer perPage,
                                                                     @RequestParam(required=true, name="page") Integer page){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaIncidenciaCuidadoSanitarioDTO> familias = familiaIncidenciaCuidadoSanitarioService.listar(periodo, tipo, perPage, page);
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
    public ResponseEntity<?> crearFamiliaIncidenciaCuidadoSanitario(@Valid @RequestBody FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaIncidenciaCuidadoSanitarioService.existeFamilia(familiaIncidenciaCuidadoSanitarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaIncidenciaCuidadoSanitarioService.existeFamiliaIncidenciaCuidadoSanitario(familiaIncidenciaCuidadoSanitarioDTO);

                if (!existe) {
                    familiaIncidenciaCuidadoSanitarioService.crear(familiaIncidenciaCuidadoSanitarioDTO);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Registro existoso");
                }
                else {
                    response.setStatus(HttpStatus.CONFLICT);
                    response.setMessage("Ya existe una alimentación con esos atributos");
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
    public ResponseEntity<?> actualizarFamiliaIncidenciaCuidadoSanitario(@Valid @RequestBody FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaIncidenciaCuidadoSanitarioService.existeFamilia(familiaIncidenciaCuidadoSanitarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaIncidenciaCuidadoSanitarioService.existeCuidadoSanitario(familiaIncidenciaCuidadoSanitarioDTO.getIdCuidadoSanitario());

                if (!existe) {
                    response.setStatus(HttpStatus.NOT_FOUND);
                    response.setMessage("No se encuentra una alimentacion asociada al id");
                }
                else {
                    familiaIncidenciaCuidadoSanitarioService.actualizar(familiaIncidenciaCuidadoSanitarioDTO);
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
    public ResponseEntity<?> validarFamiliaIncidenciaCuidadoSanitario(@PathVariable Long id_familia){
        RestResponse response = new RestResponse();

        try {
            CuidadoSanitario elemento = familiaIncidenciaCuidadoSanitarioService.validar(id_familia);
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
