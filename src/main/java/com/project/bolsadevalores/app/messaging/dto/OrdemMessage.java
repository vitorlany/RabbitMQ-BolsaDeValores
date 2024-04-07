package com.project.bolsadevalores.app.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record OrdemMessage(@JsonProperty("quantidade") int quantidade,
                           @JsonProperty("valor") double valor,
                           @JsonProperty("corretora") String corretora)
        implements Serializable {
}
