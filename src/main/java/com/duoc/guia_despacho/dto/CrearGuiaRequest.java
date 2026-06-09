package com.duoc.guia_despacho.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CrearGuiaRequest(
        @NotBlank String numeroGuia,
        @NotBlank String transportista,
        @NotNull LocalDate fecha,
        @NotBlank String destinatario,
        @NotBlank String direccionDestino,
        @NotBlank String descripcionCarga
) {
}
