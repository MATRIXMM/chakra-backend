package com.matrixmm.chakrabackend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaAnimalDTO;
import com.matrixmm.chakrabackend.dto.response.RestResponse;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.service.FamiliaAlimentacionHorarioService;
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
@RequestMapping("/api/familia_alimentacion_horario")
public class FamiliaAlimentacionHorarioController {
    @Autowired
    FamiliaAlimentacionHorarioService familiaAlimentacionHorarioService;

    @RequestMapping(value="listar/{periodo}/{tipo}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaAlimentacionHorario(@PathVariable String periodo,
                                                              @PathVariable String tipo,
                                                              @RequestParam(required=true, name="per_page") Integer perPage,
                                                              @RequestParam(required=true, name="page") Integer page){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaAlimentacionHorarioDTO> familias = familiaAlimentacionHorarioService.listar(periodo, tipo, perPage, page);
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
    public ResponseEntity<?> listarFamiliaAlimentacionHorarioPorFechas(@PathVariable @DateTimeFormat(pattern=Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaInicio,
                                                                       @PathVariable @DateTimeFormat(pattern=Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaFin){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaAlimentacionHorarioDTO> familias = familiaAlimentacionHorarioService.listarPorFechas(fechaInicio, fechaFin);
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
    public ResponseEntity<?> crearFamiliaAlimentacionHorario(@Valid @RequestBody FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaAlimentacionHorarioService.existeFamilia(familiaAlimentacionHorarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaAlimentacionHorarioService.existeFamiliaAlimentacion(familiaAlimentacionHorarioDTO);

                if (!existe) {
                    familiaAlimentacionHorarioService.crear(familiaAlimentacionHorarioDTO);
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
    public ResponseEntity<?> actualizarFamiliaAlimentacionHorario(@Valid @RequestBody FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaAlimentacionHorarioService.existeFamilia(familiaAlimentacionHorarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaAlimentacionHorarioService.existeAlimentacion(familiaAlimentacionHorarioDTO.getIdAlimentacion());

                if (!existe) {
                    response.setStatus(HttpStatus.NOT_FOUND);
                    response.setMessage("No se encuentra una alimentacion asociada al id");
                }
                else {
                    familiaAlimentacionHorarioService.actualizar(familiaAlimentacionHorarioDTO);
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
    public ResponseEntity<?> validarFamiliaAlimentacionHorario(@PathVariable Long id_familia){
        RestResponse response = new RestResponse();

        try {
            FamiliaAlimentacionHorarioDTO elemento = familiaAlimentacionHorarioService.validar(id_familia);
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
