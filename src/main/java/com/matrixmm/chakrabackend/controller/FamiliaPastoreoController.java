package com.matrixmm.chakrabackend.controller;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.dto.response.RestResponse;
import com.matrixmm.chakrabackend.model.Pastoreo;
import com.matrixmm.chakrabackend.service.FamiliaPastoreoService;
import com.matrixmm.chakrabackend.utils.Format;
import com.matrixmm.chakrabackend.utils.MyUtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/familia_pastoreo")
public class FamiliaPastoreoController {
    @Autowired
    FamiliaPastoreoService familiaPastoreoService;

    @RequestMapping(value="listar/{periodo}/{tipo}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaAlimentacionHorario(@PathVariable String periodo,
                                                              @PathVariable String tipo,
                                                              @RequestParam(required=true, name="per_page") Integer perPage,
                                                              @RequestParam(required=true, name="page") Integer page){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaPastoreoDTO> familias = familiaPastoreoService.listar(periodo, tipo, perPage, page);
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

    @RequestMapping(value="listarPorFechas/{fechaInicio}/{fechaFin}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaAlimentacionHorarioPorFechas(@PathVariable @DateTimeFormat(pattern= Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaInicio,
                                                                       @PathVariable @DateTimeFormat(pattern=Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaFin){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaPastoreoDTO> familias = familiaPastoreoService.listarPorFechas(fechaInicio, fechaFin);
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
    public ResponseEntity<?> crearFamiliaPastoreo(@Valid @RequestBody FamiliaPastoreoDTO familiaPastoreoDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaPastoreoService.existeFamilia(familiaPastoreoDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaPastoreoService.existeFamiliaPastoreo(familiaPastoreoDTO);

                if (!existe) {
                    familiaPastoreoService.crear(familiaPastoreoDTO);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Registro existoso");
                }
                else {
                    response.setStatus(HttpStatus.CONFLICT);
                    response.setMessage("Ya existe un pastoreo con esos atributos");
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
    public ResponseEntity<?> actualizarFamiliaPastoreo(@Valid @RequestBody FamiliaPastoreoDTO familiaPastoreoDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaPastoreoService.existeFamilia(familiaPastoreoDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaPastoreoService.existePastoreo(familiaPastoreoDTO.getIdPastoreo());

                if (!existe) {
                    response.setStatus(HttpStatus.NOT_FOUND);
                    response.setMessage("No se encuentra un pastoreo asociada al id");
                }
                else {
                    familiaPastoreoService.actualizar(familiaPastoreoDTO);
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
    public ResponseEntity<?> validarFamiliaPastoreo(@PathVariable Long id_familia){
        RestResponse response = new RestResponse();

        try {
            Pastoreo elemento = familiaPastoreoService.validar(id_familia);
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
