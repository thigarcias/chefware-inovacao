package model.tabelas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosEstaticos {
    String tipo;
    String descricao;
    String valor;
    String unidadeMedida;

    public DadosEstaticos() {}

    @Override
    public String toString() {
        return """
                Nome: %s
                Marca: %s
                Descrição: %s
                Valor: %s""".formatted(this.tipo, this.descricao, this.valor, this.unidadeMedida);
    }
}
