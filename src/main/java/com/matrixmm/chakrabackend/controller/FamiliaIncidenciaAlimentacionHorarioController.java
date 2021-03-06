package com.matrixmm.chakrabackend.controller;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.response.RestResponse;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.service.FamiliaIncidenciaAlimentacionHorarioService;
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
@RequestMapping("/api/familia_incidencia_alimentacion_horario")
public class FamiliaIncidenciaAlimentacionHorarioController {
    @Autowired
    FamiliaIncidenciaAlimentacionHorarioService familiaIncidenciaAlimentacionHorarioService;

    @RequestMapping(value="listar/{periodo}/{tipo}", method=RequestMethod.GET)
    public ResponseEntity<?> listarFamiliaIncidenciaAlimentacionHorario(@PathVariable String periodo,
                                                                        @PathVariable String tipo,
                                                                        @RequestParam(required=true, name="per_page") Integer perPage,
                                                                        @RequestParam(required=true, name="page") Integer page){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaIncidenciaAlimentacionHorarioDTO> familias = familiaIncidenciaAlimentacionHorarioService.listar(periodo, tipo, perPage, page);
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
    public ResponseEntity<?> listarFamiliaIncidenciaAlimentacionHorarioPorFechas(@PathVariable @DateTimeFormat(pattern= Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaInicio,
                                                                                 @PathVariable @DateTimeFormat(pattern=Format.LocalDateTimeYearMonthDayHourMinuteSecondForPathVariable) LocalDateTime fechaFin){
        RestResponse response = new RestResponse();

        try {
            List<FamiliaIncidenciaAlimentacionHorarioDTO> familias = familiaIncidenciaAlimentacionHorarioService.listarPorFechas(fechaInicio, fechaFin);
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
    public ResponseEntity<?> crearFamiliaIncidenciaAlimentacionHorario(@Valid @RequestBody FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaIncidenciaAlimentacionHorarioService.existeFamilia(familiaIncidenciaAlimentacionHorarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaIncidenciaAlimentacionHorarioService.existeFamiliaIncidenciaAlimentacion(familiaIncidenciaAlimentacionHorarioDTO);

                if (!existe) {
                    familiaIncidenciaAlimentacionHorarioService.crear(familiaIncidenciaAlimentacionHorarioDTO);
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
    public ResponseEntity<?> actualizarFamiliaIncidenciaAlimentacionHorario(@Valid @RequestBody FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO, BindingResult errors) {
        RestResponse response = new RestResponse();
        String validationMessage = MyUtilMethods.getValidationMessage(errors);
        if (validationMessage != null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(validationMessage);
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        try {
            Boolean existe = familiaIncidenciaAlimentacionHorarioService.existeFamilia(familiaIncidenciaAlimentacionHorarioDTO.getIdFamilia());
            if (!existe){
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encuentra una familia asociada al id");
            }
            else {
                existe = familiaIncidenciaAlimentacionHorarioService.existeAlimentacion(familiaIncidenciaAlimentacionHorarioDTO.getIdAlimentacion());

                if (!existe) {
                    response.setStatus(HttpStatus.NOT_FOUND);
                    response.setMessage("No se encuentra una alimentacion asociada al id");
                }
                else {
                    familiaIncidenciaAlimentacionHorarioService.actualizar(familiaIncidenciaAlimentacionHorarioDTO);
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

    @RequestMapping(value="validar/{id_incidencia}", method=RequestMethod.GET)
    public ResponseEntity<?> validarFamiliaIncidenciaAlimentacionHorario(@PathVariable Long id_incidencia){
        RestResponse response = new RestResponse();

        try {
            FamiliaIncidenciaAlimentacionHorarioDTO elemento = familiaIncidenciaAlimentacionHorarioService.validar(id_incidencia);
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
