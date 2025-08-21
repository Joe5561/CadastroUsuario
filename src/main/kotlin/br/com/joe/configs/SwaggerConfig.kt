package br.com.joe.configs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean

class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API de Usuários")
                    .version("1.0.0")
                    .description("Documentação da API para gerenciamento de usuários")
            )
    }
}