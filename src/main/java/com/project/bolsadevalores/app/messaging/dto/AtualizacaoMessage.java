package com.project.bolsadevalores.app.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.bolsadevalores.app.domain.Ordem;

public record AtualizacaoMessage(@JsonProperty("tipo") String tipo,
                                 @JsonProperty("ordem") Ordem ordem) {
}
