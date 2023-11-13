package executor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Chefware {
    @JsonProperty("comando")
    private String comando;

    @JsonCreator
    public Chefware(@JsonProperty("comando") String comando) {
        this.comando = comando;
    }

    public String getComando() {
        return comando;
    }

    public boolean isEmpty() {
        return this.comando == null || this.comando.isEmpty();
    }
}
