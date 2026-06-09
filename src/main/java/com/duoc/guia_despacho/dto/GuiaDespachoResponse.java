package com.duoc.guia_despacho.dto;

import com.duoc.guia_despacho.model.GuiaDespacho;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GuiaDespachoResponse(
        Long id,
        String numeroGuia,
        String transportista,
        LocalDate fecha,
        String destinatario,
        String direccionDestino,
        String descripcionCarga,
        String estado,
        String nombreArchivo,
        String rutaEfs,
        String s3Key,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {

    public static GuiaDespachoResponse fromEntity(GuiaDespacho guia) {
        return new GuiaDespachoResponse(
                guia.getId(),
                guia.getNumeroGuia(),
                guia.getTransportista(),
                guia.getFecha(),
                guia.getDestinatario(),
                guia.getDireccionDestino(),
                guia.getDescripcionCarga(),
                guia.getEstado(),
                guia.getNombreArchivo(),
                guia.getRutaEfs(),
                guia.getS3Key(),
                guia.getFechaCreacion(),
                guia.getFechaActualizacion()
        );
    }
}
